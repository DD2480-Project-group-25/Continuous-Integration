import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestCloneJob {
  /** Test that a repo is cloned successfully */
  @Test
  public void testCloneSuccessful() throws IOException, InterruptedException {

    EventQueue queue = new EventQueue();
    Event event =
        new Event(
            "testDirectory",
            Event.Type.CLONE,
            Event.Status.SUCCESSFUL,
            null,
            "DD2480-Project-group-25/DECIDE",
            "master");
    CloneJob job = new CloneJob(event, queue);
    job.run();

    Event generatedEvent = queue.pop();
    Assert.assertEquals(Event.Type.CLONE, generatedEvent.getType());
    Assert.assertEquals(Event.Status.SUCCESSFUL, generatedEvent.getStatus());
    Assert.assertEquals("The repository is cloned successfully", generatedEvent.getMessage());
    // remove the cloned directory
    String command = "rm -rf testDirectory";
    Runtime.getRuntime().exec(command, null, new File(".")).waitFor();
  }
}
