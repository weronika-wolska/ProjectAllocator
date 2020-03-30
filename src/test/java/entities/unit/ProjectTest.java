package entities.unit;

import entities.Project;
import entities.StaffMember;
import entities.Stream;
import org.junit.Assert;
import org.junit.Test;

public class ProjectTest {
    @Test
    public void testConstructor() {
        StaffMember supervisor = new StaffMember();
        supervisor.setName("John Smith");
        Project project = new Project("A project about projects.", Stream.CS, supervisor);

        Assert.assertEquals("project.projectName not initialized correctly", "A project about projects.", project.getProjectName());
        Assert.assertEquals("project.stream not initialized correctly", Stream.CS, project.getStream());
        Assert.assertEquals("project.stream not initialized correctly", supervisor, project.getSupervisor());
    }

    @Test
    public void testSetters() {
        StaffMember supervisorOld = new StaffMember();
        supervisorOld.setName("Jane Doe");
        Project project = new Project("A project not about projects.", Stream.DS, supervisorOld);

        StaffMember supervisorNew = new StaffMember();
        supervisorNew.setName("John Smith");

        project.setProjectName("A project about projects.");
        project.setStream(Stream.CS);
        project.setSupervisor(supervisorNew);

        Assert.assertEquals("project.projectName not initialized correctly", "A project about projects.", project.getProjectName());
        Assert.assertEquals("project.stream not initialized correctly", Stream.CS, project.getStream());
        Assert.assertEquals("project.stream not initialized correctly", supervisorNew, project.getSupervisor());
    }

    @Test
    public void testToString() {
        StaffMember supervisor = new StaffMember();
        supervisor.setName("John Smith");
        Project project = new Project("A project about projects.", Stream.CS, supervisor);

        Assert.assertEquals("project.toString working incorrectly", "Project [projectName=A project about projects., stream=CS, supervisor=John Smith]", project.toString());
    }
}
