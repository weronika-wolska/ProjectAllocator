package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import exceptions.InvalidArgumentException;

public class CandidateSolution {
    public static final int FULL_STUDENT_FITNESS = 10;
    private Map<Student, Project> candidateSolution;
    private int fitness;
    private double gpaWeight;
    private ArrayList<Student> students;
    private ArrayList<Project> projects;

    public CandidateSolution(ArrayList<Student> students, ArrayList<Project> projects) throws InvalidArgumentException{
        this(students, projects, 0);
    }

    public double getGpaWeight() {
        return gpaWeight;
    }

    public ArrayList getStudents(){
        return this.students;
    }

    public ArrayList getProjects(){
        return this.projects;
    }


    public CandidateSolution(ArrayList<Student> students, ArrayList<Project> projects, double gpaWeight) throws InvalidArgumentException{
        if(students.size()!=projects.size()){
            throw new InvalidArgumentException();
        }
        setGpaWeight(gpaWeight);
        this.candidateSolution = new HashMap<Student, Project>(students.size());
        this.students=students;
        this.projects=projects;

        for(int i=0;i<students.size();i++){
            candidateSolution.put(students.get(i), projects.get(i));
        }

        if (isThereDuplicateProjects()) {
            throw new InvalidArgumentException();
        }


        this.fitness = calculateFitness(students, projects);
    }

    public int getFitness(){
        return this.fitness;
    }


    public double getEnergy() { return ( (double) 1 / this.fitness ) * 1000; }

    public Map getCandidateSolution(){
        return this.candidateSolution;
    }

    public Project findProjectAssignedToStudent(Student student){
        return this.candidateSolution.get(student);
    }

    public Student findStudentWithProject(Project project){
        for(Entry<Student, Project> entry: candidateSolution.entrySet()){
            if(entry.getValue()==project){
                return entry.getKey();
            }
        }
        return null;
    }

    public void setGpaWeight(double gpaWeight) {
        if(gpaWeight < 0) {
            gpaWeight = 0;
        }
        else if(gpaWeight > 1) {
            gpaWeight = 1;
        }
        this.gpaWeight = gpaWeight;
    }

    // TODO

    private int calculateIndividualFitness(Map.Entry<Student, Project> entry){
        int fitness=0;
        Student student = entry.getKey();
        Project project = entry.getValue();
        // assumes student has exactly 10 preferences
        for(int i = 0; i<10;i++){
            if(student.getPreferences().get(i)==project){
                fitness = 10 - i;       // if the assigned project is first preference, fitness is 10, if it's second, fitness is 9 etc.
            }
        }
        if(fitness<1){fitness = -50;} // if the assigned project is not in student's preferences, penalise 50 fitness points
        return fitness;
    }

    private int calculateFitness(Map<Student, Project> map){
        int score = 0;
        for(Map.Entry solution : map.entrySet()){
           /* Student student = (Student)solution.getKey();
            ArrayList<Project> studentPreferences = student.getPreferences();
            Project project = (Project)solution.getValue();
            for(int i=0;i<10;i++){
                if(studentPreferences.get(i)==project){
                    score += (10 -i);
                } else {score -=50;}
            } */
            score += calculateIndividualFitness(solution);
        }
        return score;
    }

    public boolean isThereDuplicateProjects() {
        for (Project project :
                candidateSolution.values()) {
            int count = 0;
            for (Project otherProject :
                    candidateSolution.values()) {
                if (otherProject.getProjectName().equals(project.getProjectName())) {
                    ++count;
                }
            }
            if(count > 1) {
                return true;
            }
        }
        return false;
    }

    private int calculateFitness(ArrayList<Student> students,  ArrayList<Project> projects) throws InvalidArgumentException{
        if(students.size()!=projects.size()){ throw new InvalidArgumentException();}
        int fitness = 0;
        for(int i=0;i<students.size();i++){
            Student student = students.get(i);
            ArrayList<Project> preferences = student.getPreferences();
            if(preferences.get(0)== projects.get(i)){
                fitness += 10;
            }
            else if(preferences.get(1)== projects.get(i)){
                fitness+=9;
            }
            else if(preferences.get(2)== projects.get(i)){
                fitness+=8;
            }
            else if(preferences.get(3)== projects.get(i)){
                fitness+=7;
            }
            else if(preferences.get(4)== projects.get(i)){
                fitness+=6;
            }
            else if(preferences.get(5)== projects.get(i)){
                fitness+=5;
            }
            else if(preferences.get(6)== projects.get(i)){
                fitness+=4;
            }
            else if(preferences.get(7)== projects.get(i)){
                fitness+=3;
            }
            else if(preferences.get(8)== projects.get(i)){
                fitness+=2;
            }
            else if(preferences.get(9)== projects.get(i)){
                fitness+=1;
            }
            // deduct points if assigned project is not on the student's preference list
            else{
                fitness-=50;
            }
            if(!student.canDoProject(projects.get(i))) fitness = students.size() * 100; // if the project is unfitting, make solution unfit
        }
        return fitness;

    }

    // takes a candidate solution as input and produces a random change
    // if the change is better, has a higher score, the candidate solution map becomes the solution 
    // and the method returns true
    // otherwise, the candidate solution stays the same and the method returns false
    public boolean changeSolution(Map<Student, Project> candidateSolution, ArrayList<Student> students) throws InvalidArgumentException{
        // must be at least two elements in order to perform a change
        if(candidateSolution.size()<2){ throw new InvalidArgumentException(); }

        Map<Student, Project> alternateSolution = candidateSolution;
        Random random = new Random();
        int x, y;
        do{
            x = random.nextInt(alternateSolution.size());
            y = random.nextInt(alternateSolution.size());
        }while (x==y);
        Student student1 = students.get(x);
        Student student2 = students.get(y);
        Project project = alternateSolution.get(student1);
        alternateSolution.put(student1, alternateSolution.get(student2));
        alternateSolution.put(student2, project);

        // compare the two candidate solutions
        if(calculateFitness(candidateSolution)>=calculateFitness(alternateSolution)){ return false;}
        else{
            this.candidateSolution=alternateSolution;
            this.fitness=calculateFitness(alternateSolution);
            return true;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Student, Project> pairing :
                candidateSolution.entrySet()) {
            Student student = pairing.getKey();
            Project project = pairing.getValue();
            string.append("student ").append(student.getFirstName()).append(" ").append(student.getSurname()).append(" doing ").append(student.getStream());
            string.append(" was assigned\n");
            string.append("project ").append(project.getProjectName()).append(" which is in the stream ").append(project.getStream()).append("\n");
        }
        return string.toString();
    }

}