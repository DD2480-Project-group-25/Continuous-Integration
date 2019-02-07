package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/** A job that clones a repository. */
public class CloneJob implements Runnable {
  /**
   * A {@link JobExaminer} that creates instances of {@link TestJob}.
   *
   * <p>Inspect the docs for {@link JobExaminer}.
   */
  public static class Examiner extends JobExaminer {

    public Examiner(EventQueue queue) {
      super(queue);
    }

    /**
     * This function decides if it wants to accept an event and offer a {@link PrintJob}.
     *
     * <p>This function confirms to the {@link JobExaminer} interface.
     *
     * @param event the event offered to this function to accept or decline
     * @return an print job represented by a {@link Runnable} if accepted
     */
    @Override
    public Optional<Runnable> offer(Event event) {
      return event.getType() == Event.Type.WEB_HOOK
          ? Optional.of(new CloneJob(event, super.queue))
          : Optional.empty();
    }
  }

  private Event event;
  private EventQueue queue;
  private String url;
  private String branch;
  private String directory;

  private CloneJob(Event event, EventQueue queue) {
    this.event = event;
    this.queue = queue;
    // set default branch to master and default directory to current directory
    this.branch = "master";
    this.directory = ".";
  }

  /** Clones a repository. By default, the master branch is cloned to the current directory */
  @Override
  public void run() {
    try {
      try {
        String command = "git clone --branch" + " " + branch + " " + url + " " + event.getId();
        // run the command in the given directory
        Process p = Runtime.getRuntime().exec(command, null, new File(directory));
        Scanner s = new Scanner(p.getErrorStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        if (result.contains("fatal")) {
          throw new IOException();
        }
        queue.insert(
            new Event(
                event.getId(),
                Event.Type.CLONE,
                Event.Status.SUCCESSFUL,
                "The repository is cloned successfully"));
      } catch (IOException e) {
        System.err.println(e.getMessage());
        queue.insert(
            new Event(
                event.getId(), Event.Type.CLONE, Event.Status.FAIL, "Could not clone repository"));
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
