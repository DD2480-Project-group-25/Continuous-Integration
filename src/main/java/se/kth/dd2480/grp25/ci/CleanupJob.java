package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.util.Optional;

/** A job that removes a cloned repository located in the project's root */
public class CleanupJob implements Runnable {
  private Event event;

  public CleanupJob(Event event) {
    this.event = event;
  }

  public static Optional<Runnable> offer(Event event) {
    if (event.getType() == Event.EventType.CLEANUP) {
      return Optional.of(new CleanupJob(event));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Controls that the directory actually exists, if it does it is removed with bash command rm -rf
   * to avoid prompts.
   */
  @Override
  public void run() {
    try {
      File dir = new File("./" + event.getId());
      if (!dir.exists()) {
        event.setMessage("CI Server error: directory doesn't exist");
        event.setStatusCode(Event.StatusCode.FAIL);
        return;
      }
      String command = "rm -rf " + event.getId();
      Runtime.getRuntime().exec(command, null, new File(".")).waitFor();
      if (!dir.exists()) {
        event.setMessage("Cloned repository was successfully deleted");
        event.setStatusCode(Event.StatusCode.SUCCESSFUL);
      } else {
        event.setMessage("Could not delete repository");
        event.setStatusCode(Event.StatusCode.FAIL);
      }
    } catch (Exception e) {
      event.setMessage("CI Server error: " + e);
      event.setStatusCode(Event.StatusCode.FAIL);
    }
  }
}
