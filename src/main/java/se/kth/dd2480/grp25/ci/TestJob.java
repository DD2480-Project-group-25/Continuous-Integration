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
  private Event event;

  public TestJob(Event event) {
    this.event = event;
  }

  public static Optional<Runnable> offer(Event event) {
    if (event.getType() == Event.EventType.TEST) {
      return Optional.of(new TestJob(event));
    } else {
      return Optional.empty();
    }
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
              event.setMessage("All tests passed.");
              event.setStatusCode(Event.StatusCode.SUCCESSFUL);
            }

            @Override
            public void onFailure(GradleConnectionException failure) {
              event.setMessage(failure.getCause().toString());
              event.setStatusCode(Event.StatusCode.FAIL);
            }
          });
    } catch (Exception e) {
      event.setMessage("CI Server error: " + e);
      event.setStatusCode(Event.StatusCode.FAIL);
    } finally {
      conn.close();
    }
  }
}
