package se.kth.dd2480.grp25.ci;

/** An {@code Event} instance represents some important CI event. */
public class Event {
  public enum EventType {
    CLONE,
    BUILD,
    TEST,
    NOTIFY,
    CLEANUP
  }

  public enum StatusCode {
    SUCCESSFUL,
    FAIL,
    NOTISSUED
  }

  private final String id;
  private final EventType type;
  private StatusCode code;
  private String message;

  public Event(String id, EventType type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public EventType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public StatusCode getStatusCode() {
    return code;
  }

  public void setStatusCode(StatusCode code) {
    this.code = code;
  }
}
