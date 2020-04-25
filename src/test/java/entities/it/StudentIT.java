package entities.it;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import entities.Student;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;

import org.apache.tools.ant.helper.ProjectHelperImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import javax.activity.InvalidActivityException;

public class StudentIT {

    public ProjectRepository setupRepository(){
        ProjectRepository projectRepository = new ProjectRepository();
        StaffMember supervisor = new StaffMember();
        Project project = new Project("projectName", Stream.CSDS, supervisor);
        Project project2 = new Project("projectName", Stream.CS, supervisor);
        Project project3 = new Project("project", Stream.DS, supervisor);
        Project project4 = new Project("project", Stream.DS, supervisor);
        Project project5 = new Project("project", Stream.DS, supervisor);
        Project project6 = new Project("projectName", Stream.CS, supervisor);
        Project project7 = new Project("projectName", Stream.CS, supervisor);
        Project project8 = new Project("projectName", Stream.CS, supervisor);
        Project project9 = new Project("projectName", Stream.CSDS, supervisor);
        Project project10 = new Project("projectName", Stream.CSDS, supervisor);
        Project project11 = new Project("projectName", Stream.CSDS, supervisor);
        Project project12 = new Project("projectName", Stream.CSDS, supervisor);
        Project project13 = new Project("projectName", Stream.CS, supervisor);
        Project project14 = new Project("project", Stream.DS, supervisor);
        Project project15 = new Project("project", Stream.DS, supervisor);
        projectRepository.addProject(project);
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

    @Test
    public void testConstructor8Parameters() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences, projectRepository);

        if (student.getFirstName() != null) {
            Assert.assertEquals("firstName initialized incorrectly", "John", student.getFirstName());
        }
        if (student.getSurname() != null) {
            Assert.assertEquals("surname initialized incorrectly", "Smith", student.getSurname());
        }
        if (student.getStudentId() != null) {
            Assert.assertEquals("", 12345678, student.getStudentId().longValue());
        }
        if (student.getStream() != null) {
            Assert.assertEquals("surname initialized incorrectly", Stream.CS, student.getStream());
        }
        if (student.getPreferences() != null) {
            Assert.assertEquals("preferences initialized incorrectly", preferences, student.getPreferences());
        }
    }

    @Test
    public void testToString() throws InvalidArgumentException{
        ProjectRepository projectRepository = setupRepository();
        StaffMember supervisor = new StaffMember();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.CSDS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences, projectRepository);
        String expected = "Student [firstName=John, stream=CS, studentId=12345678, surname=Sm" +
                "ith, preferences=Project [projectName=" +
                "The CS Project, stream=CS, supervisor=Jane Doe]\n" +
                "Project [projectName=The DS Project, stream=CSDS, supervisor=Jane Doe]\n" +
                "Project [projectName=The CSDS Project, stream=CSDS, supervisor=Jane Doe]\n" +
                "]";
        Assert.assertEquals("toString working incorrectly", expected, student.toString());
    }

    @Test
    public void testCanDoProject() throws InvalidArgumentException {
        StaffMember supervisor = new StaffMember();
        ProjectRepository projectRepository = setupRepository();
        supervisor.setName("Jane Doe");
        Project project1 = new Project("The CS Project", Stream.CS, supervisor);
        Project project2 = new Project("The DS Project", Stream.DS, supervisor);
        Project project3 = new Project("The CSDS Project", Stream.CSDS, supervisor);
        ArrayList<Project> preferences = new ArrayList<>();
        preferences.add(project1);
        preferences.add(project2);
        preferences.add(project3);
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS, preferences, projectRepository);

        Assert.assertTrue(student.canDoProject(project1));
        Assert.assertFalse(student.canDoProject(project2));
        Assert.assertFalse(student.canDoProject(project3));

        student.setStream(Stream.DS);

        Assert.assertFalse(student.canDoProject(project1));
        Assert.assertTrue(student.canDoProject(project2));
        Assert.assertTrue(student.canDoProject(project3));

        student.setStream(Stream.CSDS);

        Assert.assertFalse(student.canDoProject(project1));
        Assert.assertTrue(student.canDoProject(project2));
        Assert.assertTrue(student.canDoProject(project3));
    }
}
