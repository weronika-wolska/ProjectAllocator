package entities;

public class Student{
    private String firstName;
    private String surname;
    private Long studentId;
    private Stream stream;

    public Student(String firstName, String surname, Long studentId, Stream stream){
        this.firstName=firstName;
        this.surname=surname;
        this.studentId=studentId;
        this.stream=stream;
    }

    public Student(){

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", stream=" + stream + ", studentId=" + studentId + ", surname="
                + surname + "]";
    }
}