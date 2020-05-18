package repositories;

import entities.Project;
import entities.Student;
import exceptions.DuplicateStudentIdException;
import exceptions.InvalidArgumentException;

import java.util.ArrayList;

public class StudentRepository{
    private ArrayList<Student> students;

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public StudentRepository(){
        students = new ArrayList<>();
    }

    public StudentRepository(ArrayList<Student> students){
        this.students = students;
    }

    public void addStudent(Student student) throws NullPointerException, DuplicateStudentIdException {
        if(!hasStudentById(student.getStudentId())) {
            students.add(student);
            System.out.println("Adding student to sRep:" + student.getName());
        }
        else {
            throw new DuplicateStudentIdException();
        }
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

    public Student getStudentById(long id) {
        Student studentWanted = null;
        for (Student student :
                students) {
            if (student.getStudentId() == id) {
                studentWanted = student;
                break;
            }
        }
        return studentWanted;
        // else throw new InvalidArgumentException();
    }

    public boolean hasStudentById(long id) {
        //System.out.println("Entered hasStudentById with id:" + id);
        boolean status = false;
        for (Student student :
                students) {
            //System.out.println("Iterating over students, current id:" + student.getStudentId());
            if (student.getStudentId() == id) {
                status = true;
                //System.out.println("Common id was found with the above, status equals true");
                break;
            }
        }
        //System.out.println("About to leave hasStudentById with:" + status);
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

    public String toString() {
        String string = "";
        for (Student student :
                students) {
            string += student.getName().trim() + " " + student.getStudentId() + " gpa:" + student.getGpa() + "\n";
            //string = string.trim();
        }
        string = string.trim();
        return string;
    }
}
