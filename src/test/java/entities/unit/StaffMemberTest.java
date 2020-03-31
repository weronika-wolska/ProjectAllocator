package entities.unit;

import java.util.ArrayList;

import entities.StaffMember;
import entities.Stream;

import org.junit.Assert;
import org.junit.Test;


public class StaffMemberTest {
    protected StaffMember staff1 = new StaffMember();
    



    @Test
    public void testSetName(){
        String name = "Jonathan Delaney";
        staff1.setName(name);
        //Assert.assertTrue(staff1.getName()==name);
        Assert.assertEquals("setName not working corectly", staff1.getName(), name);
    }

    @Test
    public void testSetSpecialFocus(){
        Stream specialFocus = Stream.CSDS;
        staff1.setSpecialFocus(specialFocus);
        //Assert.assertTrue(specialFocus==staff1.getSpecialFocus());
        Assert.assertEquals("setSpecialFocus not working correctly", specialFocus, staff1.getSpecialFocus());
    }

    @Test
    public void testSetResearchAreas(){
        ArrayList<String> researchAreas = new ArrayList<String>();
        researchAreas.add("AI");
        researchAreas.add("Cognitive Science");
        researchAreas.add("Computational Creativity");
        staff1.setResearchAreas(researchAreas);
        Assert.assertEquals("setResearchAreas not working as expected", staff1.getResearchAreas(), researchAreas);
        //Assert.assertTrue(staff1.getResearchAreas()==researchAreas);
        //Assert.asserTrue(staff1.getResearchAreas().get(0)=="AI");
        //Assert.asserTrue(staff1.getResearchAreas().get(1)=="Cognitive Science");
        //Assert.asserTrue(staff1.getResearchAreas().get(2)=="Computational Creativity");
    }

    @Test
    public void testSetResearchActivities(){
        ArrayList<String> researchActivities = new ArrayList<String>();
        researchActivities.add("checking test cases");
        staff1.setResearchActivities(researchActivities);
        Assert.assertEquals("setResearchActivities not working as expected", staff1.getResearchActivities(), researchActivities);
        //Assert.assertTrue(staff1.getResearchActivities()==researchActivities);
    }
}