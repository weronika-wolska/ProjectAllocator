package entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

public class Student{
    private String firstName;
    private String surname;
    private Long studentId;
    private Stream stream;
    private ArrayList<Project> preferences;
    private static ProjectRepository projectRepository;

    public Student(String firstName, String surname, Long studentId, Stream stream){
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        this.stream=stream;
        preferences = null;
    }

    public Student(String firstName, String surname, Long studentId, Stream stream, ArrayList<Project> preferences) throws InvalidArgumentException {
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        this.stream=stream;
        setPreferences(preferences);
    }

    public Student(){

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public ArrayList<Project> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Project> preferences) throws InvalidArgumentException {
        if(preferences.size()>10){ throw new InvalidArgumentException();}
        boolean validProjects = true;
        // check if all the preffered projects match the student's stream
        for(int i=0;i<preferences.size(); i++){
            if(stream!=preferences.get(i).getStream() || preferences.get(i).getStream()!=Stream.CSDS){
                validProjects = false;
            }
        }
        if(validProjects){ this.preferences=preferences;}
        else{ throw new InvalidArgumentException();}

        // if there is less than 10 preferences in student's list, assign them random projects
        if(preferences.size()<10){
            Random r = new Random();
            for(int i = preferences.size();i<10;i++){
                Project project;
                do{
                    int index = r.nextInt(projectRepository.getSize());
                    project = projectRepository.getProject(index);
                    this.preferences.add(i, project);
                } while (project.getStream()!=this.stream||project.getStream()!=Stream.CSDS);
            }
        }
        
    }

    private String preferenceToString() {
        StringBuilder string = new StringBuilder();
        if(preferences != null) {
            for (Project preference :
                    preferences) {
                string.append(preference.toString()).append("\n");
            }
        }
        return string.toString();
    }

    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", stream=" + stream + ", studentId=" + studentId + ", surname="
                + surname + ", preferences=" + preferenceToString() + "]";
    }

    public boolean canDoProject(Project project) {
        if(stream == Stream.CS && project.getStream() == Stream.CS) return true;
        else if(stream == Stream.DS || stream == Stream.CSDS) {
            return project.getStream() == Stream.DS || project.getStream() == Stream.CSDS;
        }
        return false;
    }
}