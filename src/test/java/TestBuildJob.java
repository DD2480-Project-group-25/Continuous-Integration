import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.BuildJob;
import se.kth.dd2480.grp25.ci.Event;

public class TestBuildJob {

  @Test()
  public void testBuildFailMessage() {
    Event event = new Event("12345", Event.EventType.BUILD);
    BuildJob build = new BuildJob(event);
    build.run();
    Assert.assertEquals(event.getMessage(), "project directory not found at: ./12345");
    Assert.assertEquals(event.getStatusCode(), Event.StatusCode.FAIL);
  }
}
