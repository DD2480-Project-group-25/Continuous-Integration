import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestCleanupJob {

  /** Test that an existing directory is deleted correctly */
  @Test
  public void testDeleteSuccessful() {
    try {
      String command = "mkdir testDirectory";
      Runtime.getRuntime().exec(command, null, new File("."));

      Event event = new Event("testDirectory", Event.Type.CLEANUP);
      CleanupJob cleanupJob = new CleanupJob(event);
      cleanupJob.run();
      Assert.assertEquals(Event.Status.SUCCESSFUL, event.getStatus());
      Assert.assertEquals("Cloned repository was successfully deleted", event.getMessage());
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /** If a directory doesn't exist the job should fail */
  @Test
  public void testDeleteFail() {
    Event event = new Event("testDirectory", Event.Type.CLEANUP);
    CleanupJob cleanupJob = new CleanupJob(event);
    cleanupJob.run();
    Assert.assertEquals(Event.Status.FAIL, event.getStatus());
    Assert.assertEquals("CI Server error: directory doesn't exist", event.getMessage());
  }
}
