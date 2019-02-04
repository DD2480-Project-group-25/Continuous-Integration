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
  private String directory;

  public TestJob(Event event) {
    this.event = event;
    // Currently running assignment 1 for testing, won't pass corresponding test on circle ci.
    this.directory = "../DECIDE/";
  }

  public static Optional<Runnable> offer(Event event) {
    if (true) {
      return Optional.of(new TestJob(event));
    } else {
      return Optional.empty();
    }
  }

  /** */
  @Override
  public void run() {
    // Scan for test files that have been built
    DirectoryScanner scanner = new DirectoryScanner();
    scanner.setBasedir(directory);
    scanner.setIncludes(new String[] {"src/test/java**\\*.java"});
    scanner.scan();
    StringBuilder sb = new StringBuilder();
    for (String file : scanner.getIncludedFiles()) {
      sb.append(file.split("java/")[1].split(".java")[0] + " ");
    }
    String[] tests = sb.toString().split("\\s");

    // Open connection to cloned and build repository
    ProjectConnection conn =
        GradleConnector.newConnector().forProjectDirectory(new File(directory)).connect();
    try {
      TestLauncher launcher = conn.newTestLauncher();
      launcher.withJvmTestClasses(tests);
      launcher.run(
          new ResultHandler<Void>() {
            @Override
            public void onComplete(Void result) {
              event.setMessage("All tests passed.");
              System.out.println(event.getMessage());
            }

            @Override
            public void onFailure(GradleConnectionException failure) {
              event.setMessage(failure.getCause().toString());
              System.out.println(event.getMessage());
            }
          });
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      conn.close();
    }
  }
}
