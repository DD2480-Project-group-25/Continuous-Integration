package se.kth.dd2480.grp25.ci;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * An {@linkplain EventRunner} delegates events from an {@link EventQueue} to instances of {@link
 * JobExaminer}.
 */
public class EventRunner implements Runnable {
  private List<JobExaminer> handlers = new LinkedList<>();
  private EventQueue queue;
  private Thread thread = new Thread(this);
  private boolean stop = false;
  private ExecutorService executor = Executors.newCachedThreadPool();

  /**
   * Create a new {@linkplain EventRunner} that delegates {@link Event}s from the specified {@link
   * EventQueue}.
   *
   * @param queue the queue to draw {@link Event}s
   */
  public EventRunner(EventQueue queue) {
    this.queue = queue;
  }

  /**
   * Start delegating events in a new {@link Thread}.
   *
   * <p>This method may only be called once.
   */
  public void start() {
    thread.start();
  }

  /**
   * Stop offering events and ask job executor to shutdown.
   *
   * <p>The method may only be called once.
   */
  public void stop() {
    stop = true;
    executor.shutdown();
    thread.interrupt();
  }

  /**
   * Wait for the thread offering events and the executor executing jobs to exit.
   *
   * <p>Waiting for the executor to terminate may timeout.
   *
   * @throws InterruptedException if interrupted while waiting
   */
  public void join() throws InterruptedException {
    thread.join();
    executor.awaitTermination(10, TimeUnit.SECONDS);
  }

  /**
   * Register an instance of {@link JobExaminer} with this runner.
   *
   * @param acceptor the handler to register´
   */
  public void registerJobAcceptor(JobExaminer acceptor) {
    handlers.add(acceptor);
  }

  /**
   * Continuously offers {@link Event} instances and submits them to an {@link
   * java.util.concurrent.Executor} if accepted until the thread is interrupted.
   */
  @Override
  public void run() {
    while (!stop) {
      Event event;

      try {
        event = queue.pop();
      } catch (InterruptedException e) {
        break;
      }

      for (JobExaminer acceptor : handlers) {
        acceptor.offer(event).ifPresent(executor::execute);
      }
    }
  }
}
