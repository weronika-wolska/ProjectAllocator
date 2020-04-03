package entities;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student{
    private String firstName;
    private String surname;
    private Long studentId;
    private Stream stream;
    private ArrayList<Project> preferences;
    private Project project; // Maybe we can get rid of this?

    public Student(String firstName, String surname, Long studentId, Stream stream){
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        this.stream=stream;
        preferences = null;
        project = null;
    }

    public Student(String firstName, String surname, Long studentId, Stream stream, ArrayList<Project> preferences, Project project) {
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        this.stream=stream;
        this.preferences = preferences;
        this.project = project;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ArrayList<Project> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Project> preferences) {
        this.preferences = preferences;
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
                + surname + ", project=" + project + ", preferences=" + preferenceToString() + "]";
    }

    public boolean canDoProject(Project project) {
        if(stream == Stream.CS && project.getStream() == Stream.CS) return true;
        else if(stream == Stream.DS || stream == Stream.CSDS) {
            return project.getStream() == Stream.DS || project.getStream() == Stream.CSDS;
        }
        return false;
    }
}