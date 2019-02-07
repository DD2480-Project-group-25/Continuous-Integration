import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.Event;

public class TestEvent {

  @Test
  public void testEventConstructor() {
    Event event = new Event("12345abc", Event.Type.CLONE, Event.Status.SUCCESSFUL, null);

    Assert.assertEquals("12345abc", event.getId());
    Assert.assertEquals(Event.Type.CLONE, event.getType());
    Assert.assertEquals(Event.Status.SUCCESSFUL, event.getStatus());
    Assert.assertEquals(null, event.getMessage());
  }
}
