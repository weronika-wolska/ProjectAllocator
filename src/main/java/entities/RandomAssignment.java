package entities;

import java.util.ArrayList;
import java.util.Random;

public class RandomAssignment {
    private int projectCount;

    // assumes there exists a legal assignment
    public RandomAssignment() {}

    public void randomize(ArrayList<Student> students, ArrayList<Project> projects) {
        ArrayList<Project> shuffledProjects = new ArrayList<>();
        boolean assignedProject;
        for (Student student: students) {
            assignedProject = false;
            while (!assignedProject) {
                projectCount = projects.size();
                Project potentialAssignment = projects.get(getRandomProjectIndex());
                if(student.canDoProject(potentialAssignment)) {
                    assignedProject = true;
                    projects.remove(potentialAssignment);
                    shuffledProjects.add(potentialAssignment);
                }
            }
        }
        projects.addAll(shuffledProjects);
    }

    private int getRandomProjectIndex() {
        Random rand = new Random();
        int nextIndex = 0;
        //System.out.println("project count=" + projectCount);
        if(projectCount != 1) {
            nextIndex = rand.nextInt(projectCount - 1);
        }
        //System.out.println("nextIndex=" + nextIndex);
        return nextIndex;
    }
}
