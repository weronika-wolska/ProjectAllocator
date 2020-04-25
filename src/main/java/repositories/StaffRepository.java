package repositories;

import entities.*;
import java.util.ArrayList;

import entities.StaffMember;

public class StaffRepository{
    private ArrayList<StaffMember> staffMembers;

    public StaffRepository(){
        this.staffMembers = new ArrayList<StaffMember>();
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

    public StaffMember getStaffMember(String name){
        for(int i=0; i< staffMembers.size();i++){
            if(staffMembers.get(i).getName()==name){
                return staffMembers.get(i);
            }
        }
        return null;
    }

    public int getSize(){
        return staffMembers.size();
    }
}
