package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.util.Optional;
import org.gradle.tooling.*;

/** A job that builds a project given by an Event object of EventType 'BUILD'. */
public class BuildJob implements Runnable {
  /**
   * This functions decides if it wants to accept an event and offer a {@link BuildJob}.
   *
   * <p>This function confirms to the {@link JobAcceptor} interface.
   *
   * @param event the event offered to this function to accept or decline
   * @return a build job represented by a {@link Runnable} if accepted
   */
  public static Optional<Runnable> offer(Event event) {
    // 1. Decide if we want to handle this event
    if (event.getType() == Event.EventType.BUILD) {
      // 2. If we do, return a job handler
      return Optional.of(new BuildJob(event));
    } else {
      // 3. If we don't, return nothing
      return Optional.empty();
    }
  }

  private Event event;
  private String path;

  public BuildJob(Event event) {
    this.event = event;
    this.path = "./" + event.getId();
  }

  /**
   * Opens a connection to a project through given path and launches a build via help function
   * 'launch()'
   */
  @Override
  public void run() {

    File project = (new File(path));

    if (!project.exists()) {
      event.setStatusCode(Event.StatusCode.FAIL);
      event.setMessage("project directory not found at: " + path);
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
              event.setStatusCode(Event.StatusCode.SUCCESSFUL);
              event.setMessage("Build succeeded.");
            }

            public void onFailure(GradleConnectionException failure) {
              event.setStatusCode(Event.StatusCode.FAIL);
              if (failure instanceof BuildException) {
                event.setMessage("Couldn't build project at " + path);
              } else {
                event.setMessage(
                    "Build failed because of unexpected exception: " + failure.toString());
              }
            }
          });
    } catch (IllegalStateException e) {
      event.setStatusCode(Event.StatusCode.FAIL);
      event.setMessage("Connection was closed during build.");

    } catch (Exception e) {
      event.setStatusCode(Event.StatusCode.FAIL);
      event.setMessage("Build failed with exception: " + e);
    } finally {
      con.close();
    }
  }
}
