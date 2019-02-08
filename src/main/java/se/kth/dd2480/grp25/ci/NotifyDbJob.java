package se.kth.dd2480.grp25.ci;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/** A job that creates entries in the database for successful and failed notifyEvents */
public class NotifyDbJob implements Runnable {
  private Event event;
  private EventQueue queue;
  private boolean test;

  /**
   * A {@link JobExaminer} that creates instances of {@link NotifyDbJob}.
   *
   * <p>Inspect the docs for {@link JobExaminer}.
   */
  public static class Examiner extends JobExaminer {
    /**
     * Create a Examiner.
     *
     * @param queue the event queue that jobs that this Examiner creates should insert events in.
     */
    public Examiner(EventQueue queue) {
      super(queue);
    }

    /**
     * Determines if an event should be accepted and a job created.
     *
     * @param event the offered event.
     * @return an optional of an NotifyDbJob if event is accepted, otherwise empty optional.
     */
    @Override
    public Optional<Runnable> offer(Event event) {
      if (event.getType() == Event.Type.NOTIFY) {
        return Optional.of(new NotifyDbJob(event, super.queue, false));
      } else {
        return Optional.empty();
      }
    }
  }
  /**
   * Create an instance of {@linkplain NotifyDbJob}.
   *
   * @param event the event that this job should process.
   * @param queue the queue that this job may append new events to.
   */
  public NotifyDbJob(Event event, EventQueue queue, boolean test) {
    this.event = event;
    this.queue = queue;
    this.test = test;
  }

  /**
   * Opens a HttpURLConnection towards url and writes a post to the api form, to create a database
   * entry.
   */
  public void run() {
    try {
      try {
        URL url;
        if (test) {
          url = new URL("https://postman-echo.com/post");
        } else {
          url = new URL("https://localhost:8080/api");
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String id = event.getId();
        String status = event.getStatus().name();

        String json =
            "{\"log entries\":{\"commit_id\":\""
                + id
                + "\",\"start\":\"00:00:00\",\"status\":\""
                + status
                + "\"}}";

        conn.setRequestProperty("Content-type", "application/json");
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
          os.write(json.getBytes(StandardCharsets.UTF_8));
        }
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.NOTIFYDB,
                  Event.Status.SUCCESSFUL,
                  "Database notified"));
        } else {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.NOTIFYDB,
                  Event.Status.FAIL,
                  "Couldn't notify database, response code: " + conn.getResponseCode()));
        }
      } catch (Exception e) {
        System.err.println(e);
        queue.insert(
            new Event(
                event.getId(),
                Event.Type.NOTIFYDB,
                Event.Status.FAIL,
                "Couldn't notify database: " + e.toString()));
      }
    } catch (InterruptedException e) {
      System.err.println(e);
    }
  }
}
