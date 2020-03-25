package entities;

import java.util.ArrayList;

public class StaffMember{
    private String name;
    private ArrayList<String> researchActivities;
    private ArrayList<String> researchAreas;
    private Stream specialFocus;
    private ArrayList<String> projects;

    public StaffMember() {

    }

    public StaffMember(String name, ArrayList<String> researchActivity, ArrayList<String> researchAreas, Stream specialFocus){
        this.name=name;
        this.researchActivities =researchActivity;
        this.researchAreas=researchAreas;
        this.specialFocus=specialFocus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getResearchActivities() {
        return researchActivities;
    }

    public void setResearchActivities(ArrayList<String> researchActivities) {
        this.researchActivities = researchActivities;
    }

    public ArrayList<String> getResearchAreas() {
        return researchAreas;
    }

    public void setResearchAreas(ArrayList<String> researchAreas) {
        this.researchAreas = researchAreas;
    }

    public Stream getSpecialFocus() {
        return specialFocus;
    }

    public void setSpecialFocus(Stream specialFocus) {
        this.specialFocus = specialFocus;
    }

    public ArrayList<String> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<String> projects) {
        this.projects = projects;
    }

    public String projectsToString() {
        String projectString = "\nMy projects are:\n";
        for (String project :
                projects) {
            projectString += project + "\n";
        }
        return projectString;
    }

    @Override
    public String toString() {
        return "StaffMember [name=" + name + ", researchActivities=" + researchActivities + ", researchAreas=" + researchAreas
                + ", specialFocus=" + specialFocus + "]" + projectsToString();
    }

    
}