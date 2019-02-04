import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestTestJob {

  @Test
  public void testTestJobRun() {
    Event event = new Event();
    TestJob job = new TestJob(event);
    job.run();
    Assert.assertEquals("All tests passed.", event.getMessage());
  }
}
