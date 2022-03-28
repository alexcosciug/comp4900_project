// JEFFREY BEN, 101157817
// EARLIEST DEADLINE FIRST ALGORITHM - SCHEDULER FILE

// BACKGROUND INFO FROM RESEARCH PAPER:
//  Another CPU scheduling algorithm for real time system is Earliest deadline first (EDF). EDF canbe used for both static and dynamic type of scheduling. 
// When n number of tasks are to bescheduled by EDF, then it’s complexity is O(n2) and percentage CPU utilization is 100%.

//  The EDF scheduling algorithm is a priority driven algorithm.
//  The task with nearest deadlineis given highest priority and it is selected for execution. 
//  This algorithm is simple and proved to beoptimal when the system is preemptive, underloaded and there is only one processor [7].

// https://www.geeksforgeeks.org/earliest-deadline-first-edf-cpu-scheduling-algorithm/

package edf_algorithm;
import java.util.List;

class EDFScheduler {


    int computeTheLCM(List<EDFTask> tasksToDo) {

    }
