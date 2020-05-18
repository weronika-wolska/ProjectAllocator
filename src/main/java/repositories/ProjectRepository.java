package repositories;

import java.util.*;
import entities.*;

public class ProjectRepository{
    private ArrayList<Project> projects;

    public ProjectRepository(){
        this.projects = new ArrayList<Project>();
    }

    public ProjectRepository(ArrayList<Project> projects){
        this.projects = projects;
    }

    public void addProject(Project project) throws NullPointerException{
        projects.add(project);
    }

    public void removeProject(Project project){
        projects.remove(project);
    }

    public Project getProject(int index){
        return projects.get(index);
    }

    public int getSize(){
        return projects.size();
    }
    public boolean hasProjectByName(String projectName) {
        boolean status = false;
        for (Project project :
                projects) {
            if (project.isTheSameProject(projectName)) {
                status = true;
                break;
            }
        }
        return status;
    }

    public ArrayList<Project> getProjects(){
        return this.projects;
    }
    public Project getProjectByName(String projectName) {
        Project projectWanted = null;
        for (Project project :
                projects) {
            if (project.isTheSameProject(projectName)) {
                projectWanted = project;
                break;
            }
        }
        if(projectWanted != null) return projectWanted;
        else return null;
    }


}
