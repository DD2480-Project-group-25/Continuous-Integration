package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.util.Optional;
import org.gradle.tooling.*;

/** A job that builds a project given by an Event object of EventType 'BUILD'. */
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
   * Opens a connection to a project through given path and launches a build via help function
   * 'launch()'
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
      ProjectConnection connection =
          GradleConnector.newConnector().forProjectDirectory(project).connect();

      launch(connection);
    }
  }

  /** A help function to launch and execute a build on a connection. */
  private void launch(ProjectConnection con) {
    try {
      BuildLauncher build = con.newBuild();
      build.run(
          new ResultHandler<Void>() {
            public void onComplete(Void result) {
              try {
                queue.insert(
                    new Event(
                        event.getId(), Event.Type.BUILD, Event.Status.SUCCESSFUL, "Build succeeded."));
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }

            public void onFailure(GradleConnectionException failure) {
              String message;
              if (failure instanceof BuildException) {
                message = "Couldn't build project at " + path;
              } else {
                message = "Build failed because of unexpected exception: " + failure.toString();
              }
              try {
                queue.insert(
                    new Event(event.getId(), Event.Type.BUILD, Event.Status.FAIL, message));
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
    } catch (IllegalStateException e) {
      try {
        queue.insert(
            new Event(
                event.getId(),
                Event.Type.BUILD,
                Event.Status.FAIL,
                "Connection was closed during build."));
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    } finally {
      con.close();
    }
  }
}
