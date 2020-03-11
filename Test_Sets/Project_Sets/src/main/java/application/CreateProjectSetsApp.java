package application;

import entities.StaffMember;
import entities.StaffReader;

import java.util.ArrayList;

public class CreateProjectSetsApp {

    public static void main(String[] args) {
        StaffReader reader = new StaffReader();
        reader.readXLSX(30, "/staff.xlsx");
        String input = "I,love,you";
        System.out.println(input);
        ArrayList<String> output = reader.tokenizeByComma(input);
        for (String string: output) {
            System.out.println(string);
        }

        for (StaffMember staff: reader.getFaculty()) {
            System.out.println(staff.toString());
        }
    }
}
