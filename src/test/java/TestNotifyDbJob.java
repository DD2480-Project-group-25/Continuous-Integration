import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.Event;
import se.kth.dd2480.grp25.ci.EventQueue;
import se.kth.dd2480.grp25.ci.NotifyDbJob;

public class TestNotifyDbJob {

  /** Test that successful notify creates a database entry */
  @Test
  public void testNotifyDbJobSuccessful() {
    try {
      EventQueue queue = new EventQueue();
      Event event =
          new Event(
              "TestNotifyDbJob", Event.Type.NOTIFY, Event.Status.SUCCESSFUL, "All tests passed");
      NotifyDbJob notifyDbJob = new NotifyDbJob(event, queue, true);
      notifyDbJob.run();
      Event generatedEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, generatedEvent.getStatus());
      Assert.assertEquals("Database notified", generatedEvent.getMessage());
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  /** Test that failed notify creates a database entry */
  @Test
  public void testNotifyDbJobNotifyFail() {
    try {
      EventQueue queue = new EventQueue();
      Event event =
          new Event(
              "TestNotifyDbJob2",
              Event.Type.NOTIFY,
              Event.Status.FAIL,
              "Notified that tests failed.");
      NotifyDbJob notifyDbJob = new NotifyDbJob(event, queue, true);
      notifyDbJob.run();
      Event generatedEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, generatedEvent.getStatus());
      Assert.assertEquals("Database notified", generatedEvent.getMessage());
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
