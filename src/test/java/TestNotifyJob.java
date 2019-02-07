import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

/**
 *
 */
public class TestNotifyJob {
    @Test
    public void testNotifyJobException() {
        EventQueue queue = new EventQueue();
        Event event = new Event("321c3452974ccfa0d4fb11d7b584f68472fcbabc", Event.EventType.TEST);
        NotifyJob job = new NotifyJob(event, queue);
        job.run();

    }
}
