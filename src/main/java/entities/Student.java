package entities;


import java.util.ArrayList;
import java.util.Random;

import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

public class Student{
    public static final double FULL_GPA = 4.2;
    private String firstName;
    private String surname;
    private Long studentId;
    private Double gpa;
    private Stream stream;
    private ArrayList<Project> preferences = new ArrayList<>();
    private ProjectRepository projectRepository;

    public Student(String firstName, String surname, Long studentId, Stream stream, ProjectRepository projectRepository) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, null, projectRepository);
    }

    public Student(String firstName, String surname, Long studentId, Stream stream, ArrayList<Project> preferences, ProjectRepository projectRepository) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, preferences, projectRepository);
    }

    public Student(ProjectRepository projectRepository) throws InvalidArgumentException{
        this("DefaultName", "DefaultSurname", (long) 12345678, 2.1, Stream.CS, null, projectRepository);
    }

    public Student(String firstName, String surname, Long studentId, Double gpa, Stream stream, ArrayList<Project> preferences, ProjectRepository projectRepository) throws InvalidArgumentException {
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        setGpa(gpa);
        this.stream=stream;
        this.projectRepository=projectRepository;
        if(preferences==null){ preferences = new ArrayList<Project>();}
        setPreferences(preferences);
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
        if(getStream()==null){ this.preferences=preferences;}
        if(preferences.size()>10){ throw new InvalidArgumentException();}
        boolean validProjects = true;
        // check if all the preffered projects match the student's stream
        for(int i=0;i<preferences.size(); i++){
            if(stream!=preferences.get(i).getStream() || preferences.get(i).getStream()!=Stream.CSDS || preferences.get(i).getStream()!=null){
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
                } while (project.getStream()!=this.stream||project.getStream()!=Stream.CSDS || project.getStream()!=null);
            }
        }
        
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        if(gpa > Student.FULL_GPA) {
            gpa = Student.FULL_GPA;
        }
        else if(gpa < 0) {
            gpa = (double) 0;
        }
        this.gpa = gpa;
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