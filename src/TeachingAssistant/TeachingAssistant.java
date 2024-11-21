package TeachingAssistant;

import Exceptions.CourseFullException;
import Other.Course;
import Professor.Professor;
import Student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TeachingAssistant extends Student {
    private List<Course> assignedCourses;
    public TeachingAssistant(String email, String password, String name, int semester)  {
        super(email, password, name, semester);
        this.assignedCourses = new ArrayList<>();
    }
    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }
    // TA can manage grades for students
    public void manageGrades(Student student, Course course, String grade) {
        // Assuming there's a method in Student class to set the grade
        student.setGrade(course, grade);
        System.out.println("Grade updated for student: " + student.getEmail());
    }

    // TA can view grades for students
    public void viewGrades(Student student) {
        // Assuming there's a method in Student class to get the grade
        List<Course> registeredCourses = student.getRegisteredCourses();

        if (registeredCourses.isEmpty()) {
            System.out.println(student.getEmail() + " is not registered for any courses.");
            return;
        }

        System.out.println("Courses registered by " + student.getEmail() + ":");
        for (Course course : registeredCourses) {
            System.out.println("Course Code: " + course.getCourseCode() + " - " + course.getTitle());
        }
        Map<Course, String> grades = student.getGrades();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course code to get a grade: ");
        String courseCode = scanner.nextLine();

        Course courseToGrade = null;
        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseToGrade = course;
                break;
            }
        }
        if (courseToGrade != null) {
            String gradesOfStudent = grades.get(courseToGrade);

            if(gradesOfStudent != null) {
                System.out.println("Grade of student " + student.getEmail() + "for course " + courseCode + " is: "+gradesOfStudent);
            }
            else{
                System.out.println("No grade has been found for "+ student.getEmail() + "for course " + courseCode );
            }
        }
        else{
            System.out.println("Course not found in the student's registered courses.");
            return;
        }



    }

    public void updateGrades(Student student) {
        // Assuming there's a method in Student class to get the grade
        List<Course> registeredCourses = student.getRegisteredCourses();

        if (registeredCourses.isEmpty()) {
            System.out.println(student.getEmail() + " is not registered for any courses.");
            return;
        }

        System.out.println("Courses registered by " + student.getEmail() + ":");
        for (Course course : registeredCourses) {
            System.out.println("Course Code: " + course.getCourseCode() + " - " + course.getTitle());
        }
        Map<Course, String> grades = student.getGrades();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course code to update a grade: ");
        String courseCode = scanner.nextLine();

        Course courseToGrade = null;
        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseToGrade = course;
                break;
            }
        }
        if (courseToGrade != null) {
            System.out.println("Enter the new grade that you want to update: ");
            String newGrade = scanner.nextLine();
            if(grades.containsKey(courseToGrade)) {
                grades.put(courseToGrade,newGrade);
                System.out.println("Grade of student " + student.getEmail() + " is updated successfully for course " + courseCode + " The updated grade is: "+newGrade);
            }
            else{
                System.out.println("No grade has been found for "+ student.getEmail() + " to be updated for course " + courseCode );
            }
        }
        else{
            System.out.println("Course not found in the student's registered courses.");
            return;
        }



    }

    public void displayEnrolledStudents(Course course) {
        if (assignedCourses.contains(course)) {
            List<Student> students = course.getEnrolledStudents();
            if (students.isEmpty()) {
                System.out.println("No students enrolled in this course.");
            } else {
                System.out.println("Enrolled Students:");
                for (Student student : students) {
                    System.out.println("Student: " + student.getEmail() + " - ID: " + student.getStudentID());
                }
            }
        } else {
            System.out.println("You are not assigned to this course.");
        }
    }



    // Add course to assignedCourses, ensuring the professor doesn't get more than 2 courses
    public void addAssignedCourse(Course course) {
        if (assignedCourses.size() >= 2) {
            System.out.println("TA " + getEmail() + " cannot be assigned more than 2 courses.");
        } else {
            assignedCourses.add(course);
            System.out.println("Course " + course.getTitle() + " assigned to TA " + getEmail());
        }
    }

    @Override
    public void login() {
        System.out.println("TA " + getEmail() + " logged in.");
    }
    @Override
    public void logout() {
        System.out.println("TA " + getEmail() + " logged out.");
    }

    public void manageGrades(Student student) {

    }
}
