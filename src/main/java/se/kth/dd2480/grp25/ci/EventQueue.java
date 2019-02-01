package se.kth.dd2480.grp25.ci;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * An blocking thread safe event queue.
 *
 * <p>This queue can at most hold {@code Integer.MAX_VALUE} events at once.
 */
public class EventQueue {
  private BlockingQueue<Event> queue = new LinkedBlockingQueue<>();

  /** Create an {@code EventQueue}. */
  public EventQueue() {}

  /**
   * Insert the specified into this queue.
   *
   * <p>This method will block if the queue already contains {@code Integer.MAX_VALUE}¨ events.
   *
   * @param event - the element to insert
   * @throws InterruptedException if interrupted while waiting
   * @throws NullPointerException if the specified element is null
   */
  public void insert(Event event) throws InterruptedException {
    queue.put(event);
  }

  /**
   * Pop the event first in the queue, waiting if there is no event.
   *
   * @return the first event in the queue
   * @throws InterruptedException if interrupted while waiting
   */
  public Event pop() throws InterruptedException {
    return queue.take();
  }
}
