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
    private double gpaweight;
    boolean iterationsReachedLimit = false;

    public GeneticAlgorithm(StudentRepository studentRepository, ProjectRepository projectRepository, double gpaweight)throws InvalidArgumentException{
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
        this.gpaweight=gpaweight;
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
    public CandidateSolution applyAlgorithm() throws Exception{
        this.population = new Population(populationSize, studentRepository, projectRepository);
        //if(this.population.population[0]==null){throw new InvalidArgumentException();}
        Random rand = new Random();
        int a, b;
        this.bestSolutionFound = this.population.getNthFittest(1);
        int iterations = 0;
        while(!isTerminatingCoditionMet(bestSolutionFound)){
            if(iterations==1000){ 
                this.iterationsReachedLimit = true;
                this.bestSolutionFound = this.population.getNthFittest(1);
                return this.bestSolutionFound;
            }
            do{
                a = rand.nextInt(10);
                b = rand.nextInt(10);
            }while(a!=b);
            if(this.population.getPopulation()==null) throw new Exception();
            if(this.population.getPopulation()[a]!=null && this.population.getPopulation()[b]!=null){
                CandidateSolution potentialSolution = this.mutate(population.getPopulation()[a], population.getPopulation()[b]);
                if(population.isViable(potentialSolution)&&potentialSolution.getFitness()>this.population.getPopulation()[populationSize-1].getFitness()){
                    population.setIndividual(populationSize-1, potentialSolution);
                }
            iterations++;
            } else throw new Exception("population contains null values");
            
        }
        this.bestSolutionFound = population.getNthFittest(1);
        return this.bestSolutionFound;
    }

    public boolean getIterationLimitReached(){
        return this.iterationsReachedLimit;
    }


    // terminating condition is that all assigned projects are different and each student
    // gets a project that's in their preference list
    private boolean isTerminatingCoditionMet(CandidateSolution solution){ 
        if(solution==null){ throw new NullPointerException();}
        return solution.everyStudentHasProjectInPreference && !solution.isThereDuplicateProjects();
        
         
        //return false;
    }

    public boolean wasTerminatedConditionMet(){
        return isTerminatingCoditionMet(this.bestSolutionFound);
    }

    // picks a random number "index" between 0 and number of students, keeps the first 0-(index-1) elements of the first solution and adds elements at index - number
    // of students from the second solution, creates and returns a new CandidateSolution from the above data
    private CandidateSolution mutate(CandidateSolution firstIndividual, CandidateSolution secondIndividual)throws IndexOutOfBoundsException, InvalidArgumentException{
        Random rand = new Random();
        try{
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
            newSolution.setGpaWeight(gpaweight);

            return newSolution;
        } catch (Exception e){
            e.getCause();
            return null;
        }
        
        
    }

    

    // population class to store all individuals we are using to mutate
    private class Population{
        private CandidateSolution population[];
        private double populationFitness = -1;
        private StudentRepository studentRepository;
        private ProjectRepository projectRepository;

        public Population(int populationSize, StudentRepository studentRepository, ProjectRepository projectRepository)throws InvalidArgumentException, Exception{
            if(studentRepository.getSize()==0 || projectRepository.getSize() == 0 || projectRepository.getSize()<studentRepository.getSize()) throw new InvalidArgumentException();
            this.studentRepository=studentRepository;
            this.projectRepository=projectRepository;
            //this.population = new CandidateSolution[populationSize];
            
                this.population = generatePopulation();
            
            //this.population = generatePopulation();
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

        private CandidateSolution[] generatePopulation() throws Exception{
            Random rand = new Random();
            //ArrayList<Student> students = new ArrayList<>();
            if(projectRepository.getSize()==0) throw new Exception();
            ArrayList<Student> students = this.studentRepository.getStudents();
            ArrayList<Project> projects = new ArrayList<>(students.size());
            int a,b;
            int i = 0;
            CandidateSolution[] population = new CandidateSolution[populationSize];
            while(i!=populationSize-1){
                for(int j=0;j<studentRepository.getSize();j++){
                    //a = rand.nextInt(this.studentRepository.getSize());
                    //students.add(studentRepository.getStudent(j));
                    b = rand.nextInt(this.projectRepository.getSize());
                    projects.add(j, projectRepository.getProject(b));
                }
                CandidateSolution potentialSolution = new CandidateSolution(students, projects);
                // if the solution has no duplicate projects and all students have a project, the solution is viable
                
                    population[i] = potentialSolution;
                    i++;
                
               
               projects.clear();
            }
            
            return population;

        }

        public boolean isViable(CandidateSolution potentialSolution){
            try{
                return (potentialSolution.getCandidateSolution().size()==this.studentRepository.getSize());
            } catch (Exception e){
                e.getCause();
                return false;
            }
           
        }

        public CandidateSolution getNthFittest(int n){
            if(this.population!=null){
                Arrays.sort(this.population, new Comparator<CandidateSolution>() {
                    @Override
                    public int compare(CandidateSolution firstIndividual, CandidateSolution secondIndividual){
                        if(secondIndividual!=null&&firstIndividual!=null){
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
                        return 0;
                    }
                });
    
                return this.population[n];
            }
            else return null;
            
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

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

}