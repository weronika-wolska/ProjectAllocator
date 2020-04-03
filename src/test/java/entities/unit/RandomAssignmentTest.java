package entities.unit;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import entities.Student;

import java.util.ArrayList;

public class RandomAssignmentTest {
    protected ArrayList<Student> students;
    protected ArrayList<Project> projects;
    //protected ArrayList<Student> students2;

    StaffMember staffCS;
    StaffMember staffDS;

    Student student1;
    Student student2;
    Student student3;
    Student student4;
    Student student5;
    Student student6;

    Project project1;
    Project project2;
    Project project3;
    Project project4;
    Project project5;
    Project project6;
    //Project project7;
    //Project project8;
    //Project project9;
    //Project project10;

    public void setUp() {
        students = new ArrayList<>();
        projects = new ArrayList<>();
        //students2 = new ArrayList<>();

        student1 = new Student("Becky", "Jones", (long) 12345, Stream.CS);
        student2 = new Student("Jessica", "Delaney", (long) 23456, Stream.CS);
        student3 = new Student("Robert", "Murphy", (long) 34567, Stream.DS);
        student4 = new Student("Bob", "Johnson", (long) 45678, Stream.CS);
        student5 = new Student("Tracy", "Jackson", (long) 56789, Stream.DS);

        staffCS = new StaffMember();
        staffCS.setName("Jane CS Doe");
        staffDS = new StaffMember();
        staffDS.setName("John DS Smith");

        project1 = new Project("create test data");
        project2 = new Project("create classes");
        project3 = new Project("create tests for classes");
        project1.setStream(Stream.CS);
        project2.setStream(Stream.CS);
        project3.setStream(Stream.CS);
        project1.setSupervisor(staffCS);
        project2.setSupervisor(staffCS);
        project3.setSupervisor(staffCS);

        project4 = new Project("create more classes");
        project5 = new Project("create interface");
        project6 = new Project("create application");
        project4.setStream(Stream.DS);
        project5.setStream(Stream.DS);
        project6.setStream(Stream.DS);
        project4.setSupervisor(staffDS);
        project5.setSupervisor(staffDS);
        project6.setSupervisor(staffDS);

        //project7 = new Project("do something");
        //project8 = new Project("do something else");
        //project9 = new Project("do another thing");
        //project10 = new Project("create something");
    }
}
