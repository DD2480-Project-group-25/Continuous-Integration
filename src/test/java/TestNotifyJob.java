import org.junit.Test;
import se.kth.dd2480.grp25.ci.*;

/**
 *
 */
public class TestNotifyJob {
    @Test
    public void testNotifyJobException() {
        EventQueue queue = new EventQueue();
        Event event = new Event("ea6083b9dae69635077cbe4bd560c425e46d28ea", Event.EventType.TEST);
        NotifyJob job = new NotifyJob(event, queue);
        job.run();
    }
}
