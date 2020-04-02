package entities;

import java.util.ArrayList;
import java.util.Random;

public class RandomAssignment {
    private int projectCount;

    // assumes there exists a legal assignment
    public void RandomAssignment(ArrayList<Student> students, ArrayList<Project> projects) {
        projectCount = students.size();
        ArrayList<Project> shuffledProjects = new ArrayList<>();
        boolean assignedProject;
        for (Student student: students) {
            assignedProject = false;
            while (!assignedProject) {
                Project potentialAssignment = projects.get(getRandomProjectIndex());
                if(student.canDoProject(potentialAssignment)) {
                    assignedProject = true;
                    projects.remove(potentialAssignment);
                    shuffledProjects.add(potentialAssignment);
                }
            }
        }

    }

    private int getRandomProjectIndex() {
        Random rand = new Random();
        return rand.nextInt(--projectCount);
    }
}
