package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InvalidArgumentException;

public class CandidateSolution {
    private Map<Student, Project> candidateSolution;
    private int fitness;

    public CandidateSolution(ArrayList<Student> students, ArrayList<Project> projects) throws InvalidArgumentException{
        if(students.size()!=projects.size()){
            throw new InvalidArgumentException();
        }

        this.candidateSolution = new HashMap<Student, Project>(students.size());

        for(int i=0;i<students.size();i++){
            candidateSolution.put(students.get(i), projects.get(i));
        }

        // TODO allow student to have less then 10 preferences, so no null pointer is thrown in calculateFitness
        //this.fitness = calculateFitness(students, projects);
    }

    public int getFitness(){
        return this.fitness;
    }

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

    private int calculateFitness(ArrayList<Student> students,  ArrayList<Project> projects){
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
            if(!student.canDoProject(projects.get(i))) fitness = students.size() * 100; // if the project is unfitting, make solution unfit
        }
        return fitness;

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