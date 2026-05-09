package models;

public class Enrollment {

    private int enrollId;
    private int studentId;
    private int courseId;
    private String enrollDate;

    public Enrollment(int enrollId, int studentId, int courseId, String enrollDate) {
        this.enrollId = enrollId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollDate = enrollDate;
    }

    public int getEnrollId() {
        return enrollId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }
}


