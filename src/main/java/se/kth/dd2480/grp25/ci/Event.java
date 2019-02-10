package se.kth.dd2480.grp25.ci;

/**
 * An {@code Event} instance represents that some important CI event has completed.
 *
 * <p>An event should be generated when an job is completed. An event holds information about what
 * commit ID it pertains to, what type of job generated it, if that job was successful or not, and
 * some message from that job. An event is immutable.
 */
public class Event {

  /** An enum describing the type of an {@linkplain Event}. */
  public enum Type {
    WEB_HOOK,
    CLONE,
    BUILD,
    TEST,
    NOTIFY,
    NOTIFYDB,
    CLEANUP,
    PRINT,
    STARTUP
  }

  /** An enum describing the status of an {@linkplain Event}. */
  public enum Status {
    SUCCESSFUL,
    FAIL
  }

  private final String id;
  private final Type type;
  private final Status code;
  private final String message;
  private final String repository;
  private final String branch;
  private final Event logEvent;

  /**
   * Constructor overloading for create event
   *
   * @param id the commit id the event pertains to.
   * @param type the type of job that completed and generated this event.
   * @param status indicates if the job that generated this event was successful or not.
   * @param message an message from the job that generated this event.
   */
  public Event(String id, Type type, Status status, String message) {
    this(id, type, status, message, "", "", null);
  }

  public Event(
      String id, Type type, Status status, String message, String repository, String branch) {
    this(id, type, status, message, repository, branch, null);
  }

  public Event(String id, Type type, Status status, String message, Event event) {
    this(id, type, status, message, "", "", event);
  }
  /**
   * Create an event.
   *
   * @param id the commit id the event pertains to.
   * @param type the type of job that completed and generated this event.
   * @param status indicates if the job that generated this event was successful or not.
   * @param message an message from the job that generated this event.
   * @param repository the name of the repository at github.
   * @param message the branch name of the commit.
   */
  public Event(
      String id,
      Type type,
      Status status,
      String message,
      String repository,
      String branch,
      Event logEvent) {
    this.id = id;
    this.type = type;
    this.code = status;
    this.message = message;
    this.repository = repository;
    this.branch = branch;
    this.logEvent = logEvent;
  }

  /**
   * Get the commit ID pertaining to this event.
   *
   * @return the commit ID pertaining to this event.
   */
  public String getId() {
    return id;
  }

  /**
   * Get the type of job that generated this event.
   *
   * @return the type of job that generated this event.
   */
  public Type getType() {
    return type;
  }

  /**
   * Get the message from the job that generated this event.
   *
   * @return the message from the job that generated this event.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Get the status of the job that generated this event.
   *
   * @return the status of the job that generated this event.
   */
  public Status getStatus() {
    return code;
  }

  /**
   * Get the repository name of the job that generated this event.
   *
   * @return the repository name of the job that generated this event.
   */
  public String getRepository() {
    return repository;
  }

  /**
   * Get the name of the branch for the current job
   *
   * @return the name of the branch for the latest commit
   */
  public String getBranch() {
    return branch;
  }

  /** Get the event that is supposed to be logged to database */
  public Event getLogEvent() {
    return logEvent;
  }
}
