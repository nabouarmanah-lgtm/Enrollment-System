package models;

public class Student {

    private int student_id;
    private String name;

    public Student(int student_id, String name) {
        this.student_id = student_id;
        this.name = name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getName() {
        return name;
    }
}