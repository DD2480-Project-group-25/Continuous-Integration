package se.kth.dd2480.grp25.ci;

/** Entry class. */
public class CI {
  public static void main(String[] args) {
    JobAcceptor[] acceptors = {PrintJob::offer};

    EventQueue q = new EventQueue();
    EventRunner d = new EventRunner(q);

    for (JobAcceptor acceptor : acceptors) {
      d.registerJobAcceptor(acceptor);
    }
    d.start();
    for (int i = 0; i < 100; i++) {
      try {
        q.insert(new Event("123", Event.EventType.CLONE));
      } catch (InterruptedException ignored) {
        System.err.println(
            "Interrupted while trying to insert event into queue, event not inserted.");
      }
    }

    try {
      Thread.sleep(10000);
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
