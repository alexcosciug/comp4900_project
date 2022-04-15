package genetic;
import model.Task;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.lang.*;

public class GeneticAlgorithm {

	private Population population;
	private Individual fittest;
	private Individual secondFittest;
	private int generationCount;
	private static int numberOfGenes;
	private static int numberOfIndividuals;
	private static boolean verbose;
	private static boolean coloredGenes;
    
	public GeneticAlgorithm(Task[] tasks, int numberOfIndividuals) {
		this.population = new Population(numberOfIndividuals, tasks);
		this.generationCount = 0;
		this.numberOfGenes = tasks.length;
	}

	public Task[] getSolution(){

		//Verbosity (e.g. Should we print genetic pool in the console?)
		verbose = true;
		//Apply color to genes (if verbose = true) Note: this will slow down the process
		coloredGenes = false;

		Random rn = new Random();
		System.out.println("Population of "+this.population.getPopSize()+" individual(s).");
		//Calculate fitness of each individual
		this.population.calculateFitness();

		System.out.println("\nGeneration: " + this.generationCount + " Fittest: " + this.population.getFittestScore());
		//show genetic pool
		showGeneticPool(this.population.getIndividuals());

		//While population gets an individual with maximum fitness
		while (true) {
			++this.generationCount;

			//Do selection
			this.selection();

			//Do crossover
			this.crossover();

			//Do mutation under a random probability
			if (rn.nextInt()%7 < 5) {
				this.mutation();
			}

			//Add fittest offspring to population
			this.addFittestOffspring();

			//Calculate new fitness value
			this.population.calculateFitness();

			System.out.println("\nGeneration: " + this.generationCount + " Fittest score: " + this.population.getFittestScore());

			//show genetic pool
			showGeneticPool(this.population.getIndividuals());
			if(Math.abs(population.selectFittest().getFitness() - population.selectSecondFittest().getFitness())<1){
				break;
			}
		}

		System.out.println("\nSolution found in generation " + this.generationCount);
		System.out.println("Index of winner Individual: "+this.population.getFittestIndex());
		System.out.println("Fitness: "+this.population.getFittestScore());
		System.out.print("Genes: ");
		for (int i = 0; i < numberOfGenes; i++) {
			System.out.print(this.population.selectFittest().getGenes()[i] + "\t");
		}
		System.out.println("");
		Task[] solution = this.population.selectFittest().getGenes();
		return solution;
	}
	//Selection
	void selection() {

		//Select the most fittest individual
		this.fittest = population.selectFittest();

		//Select the second most fittest individual
		this.secondFittest = population.selectSecondFittest();
	}

	//Crossover
	void crossover() {
		Random rn = new Random();

		//Select a random crossover point
		int crossOverPoint = rn.nextInt(this.numberOfGenes);

		//Swap values among parents
		for (int i = 0; i < crossOverPoint; i++) {
			Task temp = fittest.getGenes()[i];
			fittest.getGenes()[i] = secondFittest.getGenes()[i];
			secondFittest.getGenes()[i] = temp;
		}

	}

	//Mutation
	void mutation() {
		Random rn = new Random();

		//Select a random mutation point
		int mutationPoint = rn.nextInt(this.numberOfGenes);

		//Flip values at the mutation point
		Task[] mutationValues = splitAtMutationPoint(mutationPoint,fittest.getGenes());
		mutationValues = shuffleGenes(mutationValues);
		fittest.setGenes(mergeAtMutationPoint(mutationPoint,mutationValues,fittest.getGenes()));

		mutationPoint = rn.nextInt(this.numberOfGenes);
		mutationValues = splitAtMutationPoint(mutationPoint,secondFittest.getGenes());
		mutationValues = shuffleGenes(mutationValues);
		secondFittest.setGenes(mergeAtMutationPoint(mutationPoint,mutationValues,secondFittest.getGenes()));

	}
	Task[] shuffleGenes(Task[] taskArray){
		List<Task> taskList = Arrays.asList(taskArray);
		Collections.shuffle(taskList);
		return taskList.toArray(taskArray);
	}

	Task[] mergeAtMutationPoint(int mutationPoint, Task[] mutationArray, Task[] geneArray){
		Task[] temp = geneArray;
		for(int i = 0; i< geneArray.length-mutationPoint; ++i){
			temp[mutationPoint+i] = mutationArray[i];
		}
		return temp;
	}

	Task[] splitAtMutationPoint(int mutationPoint, Task[] taskArray){
		Task[] temp = new Task[numberOfGenes-mutationPoint];
		for(int i = 0; i< taskArray.length-mutationPoint; ++i){
			temp[i] = taskArray[mutationPoint+i];
		}
		return temp;
	}

	//Get fittest offspring
	Individual getFittestOffspring() {
		if (fittest.getFitness() > secondFittest.getFitness()) {
			return fittest;
		}
		return secondFittest;
	}


	//Replace least fittest individual from most fittest offspring
	void addFittestOffspring() {

		//Update fitness values of offspring
		fittest.calcFitness();
		secondFittest.calcFitness();

		//Get index of least fit individual
		int leastFittestIndex = population.getLeastFittestIndex();

		//Replace least fittest individual from most fittest offspring
		population.getIndividuals()[leastFittestIndex] = getFittestOffspring();
	}
    
	//show genetic state of the population pool
	static void showGeneticPool(Individual[] individuals) {
		if(!verbose) return;
		System.out.println("==Genetic Pool==");
		int increment=0;
		for (Individual individual:individuals) {
			System.out.println("> Individual  "+increment+" | "+(coloredGenes?individual.toStringColor():individual.toString())+" |");
			increment++;
		}
		System.out.println("================");
	}

}