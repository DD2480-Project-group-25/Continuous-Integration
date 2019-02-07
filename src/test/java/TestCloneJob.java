import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestCloneJob {
  @Test
  public void testCloneJobTestPass() {
    EventQueue queue = new EventQueue();
    Event event =
        new Event(
            "dbbf1908486644822e0189be7b88678c3aec7313",
            Event.Type.CLONE,
            Event.Status.SUCCESSFUL,
            "Nothing",
            "DD2480-Project-group-25/Continuous-Integration");
    CloneJob job = new CloneJob(event, queue);
    job.run();
    try {
      Event cloneEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, cloneEvent.getStatus());
      Assert.assertEquals("The repository is cloned successfully", cloneEvent.getMessage());
    } catch (InterruptedException e) {
      System.out.println(e);
      Assert.fail();
    }
  }

  @Test
  public void testCloneJobFail() throws InterruptedException {
    EventQueue queue = new EventQueue();
    Event event =
        new Event("wrong_id", Event.Type.CLONE, Event.Status.SUCCESSFUL, "Nothing", "bcbb");
    CloneJob job = new CloneJob(event, queue);
    job.run();
    Event cloneEvenet = queue.pop();
    Assert.assertEquals(Event.Type.CLONE, cloneEvenet.getType());
    Assert.assertEquals(Event.Status.FAIL, cloneEvenet.getStatus());
    Assert.assertEquals("Could not clone repository", cloneEvenet.getMessage());
  }
}
