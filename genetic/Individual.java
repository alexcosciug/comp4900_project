package genetic;
import model.Task;
import java.util.*;

public class Individual implements Cloneable{

	private int fitness = 0;
	private Task[] tasks;

	public Individual(Task[] tasks) {
		
		//Initialization

		this.tasks = tasks;
		Random rn = new Random();
		fitness = 0;
	}

	//Calculate fitness
	public void calcFitness() {
		int totalWT  = 0;
		int totalTime = 0;
		for (int i = 0; i < this.tasks.length; i++) {
			totalTime = totalTime + this.tasks[i].bt;
			totalWT = totalWT + this.tasks[i].getWaitingTime(totalTime);
		}
		this.fitness = totalWT/this.tasks.length;
	}
  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Individual individual = (Individual)super.clone();
		individual.tasks = new Task[tasks.length];
		for(int i = 0; i < individual.tasks.length; i++){
			individual.tasks[i] = this.tasks[i];
		}
		return individual;
	}

	@Override
	public String toString() {
		//without colors
		return "[genes=" + Arrays.toString(tasks) + "]";
	}
  
	public String toStringColor() {
		//with colors
		String genesString = "[genes=[";
		int increment=0;
		for(Task gene:tasks) {
			//print gene
			genesString += gene;
			//print comma
			if(increment<tasks.length) genesString += ", ";
			
			increment++;
		}
		genesString += "]]";
		return genesString;
	}

	public int getFitness() {
		return fitness;
	}

	public Task[] getGenes() {
		return tasks;
	}
	public void setGenes(Task[] tasks) {
		this.tasks = tasks;
	}

}