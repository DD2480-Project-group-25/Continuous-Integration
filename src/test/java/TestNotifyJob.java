import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

/** */
public class TestNotifyJob {

  @Test
  public void testNotifyJobTestPassed() {
    EventQueue queue = new EventQueue();
    Event event =
        new Event(
            "321c3452974ccfa0d4fb11d7b584f68472fcbabc",
            Event.Type.TEST,
            Event.Status.SUCCESSFUL,
            "Nothing");
    NotifyJob job = new NotifyJob(event, queue);
    job.run();
    try {
      Event notifyEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, notifyEvent.getStatus());
      Assert.assertEquals("Notified that build was successful.", notifyEvent.getMessage());
    } catch (InterruptedException e) {
      System.out.println(e);
      Assert.fail();
    }
  }

  @Test
  public void testNotifyJobTestFailed() {
    EventQueue queue = new EventQueue();
    Event event =
        new Event(
            "fdd1f1e31c6f67d23e1134b34d2cc184f1c5f105",
            Event.Type.TEST,
            Event.Status.FAIL,
            "Failed on LIC0 tests");
    NotifyJob job = new NotifyJob(event, queue);
    job.run();
    try {
      Event notifyEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, notifyEvent.getStatus());
      Assert.assertEquals("Notified that tests failed.", notifyEvent.getMessage());
    } catch (InterruptedException e) {
      System.out.println(e);
      Assert.fail();
    }
  }

  @Test
  public void testNotifyJobBuildFailed() {
    EventQueue queue = new EventQueue();
    Event event =
        new Event(
            "fdd1f1e31c6f67d23e1134b34d2cc184f1c5f105", Event.Type.BUILD, Event.Status.FAIL, "");
    NotifyJob job = new NotifyJob(event, queue);
    job.run();
    try {
      Event notifyEvent = queue.pop();
      Assert.assertEquals(Event.Status.SUCCESSFUL, notifyEvent.getStatus());
      Assert.assertEquals("Notified that build failed.", notifyEvent.getMessage());
    } catch (InterruptedException e) {
      System.out.println(e);
      Assert.fail();
    }
  }

  @Test
  public void testNotifyJobFailed() {
    EventQueue queue = new EventQueue();
    Event event = new Event("wrongCommit1", Event.Type.TEST, Event.Status.SUCCESSFUL, "");
    NotifyJob job = new NotifyJob(event, queue);
    job.run();
    try {
      Event notifyEvent = queue.pop();
      Assert.assertEquals(Event.Status.FAIL, notifyEvent.getStatus());
      Assert.assertEquals("Unprocessable entity", notifyEvent.getMessage());
    } catch (InterruptedException e) {
      System.out.println(e);
      Assert.fail();
    }
  }
}
