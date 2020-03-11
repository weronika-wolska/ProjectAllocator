package application;

import entities.ProjectWriter;
import entities.StaffMember;
import entities.StaffReader;

public class CreateProjectSetsApp {

    public static void main(String[] args) {
        StaffReader reader = new StaffReader();
        ProjectWriter writer = new ProjectWriter();

        reader.readXLSX(30, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects60.xlsx",reader.getFaculty());

        reader.readXLSX(60, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects120.xlsx",reader.getFaculty());

        reader.readXLSX(120, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects240.xlsx",reader.getFaculty());

        reader.readXLSX(250, "src/main/resources/staff.xlsx");
        writer.write("src/main/resources/projects500.xlsx",reader.getFaculty());
    }
}
