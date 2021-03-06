package se.kth.dd2480.grp25.ci;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/** A job for notifying Github about builds and testing. */
public class NotifyJob implements Runnable {
  private Event event;
  private EventQueue queue;
  private String token = "";

  public static class Examiner extends JobExaminer {

    public Examiner(EventQueue queue) {
      super(queue);
    }

    @Override
    public Optional<Runnable> offer(Event event) {
      if (event.getType() == Event.Type.TEST || event.getType() == Event.Type.BUILD) {
        return Optional.of(new NotifyJob(event, super.queue));
      } else {
        return Optional.empty();
      }
    }
  }

  /**
   * Create an instance of {@linkplain NotifyJob}.
   *
   * @param event the event that this job should process.
   * @param queue the queue that this job may append new events to.
   */
  public NotifyJob(Event event, EventQueue queue) {
    this.event = event;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      token = System.getenv("TOKEN");
      if (token == null) {
        Event notifyEvent =
            new Event(event.getId(), Event.Type.NOTIFY, Event.Status.FAIL, "Invalid token.");
        queue.insert(notifyEvent);
        return;
      }
      String buildSuccessful = "\"All tests passed!\"";
      String buildFail = "\"Build fail\"";
      String testFail = "\"Tests failed\"";
      URL url =
          new URL(
              "https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/"
                  + event.getId()
                  + "?access_token="
                  + token);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      try (OutputStream os = conn.getOutputStream()) {
        String params = "";
        Event notifyEvent = null;
        if (event.getType() == Event.Type.BUILD) {
          if (event.getStatus() == Event.Status.FAIL) {
            params = createParams(buildFail, "failure");
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that build failed.",
                    event);
          } else {
            // We don't want to notify the user about a successful build
            return;
          }
        }
        if (event.getType() == Event.Type.TEST) {
          if (event.getStatus() == Event.Status.SUCCESSFUL) {
            params = createParams(buildSuccessful, "success");
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that build was successful.",
                    event);
          } else {
            params = createParams(testFail, "failure");
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that tests failed.",
                    event);
          }
        }
        os.write(params.getBytes(StandardCharsets.UTF_8));
        if (conn.getResponseCode() == 422) {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.NOTIFY,
                  Event.Status.FAIL,
                  "Unprocessable entity",
                  event));
        } else {
          queue.insert(notifyEvent);
        }
        os.flush();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Create a notify message to github. Uses hardcoded URL to server, should be updated in
   * production
   *
   * @param message to github notification
   * @param state of the notification
   * @return properly formatted github notification
   */
  private String createParams(String message, String state) {
    String params =
        "{\"state\":\""
            + state
            + "\",\"target_url\":\"http://157.230.31.10:8080/logs/"
            + event.getId()
            + "\",\"description\":"
            + message
            + ",\"context\":\"DD2480 CI\"}";
    return params;
  }
}
