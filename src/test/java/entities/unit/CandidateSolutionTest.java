package entities.unit;

import entities.*;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class CandidateSolutionTest{ 
    protected ArrayList<Student> students = new ArrayList<>();
    protected ArrayList<Project> projects = new ArrayList<>();
    protected ArrayList<Student> students2 = new ArrayList<>();

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Student student5;
    Project project1 = new Project("create test data");
    Project project2 = new Project("create classes");
    Project project3 = new Project("create tests for classes");
    Project project4 = new Project("create more classes");
    Project project5 = new Project("create interface");
    Project project6 = new Project("create application");
    Project project7 = new Project("do something");
    Project project8 = new Project("do something else");
    Project project9 = new Project("do another thing");
    Project project10 = new Project("create something");

    public ProjectRepository setupRepository(){
        ProjectRepository projectRepository = new ProjectRepository();
        StaffMember supervisor = new StaffMember();
        Project project1 = new Project("create test data");
        Project project2 = new Project("create classes");
        Project project3 = new Project("create tests for classes");
        Project project4 = new Project("create more classes");
        Project project5 = new Project("create interface");
        Project project6 = new Project("create application");
        Project project7 = new Project("do something");
        Project project8 = new Project("do something else");
        Project project9 = new Project("do another thing");
        Project project10 = new Project("create something");
        Project project11 = new Project("projectName", Stream.CSDS, supervisor);
        Project project12 = new Project("projectName", Stream.CSDS, supervisor);
        Project project13 = new Project("projectName", Stream.CS, supervisor);
        Project project14 = new Project("project", Stream.DS, supervisor);
        Project project15 = new Project("project", Stream.DS, supervisor);
        projectRepository.addProject(project1);
        projectRepository.addProject(project2);
        projectRepository.addProject(project3);
        projectRepository.addProject(project4);
        projectRepository.addProject(project5);
        projectRepository.addProject(project6);
        projectRepository.addProject(project7);
        projectRepository.addProject(project8);
        projectRepository.addProject(project9);
        projectRepository.addProject(project10);
        projectRepository.addProject(project11);
        projectRepository.addProject(project12);
        projectRepository.addProject(project13);
        projectRepository.addProject(project14);
        projectRepository.addProject(project15);
        return projectRepository;
    }

    private void setUp() throws InvalidArgumentException {
        project1.setStream(Stream.CS);
        project2.setStream(Stream.CS);
        project3.setStream(Stream.DS);
        project4.setStream(Stream.CS);
        project5.setStream(Stream.DS);
        project6.setStream(Stream.CS);
        project7.setStream(Stream.CS);
        project8.setStream(Stream.CS);
        project9.setStream(Stream.CS);
        project10.setStream(Stream.CS);
        ProjectRepository projectRepository = setupRepository();
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, projectRepository);
    }

    @Test
    public void testCandidateSolutionWithValidInputs() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Student> studentss = new ArrayList<>();
        ArrayList<Project> projectss = new ArrayList<>();
        // setting up array lists
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        //Student student = new Student(firstName, surname, studentId, gpa, stream, preferences, projectRepository)
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, preferences, projectRepository);
        studentss.add(student1);
        studentss.add(student2);
        studentss.add(student3);
        studentss.add(student4);
        studentss.add(student5);
        projectss.add(project1);
        projectss.add(project2);
        projectss.add(project3);
        projectss.add(project4);
        projectss.add(project5);


        CandidateSolution candidateSolution = new CandidateSolution(studentss, projectss);
        
        // test getFitness()
        Assert.assertEquals(40, candidateSolution.getFitness());
        
        // test findProjectAssignedToStudent()
        Assert.assertEquals(project1, candidateSolution.findProjectAssignedToStudent(student1));
        Assert.assertEquals(project5, candidateSolution.findProjectAssignedToStudent(student5));
        
        // test findStudentWithProject()
        Assert.assertEquals(student1, candidateSolution.findStudentWithProject(project1));
        Assert.assertEquals(student4, candidateSolution.findStudentWithProject(project4));
    }

    @Test
    public void testCalculateFitness()throws InvalidArgumentException{
        Student student = new Student(setupRepository());
        ArrayList<Project> preferences = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(setupRepository().getProject(1));
        preferences.add(setupRepository().getProject(1));
        CandidateSolution solution = new CandidateSolution(students, projects);
        Assert.assertEquals(10, solution.getFitness());
    }

    @Test
    public void testCandidateSolutionWithInvalidInputs() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        Project project1 = new Project("create test data");
        Project project2 = new Project("create classes");
        Project project3 = new Project("create tests for classes");
        Project project4 = new Project("create more classes");
        Project project5 = new Project("create interface");
        Project project6 = new Project("create application");
        Project project7 = new Project("do something");
        Project project8 = new Project("do something else");
        Project project9 = new Project("do another thing");
        Project project10 = new Project("create something");
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        
        Assert.assertThrows(null, InvalidArgumentException.class, new ThrowingRunnable(){
        
            @Override
            public void run() throws Throwable {
                CandidateSolution candidateSolution = new CandidateSolution(students, projects);
            }
        } );
    }


    @Test
    public void testCalculateFitnessWithAssignedProjectNotInPreferenceList() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        Project project1 = new Project("create test data");
        Project project2 = new Project("create classes");
        Project project3 = new Project("create tests for classes");
        Project project4 = new Project("create more classes");
        Project project5 = new Project("create interface");
        Project project6 = new Project("create application");
        Project project7 = new Project("do something");
        Project project8 = new Project("do something else");
        Project project9 = new Project("do another thing");
        Project project10 = new Project("create something");
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS,preferences, projectRepository);
        Project p = new Project("this is not a preferred project");
        p.setStream(Stream.CS);
        projects.add(p);
        students.add(student1);
        CandidateSolution solution = new CandidateSolution(students, projects);
        Assert.assertEquals(-50, solution.getFitness(), 0.01);
    }

    @Test
    public void testGetEnergy() throws InvalidArgumentException {
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Project> preferences = new ArrayList<>();
        Project project1 = new Project("create test data");
        Project project2 = new Project("create classes");
        Project project3 = new Project("create tests for classes");
        Project project4 = new Project("create more classes");
        Project project5 = new Project("create interface");
        Project project6 = new Project("create application");
        Project project7 = new Project("do something");
        Project project8 = new Project("do something else");
        Project project9 = new Project("do another thing");
        Project project10 = new Project("create something");
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS,preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolution = new CandidateSolution(students, projects);
        Assert.assertEquals(40, candidateSolution.getFitness(), 0.01);
        Assert.assertEquals(25, candidateSolution.getEnergy(), 0.01);
    }

    @Test
    public void testGpaWeight() throws InvalidArgumentException {
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS,preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, preferences,projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, preferences, projectRepository);
        
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolution = new CandidateSolution(students, projects);
        Assert.assertEquals("gpaWeight not initialized correctly in 2 parameter constructor", 0, candidateSolution.getGpaWeight(), 0.01);
        candidateSolution = new CandidateSolution(students, projects, 0.5);
        Assert.assertEquals("gpaWeight not initialized correctly with legal 3 parameter constructor", 0.5, candidateSolution.getGpaWeight(), 0.01);
        candidateSolution = new CandidateSolution(students, projects, 0);
        Assert.assertEquals("gpaWeight not initialized correctly with legal 3 parameter constructor, bottom bound", 0, candidateSolution.getGpaWeight(), 0.01);
        candidateSolution = new CandidateSolution(students, projects, 1);
        Assert.assertEquals("gpaWeight not initialized correctly with legal 3 parameter constructor, top bound", 1, candidateSolution.getGpaWeight(), 0.01);
        candidateSolution = new CandidateSolution(students, projects, -0.5);
        Assert.assertEquals("gpaWeight not initialized correctly with illegal 3 parameter constructor, bottom spillage", 0, candidateSolution.getGpaWeight(), 0.01);
        candidateSolution = new CandidateSolution(students, projects, 1.5);
        Assert.assertEquals("gpaWeight not initialized correctly with illegal 3 parameter constructor, top spillage", 1, candidateSolution.getGpaWeight(), 0.01);
    }

    @Test
    public void testFitnessGpaMatters() throws InvalidArgumentException {
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345,1.0, Stream.CS,preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, 1.1, Stream.CS,preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, 3.9, Stream.DS,preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, 4.0,Stream.CS,preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, 4.2, Stream.DS,preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolutionHighFit = new CandidateSolution(students, projects,0.9);

        //student1.setGpa(1.0);
        //student2.setGpa(1.1);
        //student3.setGpa(3.9);
        //student4.setGpa(4.0);
        //student5.setGpa(4.2);
        students.clear();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        CandidateSolution candidateSolutionLowFit = new CandidateSolution(students, projects,0.9);

        boolean isFirstFitLargerThanSecond = candidateSolutionHighFit.getFitness() > candidateSolutionLowFit.getFitness();
        boolean isFirstEnergySmallerThanSecond = candidateSolutionHighFit.getEnergy() < candidateSolutionLowFit.getEnergy();
        Assert.assertTrue("fitness doesn't make sense when gpa matters", isFirstFitLargerThanSecond);
        Assert.assertTrue("energy doesn't make sense when gpa matters", isFirstEnergySmallerThanSecond);
    }

    @Test
    public void testFitnessGpaDoesNotMatter() throws InvalidArgumentException {
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, 4.2, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, 4.0, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, 3.0, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, 2.0, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, 1.0, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolution1 = new CandidateSolution(students, projects,0);

        student1.setGpa(1.0);
        student2.setGpa(1.1);
        student3.setGpa(3.9);
        student4.setGpa(4.0);
        student5.setGpa(4.2);
        students.clear();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        CandidateSolution candidateSolution2 = new CandidateSolution(students, projects,0);

        boolean areTheEnergiesTheSame = candidateSolution1.getEnergy() == candidateSolution2.getEnergy();
        Assert.assertTrue("energy does not make sense when gpa does not matter", areTheEnergiesTheSame);
    }

    @Test
    public void testFitnessGpaMattersSlightly() throws InvalidArgumentException {
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, 4.2, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, 4.0, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, 3.9, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, 1.1, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, 1.0, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolutionHighFit = new CandidateSolution(students, projects,0.5);

        student1.setGpa(1.0);
        student2.setGpa(1.1);
        student3.setGpa(3.9);
        student4.setGpa(4.0);
        student5.setGpa(4.2);
        students.clear();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);

        CandidateSolution candidateSolutionLowFit = new CandidateSolution(students, projects,0.5);

        boolean isFirstFitLargerThanSecond = candidateSolutionHighFit.getFitness() > candidateSolutionLowFit.getFitness();
        boolean isFirstEnergySmallerThanSecond = candidateSolutionHighFit.getEnergy() < candidateSolutionLowFit.getEnergy();
        Assert.assertTrue("fitness doesn't make sense when gpa matters", isFirstFitLargerThanSecond);
        Assert.assertTrue("energy doesn't make sense when gpa matters", isFirstEnergySmallerThanSecond);
    }

    @Test
    public void testFitnessWithDifferentGpas() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, 4.2, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, 4.2, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, 4.2, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, 4.0, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, 0.1, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolutionHighGpaWeight = new CandidateSolution(students, projects,0.9);
        CandidateSolution candidateSolutionLowGpaWeight = new CandidateSolution(students, projects,0.1);


        System.out.println("Here they are:" + candidateSolutionHighGpaWeight.getFitness() + " and " + candidateSolutionLowGpaWeight.getFitness());
        boolean isSecondFitLargerThanFirst = candidateSolutionHighGpaWeight.getFitness() < candidateSolutionLowGpaWeight.getFitness();
        boolean isSecondEnergySmallerThanFirst = candidateSolutionHighGpaWeight.getEnergy() > candidateSolutionLowGpaWeight.getEnergy();
        Assert.assertTrue("fitness doesn't make sense when gpa matters", isSecondFitLargerThanFirst);
        Assert.assertTrue("energy doesn't make sense when gpa matters", isSecondEnergySmallerThanFirst);
    }

    @Test
    public void testIsThereDuplicateProjects() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);
        Student student1 = new Student("Becky", "Jones", (long) 12345, 4.2, Stream.CS, preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, 4.2, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, 4.2, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, 4.0, Stream.CS, preferences, projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, 0.1, Stream.DS, preferences,projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project4);
        projects.add(project4);
        projects.add(project4);
        projects.add(project4);
        projects.add(project4);

        Assert.assertThrows(null, InvalidArgumentException.class, new ThrowingRunnable(){

            @Override
            public void run() throws Throwable {
                CandidateSolution candidateSolution = new CandidateSolution(students, projects,0.9);
            }
        } );
        }


    @Test
    public void testChangeSolution() throws InvalidArgumentException{
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        preferences.add(project6);
        preferences.add(project7);
        preferences.add(project8);
        preferences.add(project9);
        preferences.add(project10);

        student1.setPreferences(preferences);
        student2.setPreferences(preferences);
        students.clear();
        students.add(student1);
        students.add(student2);
        projects.clear();
        projects.add(project2);
        projects.add(project1);

        CandidateSolution cs = new CandidateSolution(students, projects);

        Assert.assertEquals(false, cs.changeSolution(cs.getCandidateSolution(), students));

        ArrayList<Project> preferences2 = new ArrayList<>();
        preferences2.add(project2);
        preferences2.add(project1);
        preferences2.add(project3);
        preferences2.add(project4);
        preferences2.add(project5);
        preferences2.add(project6);
        preferences2.add(project7);
        preferences2.add(project8);
        preferences2.add(project9);
        preferences2.add(project10);
        student2.setPreferences(preferences2);
        students.clear();
        students.add(student1);
        students.add(student2);
        projects.clear();
        projects.add(project2);
        projects.add(project1);
        CandidateSolution cS = new CandidateSolution(students, projects);
        Assert.assertEquals(true, cS.changeSolution(cS.getCandidateSolution(), students));
    }

    @Test
    public void testToString() throws InvalidArgumentException {
        StaffMember staffCS = new StaffMember();
        staffCS.setName("Jane CS Doe");
        StaffMember staffDS = new StaffMember();
        staffDS.setName("John DS Smith");

        project1.setStream(Stream.CS);
        project2.setStream(Stream.CS);
        project3.setStream(Stream.CS);
        project1.setSupervisor(staffCS);
        project2.setSupervisor(staffCS);
        project3.setSupervisor(staffCS);

        project4.setStream(Stream.DS);
        project5.setStream(Stream.DS);
        project4.setSupervisor(staffDS);
        project5.setSupervisor(staffDS);
        ProjectRepository projectRepository = setupRepository();
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);
        Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS,preferences, projectRepository);
        Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS, preferences, projectRepository);
        Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS, preferences, projectRepository);
        Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS, preferences,projectRepository);
        Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS, preferences, projectRepository);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);

        CandidateSolution candidateSolution;
        try {
            candidateSolution = new CandidateSolution(students, projects);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Assert.assertEquals("CandidateSolution.toString operating incorrectly", getRequiredStringResult(), candidateSolution.toString());
    }

    private String getRequiredStringResult() {
        return "student Becky Jones doing CS was assigned\n" +
                "project create test data which is in the stream CS\n" +
                "student Jessica Delaney doing CS was assigned\n" +
                "project create classes which is in the stream CS\n" +
                "student Robert Murphy doing DS was assigned\n" +
                "project create tests for classes which is in the stream CS\n" +
                "student Bob Johnson doing CS was assigned\n" +
                "project create more classes which is in the stream DS\n"+
                "student Tracy Jackson doing DS was assigned\n" +
                "project create interface which is in the stream DS\n";
                
    }

    
 }
