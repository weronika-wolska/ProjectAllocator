package entities.unit;

import entities.*;
import exceptions.InvalidArgumentException;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class CandidateSolutionTest {
    protected ArrayList<Student> students = new ArrayList<>();
    protected ArrayList<Project> projects = new ArrayList<>();
    protected ArrayList<Student> students2 = new ArrayList<>();

    Student student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS);
    Student student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS);
    Student student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS);
    Student student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS);
    Student student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS);

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

    @Test
    public void testCandidateSolutionWithValidInputs() throws InvalidArgumentException{
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
        student1.setPreferences(preferences);
        student2.setPreferences(preferences);
        student3.setPreferences(preferences);
        student4.setPreferences(preferences);
        student5.setPreferences(preferences);
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
        
        /*// test getFitness()
        Assert.assertEquals(40, candidateSolution.getFitness());*/
        
        // test findProjectAssignedToStudent()
        Assert.assertEquals(project1, candidateSolution.findProjectAssignedToStudent(student1));
        Assert.assertEquals(project5, candidateSolution.findProjectAssignedToStudent(student5));
        
        // test findStudentWithProject()
        Assert.assertEquals(student1, candidateSolution.findStudentWithProject(project1));
        Assert.assertEquals(student4, candidateSolution.findStudentWithProject(project4));
    }

    @Test
    public void testCandidateSolutionWithInvalidInputs() throws InvalidArgumentException{
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
        student3.setPreferences(preferences);
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
    public void testToString() {
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

        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        preferences.add(project4);
        preferences.add(project5);

        student1.setPreferences(preferences);
        student2.setPreferences(preferences);
        student3.setPreferences(preferences);
        student4.setPreferences(preferences);
        student5.setPreferences(preferences);
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
        return "student Tracy Jackson doing DS was assigned\n" +
                "project create interface which is in the stream DS\n" +
                "student Robert Murphy doing DS was assigned\n" +
                "project create tests for classes which is in the stream CS\n" +
                "student Becky Jones doing CS was assigned\n" +
                "project create test data which is in the stream CS\n" +
                "student Jessica Delaney doing CS was assigned\n" +
                "project create classes which is in the stream CS\n" +
                "student Bob Johnson doing CS was assigned\n" +
                "project create more classes which is in the stream DS\n";
    }
}