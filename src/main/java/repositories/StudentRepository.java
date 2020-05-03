package repositories;

import java.util.ArrayList;


import entities.Student;

import entities.*;
import exceptions.*;

public class StudentRepository{
    private ArrayList<Student> students;

    public StudentRepository(){
        this.students = new ArrayList<Student>();
    }

    public void addStudent(Student student) throws NullPointerException{
        students.add(student);
    }

    public ArrayList<Student> getStudents(){
        return this.students;
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
    public boolean hasStudentById(long id) {
        boolean status = false;
        for (Student student :
                students) {
            if (student.getStudentId() == id) {
                status = true;
                break;
            }
        }
        return status;
    }

    public boolean hasStudentByName(String firstName, String surname) {
        boolean status = false;
        for (Student student :
                students) {
            if (student.hasName(firstName, surname)) {
                status = true;
                break;
            }
        }
        return status;
    }

    public Student getStudentByName(String firstName, String surname) throws InvalidArgumentException {
        Student studentWanted = null;
        for (Student student :
                students) {
            if (student.hasName(firstName, surname)) {
                studentWanted = student;
                break;
            }
        }
        if(studentWanted != null) return studentWanted;
        else throw new InvalidArgumentException();
    }

    public boolean hasStudent(Student potentialStudent) {
        return hasStudentById(potentialStudent.getStudentId()) || hasStudentByName(potentialStudent.getFirstName(), potentialStudent.getSurname());
    }

    public Student getStudent(Student student) throws InvalidArgumentException {
        Student studentWanted = null;
        if(hasStudentById(student.getStudentId())) {
            studentWanted = getStudentById(student.getStudentId());
        }
        else if(hasStudentByName(student.getFirstName(), student.getSurname())) {
            studentWanted = getStudentByName(student.getFirstName(), student.getSurname());
        }
        return studentWanted;
    }
    public Student getStudentById(long id) throws InvalidArgumentException {
        Student studentWanted = null;
        for (Student student :
                students) {
            if (student.getStudentId() == id) {
                studentWanted = student;
                break;
            }
        }
        if(studentWanted != null) return studentWanted;
        else throw new InvalidArgumentException();
    }


    public ArrayList<Student> getStudentsWithoutPreferences() throws InvalidArgumentException{
        ArrayList<Student> studentsInQuestion = new ArrayList<>();
        for (Student student :
                students) {
            try {
                if(student.hasNoPreferences()) studentsInQuestion.add(student);
            }
            catch (NullPointerException npe) {
                student.setPreferences(new ArrayList<>());
                studentsInQuestion.add(student);
                npe.printStackTrace();
            }
        }
        return studentsInQuestion;
    }

}
