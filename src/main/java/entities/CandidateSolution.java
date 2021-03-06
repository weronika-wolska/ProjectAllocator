package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import exceptions.InvalidArgumentException;
import exceptions.NoRandomChangeWasMadeException;
import entities.*;
import repositories.*;

public class CandidateSolution {
    public static final int FULL_STUDENT_FITNESS = 20;
    private Map<Student, Project> candidateSolution;
    private int fitness;
    private double gpaWeight;
    private ArrayList<Student> students;
    private ArrayList<Project> projects;
    private Map<Student, Project> backupSolution;
    private ArrayList<Project> leftoverProjects;
    private ArrayList<Project> backupLeftoverProjects;

    public boolean everyStudentHasProjectInPreference = true;
    private int size;
    public ArrayList<Project> getBackupLeftoverProjects() throws NoRandomChangeWasMadeException {
        if(backupLeftoverProjects == null) {
            throw new NoRandomChangeWasMadeException();
        }
        else {
            return backupLeftoverProjects;
        }
    }

    public void setBackupLeftoverProjects(ArrayList<Project> backupLeftoverProjects) {
        this.backupLeftoverProjects = backupLeftoverProjects;
    }

    public ArrayList<Project> getLeftoverProjects() {
        return leftoverProjects;
    }

    public void setLeftoverProjects(ArrayList<Project> leftoverProjects) {
        this.leftoverProjects = leftoverProjects;
    }

    public CandidateSolution(StudentRepository students, ProjectRepository projects) throws InvalidArgumentException {
        this(students, projects, 0);
    }

