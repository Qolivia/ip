package saru;

import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate by;

    public Deadline(String dscp, LocalDate by) {
        super(dscp);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D] [" + getStatus() + "] " + dscp + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + dscp + " | " + by;
    }

}
