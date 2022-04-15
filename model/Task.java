package model;

public class Task {
    public int pid; // Process ID
    public int dt; // Deadline time
    public int wt; //wait time
    public int bt; //burst time
    public int at; //arrival time
    public int tt; // turnaround time
    public Task(int pid, int dt, int bt, int at)
    {
        this.pid = pid;
        this.dt = dt;
        this.at = at;
        this.bt = bt;
        int tt = -1;
        int wt = -1;
    }
    public int getWaitingTime(int totalTime){
        this.wt = getTurnaroundTime(totalTime) - this.bt;
        if(this.wt<0){
            this.wt = 0;
        }
        return wt;
    }

    int getTurnaroundTime(int totalTime){
        this.tt = totalTime-this.at;
        return tt;
    }
    @Override
    public String toString() {
        return "" + this.pid+ "";
    }
}
