import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.BuildJob;
import se.kth.dd2480.grp25.ci.Event;

/** Testing functionality of class 'BuildJob'. */
public class TestBuildJob {

  /** Testing that build fails and sends the right message if project directory doesn't exist. */
  @Test()
  public void testBuildFailMessage() {
    Event event = new Event("12345", Event.EventType.BUILD);
    BuildJob build = new BuildJob(event);
    build.run();
    Assert.assertEquals(event.getMessage(), "project directory not found at: ./12345");
    Assert.assertEquals(event.getStatusCode(), Event.StatusCode.FAIL);
  }
}
