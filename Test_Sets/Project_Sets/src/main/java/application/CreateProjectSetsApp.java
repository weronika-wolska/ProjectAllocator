package application;

import entities.StaffMember;
import entities.StaffReader;

import java.util.ArrayList;

public class CreateProjectSetsApp {

    public static void main(String[] args) {
        StaffReader reader = new StaffReader();
        reader.readXLSX(30, "src/main/resources/staff.xlsx");

        for (StaffMember staff: reader.getFaculty()) {
            System.out.println(staff.toString());
        }
    }
}
