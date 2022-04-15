package edf;

// coded with reference to https://github.com/elzoughby/EDF-scheduling

public class Task implements Comparable<Task> {

    private static int count = 0;
    private final int eT;
    private final int period;
    private final int id;
    private final String name;


    public Task(int eT, int period) {
        this.eT = eT;
        this.period = period;
        this.id = count;
        this.name = "T" + Integer.toString(count);
        count++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return this.id;
    }

    public int getET() {
        return this.eT;
    }

    public int getPeriod() {
        return this.period;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int compareTo(Task other) {
            return Integer.compare(this.getPeriod(), other.getPeriod());
    }
}