    public CandidateSolution(StudentRepository students, ProjectRepository projects, double gpaWeight) throws InvalidArgumentException {
        if(students.getSize() > projects.getSize()) throw new InvalidArgumentException("error: not enough projects in the input file");
        this.students = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.candidateSolution = new HashMap<>(students.getSize());
        this.leftoverProjects = new ArrayList<>();
        for( int i = 0; i < projects.getSize(); ++i) {
            if( i < students.getSize()) {
                this.students.add(students.getStudent(i));
                this.projects.add(projects.getProject(i));
            }
            else {
                this.leftoverProjects.add(projects.getProject(i));
            }
        }
        setGpaWeight(gpaWeight);
        for( int i = 0; i < students.getSize(); ++i){
            candidateSolution.put(students.getStudent(i), projects.getProject(i));
        }
        if (isThereDuplicateProjects()) {
            throw new InvalidArgumentException();
        }
        this.fitness = calculateFitness(candidateSolution);
        this.backupSolution = null; // this map is so by default, only pointed to an actual map when needed, no setters/getters necessary
        this.backupLeftoverProjects = null;
    }

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
            throw new InvalidArgumentException("error: array lists used to construct candidate solution differ in length, use repository constructor instead");
        }
        setGpaWeight(gpaWeight);
        this.candidateSolution = new HashMap<Student, Project>(students.size());
        this.students=students;
        this.projects=projects;

        for(int i=0;i<students.size();i++){
            candidateSolution.put(students.get(i), projects.get(i));
        }


        if(this.candidateSolution==null){ this.size=0;}
        else{this.size = this.candidateSolution.size();}
        this.fitness = calculateFitness(this.candidateSolution);

        if (isThereDuplicateProjects()) {
            //throw new InvalidArgumentException();
            this.fitness -= 50;
        }
    }

    public int size(){
        return this.size;
    }

    public int getFitness(){
        return this.fitness;
    }


    public double getEnergy() {
        return fitness * -1;
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

    public void setGpaWeight(double gpaWeight) {
        if(gpaWeight < 0) {
            gpaWeight = 0;
        }
        else if(gpaWeight > 1) {
            gpaWeight = 1;
        }
        this.gpaWeight = gpaWeight;
    }


    private int calculateIndividualFitness(Map.Entry<Student, Project> entry){
        int fitness=0;
        Student student = entry.getKey();
        Project project = entry.getValue();
        ArrayList<Project> preferences = student.getPreferences();
        // assumes student has exactly 10 preferences
        for(int i = 0; i<preferences.size();i++){
            if(preferences.get(0).getProjectName()==project.getProjectName()){
                return FULL_STUDENT_FITNESS;
            }
            if(preferences.get(i).getProjectName()==project.getProjectName()){
                return FULL_STUDENT_FITNESS - i;       // if the assigned project is first preference, fitness is 10, if it's second, fitness is 9 etc.
            }
        }
        if(fitness<1){
            fitness = -50;
            everyStudentHasProjectInPreference = false;
        } // if the assigned project is not in student's preferences, penalise 50 fitness points
        return fitness;
    }

    private int calculateFitness(Map<Student, Project> map){
        int score = 0;
        for(Map.Entry solution : map.entrySet()){
           /* Student student = (Student)solution.getKey();
            ArrayList<Project> studentPreferences = student.getPreferences();
            Project project = (Project)solution.getValue();
            for(int i=0;i<20;i++){
                if(studentPreferences.get(i)==project){
                    score += (20 -i);
                } //else {score -=50;}
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
                if (otherProject.getProjectName().equals(project.getProjectName())&&otherProject!=project) {
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
                this.everyStudentHasProjectInPreference = false;
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
        int leftoverCount = 0;
        if( leftoverProjects != null) leftoverCount = leftoverProjects.size();
        if((candidateSolution.size() + leftoverCount)<2){ throw new InvalidArgumentException("error: no change can be made to the solution, the assignment is already made"); }

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
        String string = "";
        for (Map.Entry<Student, Project> pairing :
                candidateSolution.entrySet()) {
            Student student = pairing.getKey();
            Project project = pairing.getValue();
            string += "student " + student.getFirstName() + " " + student.getSurname() + " doing " + student.getStream();
            string += "\twas assigned\t";
            string += "project " + project.getProjectName() + " which is in the stream " + project.getStream() + "\n";
        }
        return string;
    }

    public Map<Student, Project> getBackupSolution() throws NoRandomChangeWasMadeException{
        if(backupSolution == null) {
            throw new NoRandomChangeWasMadeException();
        }
        else {
            return backupSolution;
        }
    }

    public ArrayList<Project> getDeepCopyOfLeftoverProjects() {
        ArrayList<Project> copiedLeftoverProjects = new ArrayList<>();
        for (Project leftoverProject :
                leftoverProjects) {
            copiedLeftoverProjects.add(leftoverProject);
        }
        return copiedLeftoverProjects;
    }

    private void saveBackupSolution() {
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("STARTING SAVEBACKUPSOLUTION HERE");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        backupSolution = new HashMap<>();
        for (Map.Entry<Student, Project> solution:
            candidateSolution.entrySet()) {
            backupSolution.put(solution.getKey(), solution.getValue());
        }
        if(leftoverProjects != null) {
            backupLeftoverProjects = getDeepCopyOfLeftoverProjects();
        }
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("ENDING SAVEBACKUPSOLUTION HERE");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void makeRandomChange() throws InvalidArgumentException {
        // TODO test
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("STARTING MAKERANDOMCHANGE HERE");
        //System.out.println("unchanged solution:" + "\n" + toString());
        //System.out.println("\n\n\n\n\n\n\n\n\n\n");

        saveBackupSolution();
        Random random = new Random();
        int x, y;
        Student studentX, studentY;
        do {
            do{
                x = random.nextInt(candidateSolution.size());
                y = random.nextInt(candidateSolution.size());
            }while (x==y);
            studentX = (Student) candidateSolution.keySet().toArray()[x];
            studentY = (Student) candidateSolution.keySet().toArray()[y];
        } while (!doStudentsStudySameStream(studentX, studentY));
        Project project = candidateSolution.get(studentX);
        candidateSolution.put(studentX, candidateSolution.get(studentY));
        candidateSolution.put(studentY, project);
        fitness = calculateFitness(candidateSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("changed current solution:" + toString());
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("backup:" + backupSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("ENDING MAKERANDOMCHANGE HERE");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void makeRandomChangeWithLeftovers() throws InvalidArgumentException {
        // TODO test
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("STARTING MAKERANDOMCHANGE HERE");
        //System.out.println("unchanged solution:" + "\n" + toString());
        //System.out.println("\n\n\n\n\n\n\n\n\n\n");
        if((candidateSolution.size() + leftoverProjects.size())<2) throw new InvalidArgumentException("error: no change can be made to the solution, the assignment is already made");
        saveBackupSolution();
        Random random = new Random();
        int x, y;
        int studentCount = students.size();
        int projectCount = projects.size();
        if(leftoverProjects != null) projectCount += leftoverProjects.size();
        Student student, otherStudent;
        Project project, otherProject;
        boolean areCompatible = false;
        int searchCount = 0;
        do {
            do{
                x = random.nextInt( studentCount);
                y = random.nextInt( projectCount);
            } while ( x == y);
            ++searchCount;
            student = (Student) candidateSolution.keySet().toArray()[x];
            if( y < students.size()) {
                otherStudent = (Student) candidateSolution.keySet().toArray()[y];
                areCompatible = doStudentsStudySameStream(student, otherStudent);
                if( areCompatible) {
                    project = candidateSolution.get(student);
                    candidateSolution.put(student, candidateSolution.get(otherStudent));
                    candidateSolution.put(otherStudent, project);
                }
            }
            else {
                otherProject = leftoverProjects.get(y - projects.size());
                areCompatible = student.canDoProject(otherProject);
                if( areCompatible) {
                    project = candidateSolution.get(student);
                    leftoverProjects.add(y - projects.size(), project);
                    leftoverProjects.remove(project);
                    candidateSolution.put(student, otherProject);
                }
            }
        } while (!areCompatible && searchCount <= projectCount / 2);
        fitness = calculateFitness(candidateSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("changed current solution:" + toString());
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("backup:" + backupSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("ENDING MAKERANDOMCHANGE HERE");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private double calculateStudentsFitness(double preferencePoints, double gpa) {
        double overallFitnessPoints;
        double pointsFromPreferencesAlone = (1 - gpaWeight) * preferencePoints;
        double pointsFromGpaAlone = gpaWeight * CandidateSolution.FULL_STUDENT_FITNESS * (gpa / Student.FULL_GPA);
        overallFitnessPoints = pointsFromPreferencesAlone + pointsFromGpaAlone + pointsFromGpaAlone * pointsFromPreferencesAlone;
        return overallFitnessPoints;
    }


    public void undoRandomChange() throws NoRandomChangeWasMadeException {
        //System.out.println("STARTING UNDORANDOMCHANGE HERE");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("changed solution:" + candidateSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("backup:" + backupSolution);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        // TODO test
        if(backupSolution == null) {
            throw new NoRandomChangeWasMadeException();
        }
        else {
            candidateSolution = backupSolution;
            backupSolution = null;
            if(backupLeftoverProjects != null) {
                leftoverProjects = backupLeftoverProjects;
                backupLeftoverProjects = null;
            }
            //System.out.println("new current sol (backup was restored):" + candidateSolution);
            //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            fitness = calculateFitness(candidateSolution);
            //System.out.println("fitness should be:");
            //System.out.print(fitness);
            //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        }
        //System.out.println("ENDING UNDORANDOMCHANGE HERE");

    }

    private boolean doStudentsStudySameStream(Student a, Student b) {
        if(a == null || b == null) {
            return false;
        }
        else {
            return a.getStream() == b.getStream() ||
                    a.getStream() == Stream.commonTesting ||
                    b.getStream() == Stream.commonTesting ||
                    (a.getStream() == Stream.CSDS && b.getStream() == Stream.DS) ||
                    (a.getStream() == Stream.DS && b.getStream() == Stream.CSDS);
        }
    }

    public Double getAverageStudentSatisfaction() {
        double satisfactionSum = 0;
        for(Map.Entry solution : candidateSolution.entrySet()){
            Project project = (Project) solution.getValue();
            Student student = (Student) solution.getKey();
            ArrayList<Project> studentPreferences = student.getPreferences();
            for(int i = 0; (i < FULL_STUDENT_FITNESS) && (i < student.getPreferences().size()); i++){
                if(studentPreferences.get(i)==project){
                    satisfactionSum += (FULL_STUDENT_FITNESS - i);
                    break;
                }
            }
        }
        return satisfactionSum / candidateSolution.size() / 2;
    }


    public int getStudentsSatisfaction(Student student) {
        //System.out.println("Getting this student/project satisfaction:");
        //System.out.println(student);
        Project assignedProject = candidateSolution.get(student);
        //System.out.println(assignedProject);
        for (int i = 0; i < student.getPreferences().size(); ++i) {
            if (assignedProject.isTheSameProject(student.getPreferences().get(i).getProjectName())) {
                //System.out.println("Counting individual satisfaction:" + (FULL_STUDENT_FITNESS - i));
                return (FULL_STUDENT_FITNESS - i) / 2;
            }
        }
        if(!student.canDoProject(candidateSolution.get(student))) return -100;
        else return 0;
    }

    public int countProjectsOfAStreamAvailableInSolution(Stream streamCounted) {
        int streamCount = 0;
        for (Project project :
                projects) {
            if( project.getStream() == streamCounted) ++streamCount;
        }
        if( leftoverProjects != null) {
            //System.out.println("leftover pojects aren't null, here they are" + leftoverProjects);
            for (Project project :
                    leftoverProjects) {
                if (project.getStream() == streamCounted) ++streamCount;
            }
        }
        return streamCount;
    }

    public int countStudentsOfAStreamAvailableInSolution(Stream streamCounted) {
        int streamCount = 0;
        for (Student student :
                students) {
            if( student.getStream() == streamCounted) ++streamCount;
        }
        return streamCount;
    }

    public boolean isALegalSolutionPossible() {
        int csStudents, csdsStudents, csProjects, csdsProjects;
        csStudents = countStudentsOfAStreamAvailableInSolution(Stream.CS);
        csdsStudents = countStudentsOfAStreamAvailableInSolution(Stream.CSDS) + countStudentsOfAStreamAvailableInSolution(Stream.DS);
        csProjects = countProjectsOfAStreamAvailableInSolution(Stream.CS);
        csdsProjects = countProjectsOfAStreamAvailableInSolution(Stream.CSDS) + countProjectsOfAStreamAvailableInSolution(Stream.DS);
        //System.out.println("In isALegalSolutionPossible with (students - projects (cs,csds)):" + csStudents + " " + csdsStudents + " " + csProjects + " " + csdsProjects);
        return csStudents <= csProjects && csdsStudents <= csdsProjects && students.size() <= projects.size();
    }

    public void combineWithAnotherSolution(CandidateSolution otherSolution) throws InvalidArgumentException {
        // doesn't combine backup of the other solution into this one's

        Map<Student, Project> newSolution = new HashMap<>();
        newSolution.putAll(candidateSolution);
        newSolution.putAll(otherSolution.getCandidateSolution());
        candidateSolution = newSolution;

        students.addAll(otherSolution.getStudents());
        projects.addAll(otherSolution.getProjects());
        leftoverProjects.addAll(otherSolution.getLeftoverProjects());
        fitness = calculateFitness(candidateSolution);
    }

    public double getAverageSatisfactionOfGpasWithinClosedInterval(double lowerBound, double upperBound) throws InvalidArgumentException {
        double averageStatisfaction = 0;

        ArrayList<Student> studentsWithinInterval = getStudentsWithGpasInClosedInterval(lowerBound, upperBound);
        if( studentsWithinInterval.size() == 0) throw new InvalidArgumentException("error: no students found withing this range of GPAs");

        for (Student student :
                studentsWithinInterval) {
            averageStatisfaction += getStudentsSatisfaction(student);
        }

        averageStatisfaction /= (double) studentsWithinInterval.size();

        return averageStatisfaction;
    }

    public ArrayList<Student> getStudentsWithGpasInClosedInterval(double lowerBound, double upperBound) throws InvalidArgumentException {
        // inclusive of both bounds
        if( lowerBound > upperBound ||
                lowerBound < 0 ||
                upperBound < 0 ||
                lowerBound > Student.FULL_GPA ||
                upperBound > Student.FULL_GPA) throw new InvalidArgumentException("error: illegal range of GPAs");

        ArrayList<Student> studentsInRange = new ArrayList<>();

        for (Student student :
                students) {
            if( lowerBound <= student.getGpa() && student.getGpa() <= upperBound) studentsInRange.add(student);
        }

        return studentsInRange;
    }

}