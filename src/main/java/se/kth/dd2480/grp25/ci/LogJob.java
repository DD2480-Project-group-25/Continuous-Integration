package se.kth.dd2480.grp25.ci;

import java.util.Optional;

public class LogJob implements Runnable {
  public static class Examiner extends JobExaminer {

    /**
     * Create a JobExaminer.
     *
     * @param queue the event queue that jobs that this Examiner creates should insert events in.
     */
    public Examiner(EventQueue queue) {
      super(queue);
    }

    @Override
    public Optional<Runnable> offer(Event event) {
      return Optional.of(new LogJob(event));
    }
  }

  private Event event;

  public LogJob(Event event) {
    this.event = event;
  }

  @Override
  public void run() {
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("Commit ID: %s\n", event.getId()));
    builder.append(String.format("Type:      %s\n", event.getType()));
    builder.append(String.format("Status:    %s\n", event.getStatus()));
    builder.append(String.format("Message:   %s\n", event.getMessage()));
    System.out.println(builder.toString());
  }
}
