package se.kth.dd2480.grp25.ci;

import java.util.Calendar;

/** Entry class. */
public class CI {
  public static void main(String[] args) {
    EventQueue q = new EventQueue();
    JobExaminer[] acceptors = {
      new PrintJob.Examiner(q),
      new CloneJob.Examiner(q),
      new TestJob.Examiner(q),
      new BuildJob.Examiner(q),
      new CleanupJob.Examiner(q),
      new LogJob.Examiner(q),
    };
    EventRunner d = new EventRunner(q);

    for (JobExaminer acceptor : acceptors) {
      d.registerJobAcceptor(acceptor);
    }
    d.start();
    try {
      q.insert(
          new Event(
              Long.toHexString(System.nanoTime()),
              Event.Type.STARTUP,
              Event.Status.SUCCESSFUL, "1"));
    } catch (InterruptedException ignored) {
      System.err.println(
          "Interrupted while trying to insert event into queue, event not inserted.");
    }

    try {
      Thread.sleep(5000);
    } catch (InterruptedException ignored) {
      System.err.println("Interrupted while sleeping.");
    }

    d.stop();
    try {
      d.join();
    } catch (InterruptedException ignored) {
      System.err.println("Interrupted while waiting for event runner to stop.");
    }
  }
}
