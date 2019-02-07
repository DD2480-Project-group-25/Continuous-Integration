package se.kth.dd2480.grp25.ci;

import java.util.Optional;

/** A job that prints every event to stdout. */
public class PrintJob implements Runnable {
  /**
   * A {@link JobExaminer} that creates instances of {@link PrintJob}.
   *
   * <p>Inspect the docs for {@link JobExaminer}.
   */
  public static class Examiner extends JobExaminer {
    public Examiner(EventQueue queue) {
      super(queue);
    }

    /**
     * This functions decides if it wants to accept an event and offer a {@link PrintJob}.
     *
     * <p>This function confirms to the {@link JobExaminer} interface.
     *
     * @param event the event offered to this function to accept or decline
     * @return an print job represented by a {@link Runnable} if accepted
     */
    @Override
    public Optional<Runnable> offer(Event event) {
      return event.getType() == Event.Type.STARTUP || event.getType() == Event.Type.PRINT
          ? Optional.of(new PrintJob(event, super.queue))
          : Optional.empty();
    }
  }

  private Event event;
  private EventQueue queue;

  private PrintJob(Event event, EventQueue queue) {
    this.event = event;
    this.queue = queue;
  }

  /** Prints the given event when run. */
  @Override
  public void run() {
    System.out.println(String.format("%5s", event.getMessage()));
    try {
      queue.insert(
          new Event(
              Long.toHexString(System.nanoTime()),
              Event.Type.PRINT,
              Event.Status.SUCCESSFUL,
              "" + (Integer.parseInt(event.getMessage()) + 1)));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
