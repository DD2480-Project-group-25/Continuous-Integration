package se.kth.dd2480.grp25.ci;

import java.io.File;
import java.util.Optional;
import org.gradle.tooling.*;

/** A job that prints every event to stdout. */
public class BuildJob implements Runnable {
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
            return Optional.of(new BuildJob(event));
        } else {
            // 3. If we don't, return nothing
            return Optional.empty();
        }
    }

    private Event event;

    private BuildJob(Event event) {
        this.event = event;
    }

    /** Prints the given event when run. */
    @Override
    public void run() {
        ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(new File("."))
                .connect();

        try {
            BuildLauncher build = connection.newBuild();

            //select tasks to run:
            build.forTasks("clean", "test");

            //kick the build off:
            build.run();
        } finally {
            connection.close();
        }
    }
}
