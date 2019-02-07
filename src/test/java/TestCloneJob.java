import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestCloneJob {

  @Test
  public void testCloneJobFail() throws InterruptedException {
    EventQueue queue = new EventQueue();
    Event event =
        new Event("wrong_id", Event.Type.CLONE, Event.Status.SUCCESSFUL, "Nothing", "bcbb");
    CloneJob job = new CloneJob(event, queue);
    job.run();
    Event cloneEvent = queue.pop();
    Assert.assertEquals(Event.Type.CLONE, cloneEvent.getType());
    Assert.assertEquals(Event.Status.FAIL, cloneEvent.getStatus());
    Assert.assertEquals("Could not clone repository", cloneEvent.getMessage());
  }
}
