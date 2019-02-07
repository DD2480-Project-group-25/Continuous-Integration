package se.kth.dd2480.grp25.ci;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * A job for notifying
 */
public class NotifyJob implements Runnable {
    private Event event;
    private EventQueue queue;

    public NotifyJob(Event event, EventQueue queue) {
        this.event = event;
        this.queue = queue;
    }

    public static Optional<Runnable> offer(Event event, EventQueue queue) {
        if (event.getType() == Event.EventType.TEST || event.getType() == Event.EventType.BUILD) {
            return Optional.of(new NotifyJob(event, queue));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public void run() {
        try{
            String buildSuccessful = "\"All tests passed!\"";
            String buildFail = "\"Build fail\"";
            String testFail = "\"Some tests failed\"";
            URL url = new URL("https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/" + event.getId() + "?access_token=da82f7ee52d65a37086b016d2551a9e939f07a14");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                String params = "";
                if (event.getType() == Event.EventType.BUILD) {
                    if (event.getStatusCode() == Event.StatusCode.FAIL) {
                        params = createParams(buildFail);
                    }
                }
                if (event.getType() == Event.EventType.TEST) {
                    if (event.getStatusCode() == Event.StatusCode.SUCCESSFUL) {
                        params = createParams(buildSuccessful);
                    } else {
                        params = createParams(testFail);
                    }
                }
                os.write(params.getBytes(StandardCharsets.UTF_8));
                System.out.println(conn.getResponseCode());
                os.flush();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private String createParams(String message) {
        String params = "{\"state\":\"success\",\"target_url\":\"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/build/" + event.getId()+ "\",\"description\":" + message +",\"context\":\"own_ci\"}";
        return params;
    }
}
