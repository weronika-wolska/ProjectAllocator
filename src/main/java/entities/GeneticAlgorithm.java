package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;
import repositories.StudentRepository;

public class GeneticAlgorithm {
    //private CandidateSolution[] viableSolutions;
    private CandidateSolution bestSolutionFound;
    private StudentRepository studentRepository;
    private ProjectRepository projectRepository;
    private final int populationSize = 1000;
    private Population population;

    public GeneticAlgorithm(StudentRepository studentRepository, ProjectRepository projectRepository)throws InvalidArgumentException{
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
        this.population = new Population(populationSize, studentRepository, projectRepository);
        this.bestSolutionFound = population.getNthFittest(1);
    }

    public CandidateSolution getBestSolution(){
        return this.bestSolutionFound;
    }

    public CandidateSolution[] getPopulation(){
        return this.population.getPopulation();
    }

    // the algorithm finishes if the terminating condition is met or if it reaches
    // 1,000 iterations and the condition is still not satisfied
    // returns the solution with the highest fitness
    public CandidateSolution applyAlgorithm() throws InvalidArgumentException{
        Random rand = new Random();
        int a, b;
        this.bestSolutionFound = this.population.getNthFittest(1);
        int iterations = 0;
        while(!isTerminatingCoditionMet(population)){
            if(iterations==1000){ 
                this.bestSolutionFound = population.getNthFittest(1);
                return this.bestSolutionFound;
            }
            do{
                a = rand.nextInt(10);
                b = rand.nextInt(10);
            }while(a!=b);
            CandidateSolution potentialSolution = this.mutate(population.getPopulation()[a], population.getPopulation()[b]);
            if(population.isViable(potentialSolution)&&potentialSolution.getFitness()>this.population.getPopulation()[populationSize-1].getFitness()){
                population.setIndividual(populationSize-1, potentialSolution);
            }
           iterations++;
        }
        this.bestSolutionFound = population.getNthFittest(1);
        return this.bestSolutionFound;
    }


    // terminating condition is that all assigned projects are different and each student
    // gets a project that's in their preference list
    private boolean isTerminatingCoditionMet(Population population){ 
        boolean met = true;
        for(int i=0;i<population.getPopulationSize();i++){
            if(this.population.getPopulation()[i].getFitness()<1){
                met = false;
            }
        }

        return met;
    }

    // picks a random number "index" between 0 and number of students, keeps the first 0-(index-1) elements of the first solution and adds elements at index - number
    // of students from the second solution, creates and returns a new CandidateSolution from the above data
    private CandidateSolution mutate(CandidateSolution firstIndividual, CandidateSolution secondIndividual)throws IndexOutOfBoundsException, InvalidArgumentException{
        Random rand = new Random();
        ArrayList<Student> newStudents = firstIndividual.getStudents();
        ArrayList<Project> newProjects = firstIndividual.getProjects();
        ArrayList<Student> secondStudents = secondIndividual.getStudents();
        ArrayList<Project> secondProjects = secondIndividual.getProjects();
        int index = rand.nextInt(firstIndividual.getCandidateSolution().size());
        for(int i=index;i<firstIndividual.getCandidateSolution().size();i++){
            newStudents.remove(i);
            newStudents.add(i, secondStudents.get(i));
            newProjects.remove(i);
            newProjects.add(i, secondProjects.get(i));
        }
        CandidateSolution newSolution = new CandidateSolution(newStudents, newProjects);

        return newSolution;
    }

    

    // population class to store all individuals we are using to mutate
    private class Population{
        private CandidateSolution population[];
        private double populationFitness = -1;
        private StudentRepository studentRepository;
        private ProjectRepository projectRepository;

        public Population(int populationSize, StudentRepository studentRepository, ProjectRepository projectRepository)throws InvalidArgumentException{
            this.studentRepository=studentRepository;
            this.projectRepository=projectRepository;
            this.population = new CandidateSolution[populationSize];
            this.population = generatePopulation();
        }

        public CandidateSolution[] getPopulation(){
            return this.population;
        }

        public double getPopulationFitness(){
            return this.populationFitness;
        }

        public void setPopulation(CandidateSolution[] newPopulation){
            this.population = newPopulation;
        }

        public void setPopulationFitness(double newFitness){
            this.populationFitness = newFitness;
        }

        private CandidateSolution[] generatePopulation() throws InvalidArgumentException{
            Random rand = new Random();
            ArrayList<Student> students = new ArrayList<>();
            ArrayList<Project> projects = new ArrayList<>();
            int a,b;
            int i = 0;
            CandidateSolution[] population = new CandidateSolution[populationSize];
            while(population.length!=populationSize){
                for(int j=0;j<studentRepository.getSize();j++){
                    a = rand.nextInt(this.studentRepository.getSize());
                    students.add(studentRepository.getStudent(a));
                    b = rand.nextInt(this.projectRepository.getSize());
                    projects.add(projectRepository.getProject(b));
                }
                CandidateSolution potentialSolution = new CandidateSolution(students, projects);
                // if the solution has no duplicate projects and all students have a project, the solution is viable
                if(isViable(potentialSolution)){
                    population[i] = potentialSolution;
                    i++;
                }
               students.clear();
               projects.clear();
            }
            return population;

        }

        private boolean isViable(CandidateSolution potentialSolution){
            return (!potentialSolution.isThereDuplicateProjects()&&potentialSolution.getCandidateSolution().size()==this.studentRepository.getSize());
        }

        public CandidateSolution getNthFittest(int n){
            Arrays.sort(this.population, new Comparator<CandidateSolution>() {
                @Override
                public int compare(CandidateSolution firstIndividual, CandidateSolution secondIndividual){
                    if(firstIndividual.getFitness()>secondIndividual.getFitness()){
                         return -1;
                    }
                    else if(firstIndividual.getFitness()<secondIndividual.getFitness()){ 
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            });

            return this.population[n];
        }

        public int getPopulationSize(){
            return this.population.length;
        }

        public CandidateSolution getIndividual(int index){
            return this.population[index];
        }

        public void setIndividual(int index, CandidateSolution individual){
            this.population[index]=individual;
        }

        // using Fisher-Yates algorithm
        public void shuffle(){
            Random rand = new Random();
            for(int i=this.getPopulationSize()-1;i>0;i--){
                int j = rand.nextInt(i);
                CandidateSolution tmp = this.population[i];
                this.population[i]=this.population[j];
                this.population[j]=tmp;
                
	        }
        }
    }

}