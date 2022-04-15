package edf;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// coded with reference to https://github.com/elzoughby/EDF-scheduling

public class Scheduler {

    public static ScheduledTasks schedule(final List<Task> taskList, int N) {

        int lcm = calcLCM(taskList);
        ScheduledTasks out = new ScheduledTasks();
        Map<Integer, List<Task>> waitingMap = new HashMap<>();


        for(int timeUnit = 0; timeUnit < lcm; timeUnit++) {

            out.getDeadlinesList().add(timeUnit, new ArrayList<>());

            //add iterative tasks into the waiting list
            for(Task t : taskList)
                if(timeUnit % t.pt == 0) {

                    if(! waitingMap.containsKey(timeUnit + t.pt))
                        waitingMap.put(timeUnit + t.pt, new ArrayList<>());

                    for(int i = 0; i < t.bt; i++)
                        waitingMap.get(timeUnit + t.pt).add(t);

                    out.getDeadlinesList().get(timeUnit).add(t);
                }

            if(! waitingMap.isEmpty()) {
                //the highest priority task has the minimum period
                Integer minKey = waitingMap.keySet().stream().min(Integer::compareTo).get();
                out.getTaskList().add(waitingMap.get(minKey).get(0));
                waitingMap.get(minKey).remove(0);
                if(waitingMap.get(minKey).isEmpty())
                    waitingMap.remove(minKey);
            } else
                out.getTaskList().add(null);

        }

        out.getDeadlinesList().remove(0);
        out.getDeadlinesList().add(new ArrayList<>());
        for(Task task:out.getTaskList()){
            System.out.print(task + " ");
        }
        System.out.println("\n");
        System.out.println("Average wait time EDF: " + getAvgWaitingTime(taskList,N));
        return out;
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
    static int calcLCM(List<Task> taskList) {
        int lcm = taskList.get(0).pt;
        boolean flag = true;
        for( int i =0 ; i<200;i++){
            for(Task x : taskList) {
                if(lcm % x.pt != 0) {
                    flag = true;
                    break;
                }
                flag = false;
            }
            lcm = flag? (lcm + 1) : lcm;
        }

        return lcm;
    }


    static class ScheduledTasks {

        private List<Task> taskList = new ArrayList<>();
        private List<List<Task>> deadlinesList = new ArrayList<>();

        public List<Task> getTaskList() {
            return taskList;
        }

        public List<List<Task>> getDeadlinesList() {
            return deadlinesList;
        }
    }

}