package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import org.gradle.tooling.*;

/** A job that builds a project given by an Event object of Type 'CLONE'. */
public class BuildJob implements Runnable {
  public static class Examiner extends JobExaminer {

    /**
     * Create a JobExaminer.
     *
     * @param queue the event queue that jobs that this Examiner creates should insert events in.
     */
    public Examiner(EventQueue queue) {
      super(queue);
    }

    /**
     * This functions decides if it wants to accept an event and offer a {@link BuildJob}.
     *
     * <p>This function confirms to the {@link JobExaminer} interface.
     *
     * @param event the event offered to this function to accept or decline
     * @return a build job represented by a {@link Runnable} if accepted
     */
    @Override
    public Optional<Runnable> offer(Event event) {
      return event.getType() == Event.Type.CLONE && event.getStatus() == Event.Status.SUCCESSFUL
          ? Optional.of(new BuildJob(event, super.queue))
          : Optional.empty();
    }
  }

  private Event event;
  private String path;
  private EventQueue queue;

  public BuildJob(Event event, EventQueue queue) {
    this.event = event;
    this.path = "./" + event.getId();
    this.queue = queue;
  }

  /**
   * Controls that the project repository exists and if it does, builds the project with the help of
   * "gradle build". Logs compile errors.
   */
  @Override
  public void run() {
    File project = (new File(path));
    if (!project.exists()) {
      try {
        queue.insert(
            new Event(
                event.getId(),
                Event.Type.BUILD,
                Event.Status.FAIL,
                String.format("project directory not found at: %s", path)));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      build();
    }
  }

  /** A help function to build a gradle project Hello */
  private void build() {
    try {
      try {
        String command = "gradle build";
        Process p = Runtime.getRuntime().exec(command, null, new File("."));

        Scanner s = new Scanner(p.getErrorStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        if (result.contains("FAILED") && result.contains("compile")) {
          throw new IOException();
        }
        queue.insert(
            new Event(
                event.getId(), Event.Type.BUILD, Event.Status.SUCCESSFUL, "Build succeeded."));
      } catch (Exception e) {
        System.err.println(e);
        queue.insert(
            new Event(
                event.getId(), Event.Type.BUILD, Event.Status.FAIL, "Could not build project"));
      }
    } catch (InterruptedException ie) {
      System.err.println(ie);
    }
  }
}
