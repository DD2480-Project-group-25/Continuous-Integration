package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.util.Optional;
import org.apache.tools.ant.DirectoryScanner;
import org.gradle.tooling.*;

/**
 * A test job that runs all tests in a cloned repository, given that the tests are located according
 * to the following structure: [path to project dir]/src/test/java/[all tests]
 */
public class TestJob implements Runnable {
  /**
   * A {@link JobExaminer} that creates instances of {@link TestJob}.
   *
   * <p>Inspect the docs for {@link JobExaminer}.
   */
  public static class Examiner extends JobExaminer {

    public Examiner(EventQueue queue) {
      super(queue);
    }

    @Override
    public Optional<Runnable> offer(Event event) {
      return event.getType() == Event.Type.BUILD && event.getStatus() == Event.Status.SUCCESSFUL
          ? Optional.of(new TestJob(event, super.queue))
          : Optional.empty();
    }
  }

  private Event event;
  private EventQueue queue;

  public TestJob(Event event, EventQueue queue) {
    this.event = event;
    this.queue = queue;
  }

  /**
   * Scans the project directory for the name of the test files. Creates a connection to the gradle
   * project, starts a TestLauncher that runs all test files of the gradle project. If any tests
   * fail an error string is set in the Event.
   */
  @Override
  public void run() {
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir("./" + event.getId());
    scanner.setIncludes(new String[] {"src/test/java**\\*.java"});
    scanner.scan();
    StringBuilder sb = new StringBuilder();
    for (String file : scanner.getIncludedFiles()) {
      sb.append(file.split("java/")[1].split(".java")[0] + " ");
    }
    String[] tests = sb.toString().split("\\s");

    ProjectConnection conn =
        GradleConnector.newConnector()
            .forProjectDirectory(new File("./" + event.getId()))
            .connect();
    try {
      TestLauncher launcher = conn.newTestLauncher();
      launcher.withJvmTestClasses(tests);
      launcher.run(
          new ResultHandler<Void>() {
            @Override
            public void onComplete(Void result) {
              try {
                queue.insert(
                    new Event(
                        event.getId(),
                        Event.Type.TEST,
                        Event.Status.SUCCESSFUL,
                        "All tests passed."));
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }

            @Override
            public void onFailure(GradleConnectionException failure) {
              try {
                queue.insert(
                    new Event(
                        event.getId(),
                        Event.Type.TEST,
                        Event.Status.FAIL,
                        failure.getCause().toString()));
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
    } catch (Exception e) {
      try {
        queue.insert(new Event(event.getId(), Event.Type.TEST, Event.Status.FAIL, e.getMessage()));
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    } finally {
      conn.close();
    }
  }
}
