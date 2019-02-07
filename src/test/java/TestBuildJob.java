import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.BuildJob;
import se.kth.dd2480.grp25.ci.Event;
import se.kth.dd2480.grp25.ci.EventQueue;

/** Testing functionality of class 'BuildJob'. */
public class TestBuildJob {

  /** Testing that build fails and sends the right message if project directory doesn't exist. */
  @Test()
  public void testBuildFailMessage() throws InterruptedException {
    EventQueue queue = new EventQueue();
    Event event = new Event("12345", Event.Type.CLONE, Event.Status.SUCCESSFUL, "");
    BuildJob build = new BuildJob(event, queue);
    build.run();
    Event generatedEvent = queue.pop();
    Assert.assertEquals(Event.Type.BUILD, generatedEvent.getType());
    Assert.assertEquals("project directory not found at: ./12345", generatedEvent.getMessage());
    Assert.assertEquals(Event.Status.FAIL, generatedEvent.getStatus());
  }
}
