package entities;


import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

public class Student{
    public static final double FULL_GPA = 4.2;
    private String firstName;
    private String surname;
    private String name;
    private Long studentId;
    private Double gpa;
    private Stream stream;
    private ArrayList<Project> preferences = new ArrayList<>();
    private static ProjectRepository projectRepository;

    public Student(String firstName, String surname, Long studentId, Stream stream, ProjectRepository projectRepository) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, null, projectRepository);
    }

    public Student(String firstName, String surname, Long studentId, Stream stream) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, null, null);
    }

    public Student(String firstName, String surname, Long studentId, Stream stream, ArrayList<Project> preferences, ProjectRepository projectRepository) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, preferences, projectRepository);
    }

    public Student(String firstName, String surname, Long studentId, Stream stream, ArrayList<Project> preferences) throws InvalidArgumentException{
        this(firstName, surname, studentId, 2.1, stream, preferences, null);
    }

    public Student(ProjectRepository projectRepository) throws InvalidArgumentException{
        this("DefaultName", "DefaultSurname", (long) 12345678, 2.1, Stream.CS, null, projectRepository);
    }

    public Student() throws InvalidArgumentException{
        this("DefaultName",  (long) 12345678, 2.1,  null);
    }

    public Student(String firstName, String surname, Long studentId, Double gpa, Stream stream, ArrayList<Project> preferences) throws InvalidArgumentException {
        this(firstName, surname, studentId, gpa, stream, preferences, null);
    }

    public Student(String firstName, String surname, Long studentId, Double gpa, Stream stream, ArrayList<Project> preferences, ProjectRepository projectRepository) throws InvalidArgumentException {
        this.firstName=firstName;
        this.surname=surname;
        this.name = firstName + " " + surname;
        this.studentId=studentId;
        setGpa(gpa);
        this.stream=stream;
        if (projectRepository == null) { projectRepository = new ProjectRepository();}
        setProjectRepository(projectRepository);
        Student.projectRepository=projectRepository;
        if(preferences==null){ preferences = new ArrayList<>();}
        if(projectRepository==null) this.preferences=null;
        else setPreferences2(preferences);
    }


    public Student(String name, long id ,double GPA, ArrayList<Project> preferences){
        this.name = name;
        this.studentId=id;
        this.gpa = GPA;
        this.preferences = preferences;
    }

    public Student(String name, long id, ArrayList<Project> preferences){
        this.name = name;
        this.studentId=id;
        this.gpa = 3.5;
        this.preferences = preferences;
    }

    public static void setProjectRepository(ProjectRepository projectRepository) {
        Student.projectRepository = projectRepository;
    }

    public static ProjectRepository getProjectRepository() {
        return Student.projectRepository;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        setName(firstName + " " + surname);
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        setName(firstName +  " " + surname);
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

    public void setPreferences(ArrayList<Project> preferences){
        this.preferences = preferences;
    }
    public void setPreferences2(ArrayList<Project> spreferences) throws InvalidArgumentException {
        if(this.stream==null){ this.preferences=spreferences;}
        if(spreferences.size()>10){ throw new InvalidArgumentException();}
        //boolean validProjects = true;
        // check if all the preffered projects match the student's stream
        else{
            for(int i=0;i<spreferences.size(); i++){
            if(!canDoProject(spreferences.get(i))){
                spreferences.remove(i);
                i--;
            }
        }}
        this.preferences=spreferences;
        //else{ throw new InvalidArgumentException();}

        // if there is less than 10 preferences in student's list, assign them random projects
        if(this.preferences.size()<10){
            Random r = new Random();
            for(int i = this.preferences.size();i<10;i++){
                Project project;
                do{
                    int index = r.nextInt(projectRepository.getSize());
                    project = projectRepository.getProject(index);
                    //this.preferences.add(i, project);
                } while (!canDoProject(project));
                this.preferences.add(i, project);
            }
        }
        
    }


    public boolean hasNoPreferences() {
        if(preferences == null) {
            return true;
        }
        else return preferences.size() == 0;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
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
                + surname + ", gpa=" + gpa  + ", preferences=" + preferenceToString() + "]";
    }

    public boolean canDoProject(Project project) {
        /*if(stream == Stream.CS && project.getStream() == Stream.CS) return true;
        else if(stream == Stream.commonTesting) return true;
        else if(stream == Stream.DS || stream == Stream.CSDS) {
            return project.getStream() == Stream.DS || project.getStream() == Stream.CSDS;
        }
        return false;*/
        return (project.getStream()==this.stream||project.getStream()==Stream.CSDS || project.getStream()==null);
    }

    public boolean hasFirstName(String firstName) {
        return firstName.trim().toLowerCase().equals(this.firstName.trim().toLowerCase());
    }

    public boolean hasSurname(String surname) {
        return surname.trim().toLowerCase().equals(this.surname.trim().toLowerCase());
    }

    public boolean hasName(String firstName, String surname) {
        return surname.trim().toLowerCase().equals(this.surname.trim().toLowerCase()) &&
                firstName.trim().toLowerCase().equals(this.firstName.trim().toLowerCase());
    }

    public boolean isTheSameStudent(Student other) {
        //System.out.println("Entered isTheSameStudent.");
        if(other == null) return false;
        else {
            //System.out.println("Checking non null other staff member with:" + (studentId.equals(other.studentId)));
            return studentId.equals(other.studentId);
        }
    }

    public void setByOneName(String name) {
        boolean hasSpace = name.trim().toLowerCase().contains(" ");
        if( hasSpace) {
            // eliminating white space in the string
            ArrayList<String> names = new ArrayList<>();
            if(hasSpace) {
                names = tokenizeBySpace(name);
            }

            // getting surname
            surname = names.get(names.size() - 1);
            names.set(names.size() - 1, null);

            // getting first name/s
            firstName = "";
            for (String aName :
                    names) {
                if (aName != null) {
                    firstName += " " + aName;
                }
            }
            firstName = firstName.trim();

        }
        else {
            firstName = "";
            surname = name.trim();
        }
        setName(firstName + " " + surname);
    }

    public ArrayList<String> tokenizeBySpace(String string) {
        //System.out.println("In tokenizeByComma with:" + string);
        ArrayList<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(string," ");
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if(!token.equals("") && !token.trim().isEmpty()) {
                tokens.add(token);
            }
        }
        //System.out.println("In tokenizeByComma with tokens:" + tokens);
        return tokens;
    }

}
