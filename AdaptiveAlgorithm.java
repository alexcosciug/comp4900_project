import genetic.GeneticAlgorithm;
import model.Task;

public class AdaptiveAlgorithm
{
	public static void main(String args[])
	{
		System.out.println("Number of process: ");//how many process you have to enter
		int num=200;
		
		Task B[]=new Task[num];

		
		for(int i=0;i<num;i++)
		{
			//Task(pid,dt,bt,at)
			int burstTime = (int)((Math.random() * (30 - 10)) + 10);
			B[i] = new Task( i,burstTime + (int)((Math.random() * (30 - 1)) + 1),burstTime,(int)((Math.random() * (5 - 1)) + 1));
			System.out.println("Burst time for "+B[i].pid+": "+B[i].bt+"\t"+"Arrival time: "+B[i].at);
		}

		//get optimal schedule based on GA
		GeneticAlgorithm genetic = new GeneticAlgorithm(B,20);
		Task[] geneticSln = genetic.getSolution();
		
		float total=0;
		
		int k=0;
		
		System.out.println("Process\t\tBurst time\twaiting time");
		
		for(int i=0;i<num;i++)
		{
			System.out.println("p"+(geneticSln[i].pid)+"\t"+geneticSln[i].dt+"\t"+geneticSln[i].wt);
			total+=geneticSln[i].wt;
		}
		
		System.out.println("The average waiting time is: "+(total/num));
	}
}