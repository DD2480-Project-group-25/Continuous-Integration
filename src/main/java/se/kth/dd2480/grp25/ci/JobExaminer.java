package se.kth.dd2480.grp25.ci;

import java.util.Optional;

/**
 * The {@linkplain JobExaminer} interface describes a function that decides if it wants to offer a
 * {@link Runnable} for a given {@link Event}.
 */
public abstract class JobExaminer {
  protected EventQueue queue;

  /**
   * Create a JobExaminer.
   *
   * @param queue the event queue that jobs that this Examiner creates should insert events in.
   */
  public JobExaminer(EventQueue queue) {
    this.queue = queue;
  }

  /**
   * This method is used to offer an {@code JobHandler} an {@link Event}.
   *
   * <p>If the handler accepts to process the event, then should the handler create a new thread to
   * process it in.
   *
   * @param event the offered event
   */
  public abstract Optional<Runnable> offer(Event event);
}
