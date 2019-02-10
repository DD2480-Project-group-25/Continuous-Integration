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
      Event buildEvent =
          new Event("TestNotifyDb", Event.Type.BUILD, Event.Status.FAIL, "Could not build project");
      Event event =
          new Event(
              "TestNotifyDbJob",
              Event.Type.NOTIFY,
              Event.Status.SUCCESSFUL,
              "All tests passed",
              buildEvent);
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
      Event testEvent =
          new Event(
              "TestNotifyDb", Event.Type.TEST, Event.Status.FAIL, "Failed on test TestJsonParser");
      Event event =
          new Event(
              "TestNotifyDbJob2",
              Event.Type.NOTIFY,
              Event.Status.SUCCESSFUL,
              "Notified that tests failed.",
              testEvent);
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
