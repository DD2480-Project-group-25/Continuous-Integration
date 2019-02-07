package se.kth.dd2480.grp25.ci;

import sun.net.www.http.HttpClient;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            String command = "curl -X POST -H ‘Content-Type: application/json’ --data ‘{“state”: “success”, “description“: “Build successful“}’ https://<token>:x-oauth-basic@api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/" + event.getId();
            Process p = Runtime.getRuntime().exec(command, null, new File("."));
        }catch(Exception e){
            System.out.println("Exception");
        }
    }

    public static void main(String[] args){

    }
}
