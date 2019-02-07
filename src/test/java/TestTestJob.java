import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

public class TestTestJob {

  /** There is no cloned repository with the name 123, so exception is thrown. */
  @Test(expected = IllegalStateException.class)
  public void testTestJobException() {
    EventQueue queue = new EventQueue();
    Event event = new Event("123", Event.Type.BUILD, Event.Status.SUCCESSFUL, null);
    TestJob job = new TestJob(event, queue);
    job.run();
  }
}
