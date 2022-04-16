package edf;
import model.Task;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    public static Scheduled schedule(List<Task> tasks, int N) {

        Scheduled finalResult = new Scheduled();
        List<Object> waiting = new ArrayList<Object>();
        int lcm = calculateTheLCM(tasks);

        for(int interval = 0;interval < lcm; interval++) {
            finalResult.deadlines.add(interval, new ArrayList<>());

            for(Task t: tasks) {
                if(interval % t.pt == 0) {
                    if(!waiting.contains(interval + t.pt)){
                        waiting.add(interval + t.pt, new ArrayList<>());
                    }

                    for(int i = 0; i < t.bt; i++) {
                        waiting.get(interval + t.pt);
                    }

                    finalResult.deadlines.get(interval).add(t);
                }
            }
        }

        for(Task task: finalResult.tasks){
            System.out.print(task + " ");
        }
        System.out.println("\n");
        System.out.println("Average wait time EDF: " + getAvgWaitingTime(tasks,N));
        return finalResult;
    }

    public static class Scheduled {
        List<Task> tasks = new ArrayList<>();
        List<List<Task>> deadlines = new ArrayList<>();
    }

    public static float getAvgWaitingTime(List<Task> taskList, int N){
        int avg;
        int totalWT  = 0;
        int totalTime = 0;
        for (Task task:taskList) {
            totalTime = totalTime + task.bt;
            totalWT = totalWT + task.getWaitingTime(totalTime);
        }
        avg= totalWT/N;
        return avg;
    }

    // coded with reference to https://www.baeldung.com/java-least-common-multiple
    // I had to consult online code examples and resources because I was unsure how to implement LCM calculation
    // when the input was greater than two numbers
    public static int calculateTheLCM(List<Task> tasks) {
        boolean flag = true;
        int lcm = tasks.get(0).pt;
        for(int i =0 ; i<1000; i++){
            for(Task t : tasks) {
                if(lcm % t.pt != 0) {
                    flag = true;
                    break;
                }
                flag = false;
            }
            if(flag == true) {
                lcm = lcm + 1;
            }
        }
        return lcm;
    }
}