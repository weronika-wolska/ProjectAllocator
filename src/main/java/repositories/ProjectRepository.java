package repositories;

import java.util.*;
import entities.*;

public class ProjectRepository{
    private ArrayList<Project> projects;

    public ProjectRepository(){
        this.projects = new ArrayList<Project>();
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
}
