import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestCleanupJob {

  /** Test that an existing directory is deleted correctly */
  @Test
  public void testDeleteSuccessful() throws IOException, InterruptedException {
    String command = "mkdir testDirectory";
    Runtime.getRuntime().exec(command, null, new File("."));

    EventQueue queue = new EventQueue();
    Event event = new Event("testDirectory", Event.Type.TEST, Event.Status.SUCCESSFUL, null);
    CleanupJob cleanupJob = new CleanupJob(event, queue);
    cleanupJob.run();
    Event generatedEvent = queue.pop();
    Assert.assertEquals(Event.Type.CLEANUP, generatedEvent.getType());
    Assert.assertEquals(Event.Status.SUCCESSFUL, generatedEvent.getStatus());
    Assert.assertEquals("Cloned repository was successfully deleted", generatedEvent.getMessage());
  }

  /** If a directory doesn't exist the job should fail */
  @Test
  public void testDeleteNonExistingDirectory() throws InterruptedException {
    EventQueue queue = new EventQueue();
    Event event = new Event("testDirectory", Event.Type.TEST, Event.Status.SUCCESSFUL, null);
    CleanupJob cleanupJob = new CleanupJob(event, queue);
    cleanupJob.run();
    Event generatedEvent = queue.pop();
    Assert.assertEquals(Event.Type.CLEANUP, generatedEvent.getType());
    Assert.assertEquals(Event.Status.SUCCESSFUL, generatedEvent.getStatus());
    Assert.assertEquals("Directory doesn't exist, nothing to cleanup", generatedEvent.getMessage());
  }
}
