package repositories;

import java.util.ArrayList;


import entities.Student;

import entities.*;

public class StudentRepository{
    private ArrayList<Student> students;

    public StudentRepository(){
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student) throws NullPointerException{
        students.add(student);
    }

    public Student getStudent(int index){
        return students.get(index);
    }

    public void removeStudent(Student student){
        students.remove(student);
    }

    public int getSize(){
        return students.size();
    }
}
