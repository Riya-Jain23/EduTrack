package Other;

import Exceptions.CourseFullException;
import Student.Student;
import Professor.Professor;
import TeachingAssistant.TeachingAssistant;
import Other.Feedback;
import java.time.LocalDate;
import java.util.*;

public class Course {
    private String courseCode;
    private String title;
    private Professor professor;
    private int credits;
    private List<String> prerequisites;
    private String schedule;
    private List<Student> enrolledStudents;
    private int enrollmentLimit;
    private String syllabus;
    private int semester;
    private String location;
    private LocalDate DropDateDeadline;
    private TeachingAssistant teachingAssistant;
    private List<Feedback<?>> feedbackList;

    public Course(String courseCode, String title, int credits, String schedule, int enrollmentLimit, int semester, String location, List<String> prerequisites,LocalDate DropDateDeadline) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.schedule = schedule;
        this.enrollmentLimit = enrollmentLimit;
        this.prerequisites = prerequisites;
        this.enrolledStudents = new ArrayList<>();
        this.semester = semester;
        this.location = location;
        this.DropDateDeadline=DropDateDeadline;
        this.feedbackList = new ArrayList<>();
    }

    // Getters and setters
    public String getSyllabus() {
        return syllabus;
    }
    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public LocalDate getDropDateDeadline(){return DropDateDeadline;}
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public LocalDate setDropDateDeadline(LocalDate DropDateDeadline){this.DropDateDeadline = DropDateDeadline;
        return DropDateDeadline;
    }
    public List<String> getPrerequisites() {
        return prerequisites;
    }
    public void setPrerequisites(List<String> prerequisites) {
        this.prerequisites = prerequisites;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public TeachingAssistant getTeachingAssistant(){ return teachingAssistant;}
    public void setTeachingAssistant(TeachingAssistant teachingAssistant){
        this.teachingAssistant = teachingAssistant;
    }
    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }
    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    public void addStudent(Student student) throws CourseFullException {
        if (enrolledStudents.size() >= enrollmentLimit) {
            throw new CourseFullException("Cannot add student " + student.getEmail() + ". Course " + title + " is full.");
            //System.out.println("Cannot add student " + student.getEmail() + ". Course " + title + " is full.");
            //return;
        }
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            System.out.println("Student " + student.getEmail() + " added to course " + title);
        } else {
            System.out.println("Student " + student.getEmail() + " is already enrolled in " + title);
        }
    }

    public <T> void addFeedback(Feedback<T> feedback) {
        feedbackList.add(feedback);
        System.out.println("Feedback added: " + feedback);
    }

    // Method to view feedback
    public List<Feedback<?>> getFeedbackList() {
        return feedbackList;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Credits: " + credits + ", Schedule: " + schedule + ", Enrollment Limit: " + enrollmentLimit + ", Prerequisites: " + prerequisites;
    }
}

