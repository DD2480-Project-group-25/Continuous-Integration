package se.kth.dd2480.grp25.ci;

import java.util.Optional;

/**
 * The {@linkplain JobAcceptor} interface describes a function that decides if it wants to offer a
 * {@link Runnable} for a given {@link Event}.
 */
public interface JobAcceptor {
  /**
   * This method is used to offer an {@code JobHandler} an {@link Event}.
   *
   * <p>If the handler accepts to process the event, then should the handler create a new thread to
   * process it in.
   *
   * @param event the offered event
   */
  Optional<Runnable> offer(Event event);
}
