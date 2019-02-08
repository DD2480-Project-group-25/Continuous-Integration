package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/** A job that removes a cloned repository located in the project's root */
public class CleanupJob implements Runnable {
  /**
   * A {@link JobExaminer} that creates instances of {@link CloneJob}.
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
     * @return an optional of an CleanupJob if event is accepted, otherwise empty optional.
     */
    @Override
    public Optional<Runnable> offer(Event event) {
      boolean interesting =
          (event.getType() == Event.Type.TEST && event.getStatus() == Event.Status.SUCCESSFUL)
              || (event.getType() != Event.Type.NOTIFY
                  && event.getType() != Event.Type.NOTIFYDB
                  && event.getType() != Event.Type.CLEANUP
                  && event.getStatus() == Event.Status.FAIL);

      return interesting ? Optional.of(new CleanupJob(event, super.queue)) : Optional.empty();
    }
  }

  private Event event;
  private EventQueue queue;

  /**
   * Create a {@linkplain CleanupJob} instance.
   *
   * @param event the event that this job should process.
   * @param queue the queue that this job may append new events to.
   */
  public CleanupJob(Event event, EventQueue queue) {
    this.event = event;
    this.queue = queue;
  }

  /**
   * Controls that the directory actually exists, if it does it is removed with bash command rm -rf
   * to avoid prompts.
   */
  @Override
  public void run() {
    try {
      try {
        File dir = new File("./" + event.getId());
        if (!dir.exists()) {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.CLEANUP,
                  Event.Status.FAIL,
                  "Directory doesn't exist, nothing to cleanup"));
          return;
        }
        String command = "rm -rf " + event.getId();
        Runtime.getRuntime().exec(command, null, new File(".")).waitFor();
        if (!dir.exists()) {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.CLEANUP,
                  Event.Status.SUCCESSFUL,
                  "Cloned repository was successfully deleted"));
        } else {
          queue.insert(
              new Event(
                  event.getId(),
                  Event.Type.CLEANUP,
                  Event.Status.FAIL,
                  "Could not delete repository."));
        }
      } catch (IOException e) {
        queue.insert(
            new Event(event.getId(), Event.Type.CLEANUP, Event.Status.FAIL, e.getMessage()));
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
