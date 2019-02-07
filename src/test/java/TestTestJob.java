import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestTestJob {

  /** There is no cloned repository with the name 123, so exception is thrown. */
  @Test(expected = IllegalStateException.class)
  public void testTestJobException() {
    Event event = new Event("123", Event.Type.TEST);
    TestJob job = new TestJob(event);
    job.run();
  }
}
