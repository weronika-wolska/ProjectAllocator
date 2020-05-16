package entities;

import java.util.ArrayList;

public class StaffMember{
    private String name;
    private ArrayList<String> researchActivities;
    private ArrayList<String> researchAreas;
    private Stream specialFocus;
    private ArrayList<String> projects;

    public StaffMember() {
        this("DefaultName", null, null, Stream.CS);
    }

    public StaffMember(String name, ArrayList<String> researchActivity, ArrayList<String> researchAreas, Stream specialFocus){
        setName(name);
        setSpecialFocus(specialFocus);
        setResearchActivities(researchActivity);
        setResearchAreas(researchAreas);
        setProjects(null);
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
        try {
            filterOutDefaultStringValues(researchActivities);
        }
        catch(NullPointerException npe) {
            //npe.printStackTrace();
            researchActivities = new ArrayList<>();
        }
        //System.out.println("In SM.setRAc with" + researchActivities);
        this.researchActivities = researchActivities;
    }

    public ArrayList<String> getResearchAreas() {
        return researchAreas;
    }

    public void setResearchAreas(ArrayList<String> researchAreas) {
        try {
            filterOutDefaultStringValues(researchAreas);
        }
        catch(NullPointerException npe) {
            //npe.printStackTrace();
            researchAreas = new ArrayList<>();
        }
        //System.out.println("In SM.setRAr with" + researchAreas);
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
        try {
            filterOutDefaultStringValues(projects);
        }
        catch(NullPointerException npe) {
            //npe.printStackTrace();
            projects = new ArrayList<>();
        }
        //System.out.println("In SM.setP with" + projects);
        this.projects = projects;
    }

    public void addProject(String projectName) {
        projects.add(projectName);
    }

    private void filterOutDefaultStringValues(ArrayList<String> values) {
        //System.out.println("In SM.filter pre with values:" + values);
        values.removeIf(value -> value.trim().toLowerCase().equals(""));
        //System.out.println("In SM.filter post with values:" + values);
    }
    public String projectsToString() {
        String projectString = "\nMy projects are:\n";
        for (String project :
                projects) {
            projectString += project + "\n";
        }
        return projectString;
    }

    public boolean isTheSameStaffMember(StaffMember other) {
        //System.out.println("Entered isTheSameStaffMember.");
        if(other == null) return false;
        else {
            boolean uno = name.trim().toLowerCase().equals(other.getName().trim().toLowerCase());
            //System.out.println("Checking non null other staff member with:" + uno);
            return name.trim().toLowerCase().equals(other.getName().trim().toLowerCase());
        }
    }

    public static boolean areListsTheSame(ArrayList<String> listOne, ArrayList<String> listTwo) {
        if(listOne == null && listTwo == null) return true;
        else if(listOne == null || listTwo == null) return false;
        else {
            if(listOne.size() != listTwo.size()) return false;
            else {
                for (int i = 0; i < listOne.size(); ++i) {
                    if(!listOne.get(i).trim().toLowerCase().equals(listTwo.get(i).trim().toLowerCase())) return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "StaffMember [name=" + name + ", researchActivities=" + researchActivities + ", researchAreas=" + researchAreas
                + ", specialFocus=" + specialFocus + "]" + projectsToString();
    }


}
