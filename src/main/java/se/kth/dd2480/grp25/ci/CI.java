package se.kth.dd2480.grp25.ci;

import java.io.IOException;

/** Entry class. */
public class CI {

  /**
   * Entry point.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    final int port = 8000;
    EventQueue q = new EventQueue();
    CiServer server = null;
    try {
      server = new CiServer(q, port);
    } catch (IOException e) {
      System.err.println("Can't start web server!");
      e.printStackTrace();
      System.exit(-1);
    }

    JobExaminer[] acceptors = {
      new CloneJob.Examiner(q),
      new TestJob.Examiner(q),
      new BuildJob.Examiner(q),
      new CleanupJob.Examiner(q),
      new LogJob.Examiner(q),
      new NotifyJob.Examiner(q),
      new NotifyDbJob.Examiner(q)
    };
    EventRunner d = new EventRunner(q);

    for (JobExaminer acceptor : acceptors) {
      d.registerJobAcceptor(acceptor);
    }

    d.start();
  }
}
