package se.kth.dd2480.grp25.ci;

/** An {@code Event} instance represents some important CI event. */
public class Event {
  public enum StatusCode {
    SUCCESSFUL,
    FAIL,
    NOTISSUED
  }

  public StatusCode code = StatusCode.NOTISSUED;
}
