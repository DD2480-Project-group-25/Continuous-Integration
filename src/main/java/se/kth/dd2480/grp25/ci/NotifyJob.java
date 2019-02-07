package se.kth.dd2480.grp25.ci;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
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
            String command = "curl -X POST -H \'Content-Type:application/json\' --data \'{\"state\":\"success\",\"target_url\":\"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/build/321c3452974ccfa0d4fb11d7b584f68472fcbabc\",\"description\":\"BuildSuccessful\",\"context\":\"own_ci\"}\' https://:x-oauth-basic@api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/321c3452974ccfa0d4fb11d7b584f68472fcbabc\n";
            String params = "{\"state\":\"success\",\"target_url\":\"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/build/321c3452974ccfa0d4fb11d7b584f68472fcbabc\",\"description\":\"BuildSuccessful\",\"context\":\"own_ci\"}";
            System.out.println(command);
            URL url = new URL("https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/321c3452974ccfa0d4fb11d7b584f68472fcbabc");
            //URL url = new URL("https://postman-echo.com/post");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
                System.out.println(conn.getResponseCode());
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
