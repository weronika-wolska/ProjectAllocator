package entities.unit;

import entities.StaffMember;
import entities.Stream;
import entities.Student;
import exceptions.InvalidArgumentException;
import repositories.ProjectRepository;
import entities.Project;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;

//import org.apache.tools.ant.Project;
import org.junit.Assert;



public class StudentTest {
    static ProjectRepository projectRepository = new ProjectRepository();

    @Test
    public void testDefaultConstructor() {
        Student student = new Student();
        Assert.assertNotNull("default constructor not working correctly", student);
    }

    @Test
    public void testConstructor4Parameters() {
        Student student = new Student("John", "Smith", (long) 12345678, Stream.CS);
        Assert.assertEquals("student.name not initialized correctly", "John", student.getFirstName());
        Assert.assertEquals("student.surname not initialized correctly", "Smith", student.getSurname());
        Assert.assertEquals("student.id not initialized correctly", 12345678, student.getStudentId().longValue());
        Assert.assertEquals("student.stream not initialized correctly", Stream.CS, student.getStream());
        Assert.assertNull("student.preferences not initialized correctly", student.getPreferences());
    }

    @Test
    public void testSetters() {
        Student student = new Student("Jane", "Doe", (long) 87654321, Stream.DS);

        student.setFirstName("John");
        student.setSurname("Smith");
        student.setStudentId((long) 12345678);
        student.setStream(Stream.CS);

        Assert.assertEquals("student.name not initialized correctly", "John", student.getFirstName());
        Assert.assertEquals("student.surname not initialized correctly", "Smith", student.getSurname());
        Assert.assertEquals("student.id not initialized correctly", 12345678, student.getStudentId().longValue());
        Assert.assertEquals("student.stream not initialized correctly", Stream.CS, student.getStream());
    }

    // setPreferences has separate test since it needs more assertions
    @Test
    public void testSetPreferences() throws InvalidArgumentException{
        

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
        ArrayList<Project> preferencesCS = new ArrayList<>();
        preferencesCS.add(project);
        preferencesCS.add(project2);
        preferencesCS.add(project6);
        preferencesCS.add(project7);
        preferencesCS.add(project8);
        preferencesCS.add(project9);
        preferencesCS.add(project10);
        preferencesCS.add(project11);
        preferencesCS.add(project12);
        preferencesCS.add(project13);
        ArrayList<Project> preferencesDS = new ArrayList<>();
        preferencesDS.add(project);
        preferencesDS.add(project3);
        preferencesDS.add(project4);
        preferencesDS.add(project5);
        preferencesDS.add(project14);
        preferencesDS.add(project15);
        preferencesDS.add(project10);
        preferencesDS.add(project11);
        preferencesDS.add(project12);
        preferencesDS.add(project9);

        // test with full preferences and correct streams
        Student studentCS = new Student("firstName", "surname", (long) 12345, Stream.CS, preferencesCS);
        Assert.assertEquals(preferencesCS, studentCS.getPreferences());
        Student studentDS = new Student("firstName", "surname", (long) 123456, Stream.DS, preferencesDS);
        Assert.assertEquals(preferencesDS, studentDS.getPreferences());

        ArrayList<Project> preferencesCSShort = new ArrayList<>();
        preferencesCSShort.add(project);
        preferencesCSShort.add(project2);
        preferencesCSShort.add(project6);
        preferencesCSShort.add(project7);
        preferencesCSShort.add(project8);
        ArrayList<Project> preferencesDSShort = new ArrayList<>();
        preferencesDSShort.add(project);
        preferencesDSShort.add(project3);
        preferencesDSShort.add(project4);
        preferencesDSShort.add(project5);
        preferencesDSShort.add(project14);

        // test with <10 preferences
        Student studentCSS = new Student("firstName", "surname", (long) 2345, Stream.CS, preferencesCSShort);
        Assert.assertEquals(10, studentCSS.getPreferences().size());
        Student studentDSS = new Student("firstName", "surname", (long) 23456, Stream.DS, preferencesDSShort);
        Assert.assertEquals(10, studentDSS.getPreferences().size());

        // test invalid projects
        ArrayList<Project> invalidPreferences = new ArrayList<>();
        invalidPreferences.add(project);
        invalidPreferences.add(project2);
        invalidPreferences.add(project3);
        invalidPreferences.add(project4);
        invalidPreferences.add(project5);
        invalidPreferences.add(project6);
        invalidPreferences.add(project7);
        invalidPreferences.add(project8);
        invalidPreferences.add(project9);
        invalidPreferences.add(project10);
        Assert.assertThrows(InvalidArgumentException.class, new ThrowingRunnable(){
        
            @Override
            public void run() throws Throwable {
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
                ArrayList<Project> invalidPreferences = new ArrayList<>();
                invalidPreferences.add(project);
                invalidPreferences.add(project2);
                invalidPreferences.add(project3);
                invalidPreferences.add(project4);
                invalidPreferences.add(project5);
                invalidPreferences.add(project6);
                invalidPreferences.add(project7);
                invalidPreferences.add(project8);
                invalidPreferences.add(project9);
                invalidPreferences.add(project10);
                Student badStudent = new Student("firstName", "surname",(long) 345677, Stream.CS, invalidPreferences);
                
            }
        });

        // test exception is thrown if the preference list is too long
        
        Assert.assertThrows(InvalidArgumentException.class, new ThrowingRunnable(){
        
            @Override
            public void run() throws Throwable {
                StaffMember supervisor = new StaffMember();
                Project project = new Project("projectName", Stream.CSDS, supervisor);
                Project project2 = new Project("projectName", Stream.CS, supervisor);
                Project project6 = new Project("projectName", Stream.CS, supervisor);
                Project project7 = new Project("projectName", Stream.CS, supervisor);
                Project project8 = new Project("projectName", Stream.CS, supervisor);
                Project project9 = new Project("projectName", Stream.CSDS, supervisor);
                Project project10 = new Project("projectName", Stream.CSDS, supervisor);
                Project project11 = new Project("projectName", Stream.CSDS, supervisor);
                Project project12 = new Project("projectName", Stream.CSDS, supervisor);
                Project project13 = new Project("projectName", Stream.CS, supervisor);
                Project p = new Project("projectName", Stream.CS, supervisor);
                ArrayList<Project> tooManyPreferences = new ArrayList<>();
                tooManyPreferences.add(p);
                tooManyPreferences.add(project2);
                tooManyPreferences.add(project);
                tooManyPreferences.add(project6);
                tooManyPreferences.add(project7);
                tooManyPreferences.add(project8);
                tooManyPreferences.add(project9);
                tooManyPreferences.add(project10);
                tooManyPreferences.add(project11);
                tooManyPreferences.add(project12);
                tooManyPreferences.add(project13);

                Student s = new Student("firstName", "surname", (long) 583534, Stream.CS, tooManyPreferences);
                
            }
        });
    }
}
