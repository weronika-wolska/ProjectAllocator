package repositories;

import entities.*;
import java.util.ArrayList;

import entities.StaffMember;

public class StaffRepository{
    private ArrayList<StaffMember> staffMembers;

    public StaffRepository(){

    }

    public void addStaffMember(StaffMember staffMember) throws NullPointerException{
        staffMembers.add(staffMember);
    }

    public void removeStaffMember(StaffMember staffMember){
        staffMembers.remove(staffMember);
    }

    public StaffMember getStaffMember(int index){
        return staffMembers.get(index);
    }

    public int getSize(){
        return staffMembers.size();
    }
}
