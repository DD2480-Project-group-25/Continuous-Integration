package se.kth.dd2480.grp25.ci;

/** This class represents a webhook event */
public class WebHookEvent extends Event {
  WebHookEvent(String id, EventType type) {
    super(id, type);
  } // a constructor
}
