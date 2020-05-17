package entities.unit;

import entities.*;
import exceptions.InvalidArgumentException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CandidateSolutionWriterTest {
    private Student student0;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;
    private Student student6;
    private Student student7;
    private Student student8;
    private Student student9;

    private Project project0;
    private Project project1;
    private Project project2;
    private Project project3;
    private Project project4;
    private Project project5;
    private Project project6;
    private Project project7;
    private Project project8;
    private Project project9;

    private StaffMember staff0;
    private StaffMember staff1;
    private StaffMember staff2;

    private ArrayList<Student> students;
    private ArrayList<Project> projects;
    private ArrayList<ArrayList<Project>> studentPreferences;
    private CandidateSolution currentSolution;

    private ArrayList<String> createStringList() {
        ArrayList<String> auxiliaryStringList = new ArrayList<>(3);
        auxiliaryStringList.add("a");
        auxiliaryStringList.add("b");
        auxiliaryStringList.add("c");
        return auxiliaryStringList;
    }

    private void setAStudentsPreferencesToIndices(int studentNum, int[] indices) {
        ArrayList<Project> preferencesOfStudent = studentPreferences.get(studentNum);
        for (int index :
                indices) {
            preferencesOfStudent.add(projects.get(index));
        }
    }

    private void createStudentPreferences() {
        studentPreferences = new ArrayList<>();
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());
        studentPreferences.add(new ArrayList<>());

        setAStudentsPreferencesToIndices(0, new int[]{9,8,7,6,5,4,3,2,1,0});
        setAStudentsPreferencesToIndices(1, new int[]{9,8,6,7,5,4,2,1,0,3});
        setAStudentsPreferencesToIndices(2, new int[]{4,3,2,1,0,9,8,7,6,5});
        setAStudentsPreferencesToIndices(3, new int[]{4,3,2,1,0,9,8,7,6,5});
        setAStudentsPreferencesToIndices(4, new int[]{2,3,4,5,6,7,8,9,0,1});
        setAStudentsPreferencesToIndices(5, new int[]{8,7,6,5,4,3,2,1,0,9});
        setAStudentsPreferencesToIndices(6, new int[]{7,6,5,4,3,2,1,0,9,8});
        setAStudentsPreferencesToIndices(7, new int[]{6,5,4,3,2,1,0,9,8,7});
        setAStudentsPreferencesToIndices(8, new int[]{3,4,5,6,7,8,9,0,1,2});
        setAStudentsPreferencesToIndices(9, new int[]{0,1,2,3,4,5,6,7,8,9});
    }

    private void setupA() throws InvalidArgumentException {
        ArrayList<String> auxiliaryStringList = createStringList();
        staff0 = new StaffMember("Florence Nightingale", auxiliaryStringList, auxiliaryStringList, Stream.CS);
        staff1 = new StaffMember("Joseph Lister", auxiliaryStringList, auxiliaryStringList, Stream.CSDS);
        staff2 = new StaffMember("Louis Pasteur", auxiliaryStringList, auxiliaryStringList, Stream.DS);

        projects = new ArrayList<>();
        project0 = new Project("Nurses in CS.", Stream.commonTesting, staff0);
        project1 = new Project("Grime in CS.", Stream.commonTesting, staff0);
        project2 = new Project("Pretty hats in CS.", Stream.commonTesting, staff0);
        project3 = new Project("Cleaning in CS.", Stream.commonTesting, staff0);
        project4 = new Project("Killing gangrene in CS and DS.", Stream.commonTesting, staff1);
        project5 = new Project("Listerine in CS and DS.", Stream.commonTesting, staff1);
        project6 = new Project("Dealing with being ridiculed in CS and DS.", Stream.commonTesting, staff1);
        project7 = new Project("Cheese in DS.", Stream.commonTesting, staff2);
        project8 = new Project("Heat it and leave it, a motto in DS.", Stream.commonTesting, staff2);
        project9 = new Project("Germ theory vs DS.", Stream.commonTesting, staff2);
        projects.add(project0);
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        projects.add(project7);
        projects.add(project8);
        projects.add(project9);
        createStudentPreferences();

        students = new ArrayList<>();
        student0 = new Student("Mary", "Mother of God", (long) 01234567, 2.5, Stream.commonTesting, studentPreferences.get(0));
        student1 = new Student("Alexander", "Great", (long) 12345670, 4.2, Stream.commonTesting, studentPreferences.get(1));
        student2 = new Student("Bartholomew", "Dias", (long) 23456701, 1.8, Stream.commonTesting, studentPreferences.get(2));
        student3 = new Student("Xenophilius", "Lovegood", (long) 34567012, 2.7, Stream.commonTesting, studentPreferences.get(3));
        student4 = new Student("Saint", "Patrick", (long) 45670123, 2.9, Stream.commonTesting, studentPreferences.get(4));
        student5 = new Student("Jozef", "Pilsodzki", (long) 56701234, 3.4, Stream.commonTesting, studentPreferences.get(5));
        student6 = new Student("Ewa", "Farna", (long) 67012345, 3.3, Stream.commonTesting, studentPreferences.get(6));
        student7 = new Student("Ted", "Bundy", (long) 70123456, 3.9, Stream.commonTesting, studentPreferences.get(7));
        student8 = new Student("Al", "Bundy", (long) 10234567, 1.2, Stream.commonTesting, studentPreferences.get(8));
        student9 = new Student("Lionel", "Messy", (long) 12345678, 2.4, Stream.commonTesting, studentPreferences.get(9));
        students.add(student0);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);

        RandomAssignment randomAssignment = new RandomAssignment();
        randomAssignment.randomize(students, projects);
        currentSolution = new CandidateSolution(students, projects, 0.5);
    }

    @Test
    public void writeXLSXTest1() throws InvalidArgumentException {
        setupA();
        CandidateSolutionWriter writer = new CandidateSolutionWriter();
        Assert.assertTrue(writer.writeXLSX("src/test/testResources/CandidateSolutionWriterExam1.xlsx", currentSolution));
        //writer.writeXLSX("src/test/testResources/CandidateSolutionWriterExam1.xlsx", currentSolution);
    }
}
