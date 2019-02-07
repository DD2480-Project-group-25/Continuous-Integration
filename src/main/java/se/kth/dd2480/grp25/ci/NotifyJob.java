package se.kth.dd2480.grp25.ci;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/** A job for notifying */
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
      if (event.getType() == Event.Type.TEST
          || event.getType() == Event.Type.BUILD
          || event.getType() == Event.Type.WEB_HOOK) {
        return Optional.of(new NotifyJob(event, super.queue));
      } else {
        return Optional.empty();
      }
    }
  }

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
      String testFail = "\"Some tests failed: " + event.getMessage() + "\"";
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
            params = createParams(buildFail);
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that build failed.");
          } else {
            // We don't want to notify the user about a successful build
            return;
          }
        }
        if (event.getType() == Event.Type.TEST) {
          if (event.getStatus() == Event.Status.SUCCESSFUL) {
            params = createParams(buildSuccessful);
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that build was successful.");
          } else {
            params = createParams(testFail);
            notifyEvent =
                new Event(
                    event.getId(),
                    Event.Type.NOTIFY,
                    Event.Status.SUCCESSFUL,
                    "Notified that tests failed.");
          }
        }
        os.write(params.getBytes(StandardCharsets.UTF_8));
        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
          notifyEvent =
              new Event(event.getId(), Event.Type.NOTIFY, Event.Status.FAIL, "Failed to notify");
        }
        queue.insert(notifyEvent);
        os.flush();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private String createParams(String message) {
    String params =
        "{\"state\":\"success\",\"target_url\":\"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/build/"
            + event.getId()
            + "\",\"description\":"
            + message
            + ",\"context\":\"DD2480 CI\"}";
    return params;
  }
}
