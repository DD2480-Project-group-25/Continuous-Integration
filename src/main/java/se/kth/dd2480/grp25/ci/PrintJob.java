package se.kth.dd2480.grp25.ci;

import java.util.Optional;

/** A job that prints every event to stdout. */
public class PrintJob implements Runnable {
  /**
   * This functions decides if it wants to accept an event and offer a {@link PrintJob}.
   *
   * <p>This function confirms to the {@link JobAcceptor} interface.
   *
   * @param event the event offered to this function to accept or decline
   * @return an print job represented by a {@link Runnable} if accepted
   */
  public static Optional<Runnable> offer(Event event) {
    // 1. Decide if we want to handle this event
    if (true) {
      // 2. If we do, return a job handler
      return Optional.of(new PrintJob(event));
    } else {
      // 3. If we don't, return nothing
      return Optional.empty();
    }
  }

  private Event event;

  private PrintJob(Event event) {
    this.event = event;
  }

  /** Prints the given event when run. */
  @Override
  public void run() {
    System.out.println(event);
  }
}
