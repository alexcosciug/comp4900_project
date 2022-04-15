package genetic;
import model.Task;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Population class
public class Population {

	private int popSize;
	private Individual[] individuals;
	private int geneLength;
	private int fittestScore = 0;

	/*
	 * @purpose Initialize population
	 */
	public Population(int popSize, Task[] tasks) {
		super();
		this.popSize = popSize;
		this.individuals = new Individual[popSize];
		//Create a first population pool
		List<Task> taskList = Arrays.asList(tasks);
		Task[] temp = new Task[tasks.length];
		for (int i = 0; i < popSize; i++) {
			Collections.shuffle(taskList);
			taskList.toArray(tasks);
			temp = Arrays.copyOf(tasks, tasks.length);
			individuals[i] = new Individual(temp);
		}
	}

	//Get the fittest individual and update fittest score
	public Individual selectFittest() {
		int maxFit = Integer.MAX_VALUE;
		int maxFitIndex = 0;
		for (int i = 0; i < individuals.length; i++) {
			if (maxFit >= individuals[i].getFitness()) {
				maxFit = individuals[i].getFitness();
				maxFitIndex = i;
			}
		}
		//update fittest score
		fittestScore = individuals[maxFitIndex].getFitness();
      
		//try to return the fittest individual
		try {
			return (Individual) individuals[maxFitIndex].clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Get the second most fittest individual
	public Individual selectSecondFittest() {
		int maxFit1 = 0;
		int maxFit2 = 0;
		for (int i = 0; i < individuals.length; i++) {
			if (individuals[i].getFitness() < individuals[maxFit1].getFitness()) {
				maxFit2 = maxFit1;
				maxFit1 = i;
			} else if (individuals[i].getFitness() < individuals[maxFit2].getFitness()) {
				maxFit2 = i;
			}
		}
		try {
			return (Individual) individuals[maxFit2].clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Get index of least fittest individual
	public int getLeastFittestIndex() {
		int minFitVal = Integer.MIN_VALUE;
		int minFitIndex = 0;
		for (int i = 0; i < individuals.length; i++) {
			if (minFitVal <= individuals[i].getFitness()) {
				minFitVal = individuals[i].getFitness();
				minFitIndex = i;
			}
		}
		return minFitIndex;
	}
  
	//Get index of the fittest individual
	public int getFittestIndex() {
		int maxFit = Integer.MIN_VALUE;
		int maxFitIndex = 0;
		for (int i = 0; i < individuals.length; i++) {
			if (maxFit <= individuals[i].getFitness()) {
				maxFit = individuals[i].getFitness();
				maxFitIndex = i;
			}
		}
		return maxFitIndex;
		}

	//Calculate fitness of each individual
	public void calculateFitness() {
		for (int i = 0; i < individuals.length; i++) {
			individuals[i].calcFitness();
		}
		selectFittest();
	}
  
	//Getters and Setters
  
	public int getPopSize() {
		return popSize;
	}
	
	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}
	
	public Individual[] getIndividuals() {
		return individuals;
	}
	
	public void setIndividuals(Individual[] individuals) {
		this.individuals = individuals;
	}

	
	public int getFittestScore() {
		return fittestScore;
	}


}