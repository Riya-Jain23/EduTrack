package Professor;

import java.util.*;
import Other.User;
import Other.Course;
import Other.Feedback;
import Student.Student;
import TeachingAssistant.TeachingAssistant;

public class Professor extends User {
    private String professorID;
    private List<Course> assignedCourses;
    private String officeHours;

    public Professor(String email, String password, String professorID, String officeHours) {
        super(email, password);
        this.professorID = professorID;
        this.officeHours = officeHours;
        this.assignedCourses = new ArrayList<>();
    }

    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }
    public String getProfessorID() {
        return professorID;
    }
    public void setAssignedCourses(List<Course> assignedCourses) {
        this.assignedCourses = assignedCourses;
    }
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }
    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }
    public String getOfficeHours() {
        return officeHours;
    }

    @Override
    public void login() {
        System.out.println("Professor " + getEmail() + " logged in.");
    }
    @Override
    public void logout() {
        System.out.println("Professor " + getEmail() + " logged out.");
    }

    public void assignTAToCourses(List<TeachingAssistant> teachingAssistantList, Course course) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available TA's:");
        for (TeachingAssistant teachingAssistant : teachingAssistantList) {
            System.out.println(teachingAssistant.getEmail());
        }

        System.out.print("Enter ta's email to assign to this course: ");
        String professorEmail = scanner.nextLine();

        TeachingAssistant selectedTA = null;
        for (TeachingAssistant teachingAssistant : teachingAssistantList) {
            if (teachingAssistant.getEmail().equalsIgnoreCase(professorEmail)) {
                selectedTA = teachingAssistant;
                break;
            }
        }

        if (selectedTA != null) {
            course.setTeachingAssistant(selectedTA);
            selectedTA.addAssignedCourse(course);  // Ensure the course is added to TA's list
            System.out.println("Assigned TA " + selectedTA.getEmail() + " to " + course.getTitle());
        } else {
            System.out.println("TA not found.");
        }
    }

    // Add course to assignedCourses, ensuring the professor doesn't get more than 2 courses
    public void addAssignedCourse(Course course) {
        if (assignedCourses.size() >= 2) {
            System.out.println("Professor " + getEmail() + " cannot be assigned more than 2 courses.");
        } else {
            assignedCourses.add(course);
            System.out.println("Course " + course.getTitle() + " assigned to Professor " + getEmail());
        }
    }

    // Method to view feedback for a specific course
    public void viewCourseFeedback(Course course) {
        List<Feedback<?>> feedbacks = course.getFeedbackList();
        System.out.println("Feedback for course: " + course.getTitle());
        if (feedbacks.isEmpty()) {
            System.out.println("No feedback available for this course.");
        } else {
            for (Feedback<?> feedback : feedbacks) {
                System.out.println(feedback);
            }
        }
    }

    @FunctionalInterface
    public interface CourseUpdater {
        void update(Course course);
    }
    public void performCourseUpdate(Course course, CourseUpdater updater, String updateMessage) {
        if (assignedCourses.contains(course)) {
            updater.update(course);  // Perform the update using the provided lambda
            System.out.println(updateMessage + " for course: " + course.getTitle());
        } else {
            System.out.println("You are not assigned to this course.");
        }
    }
    public void updateCourseSyllabus(Course course, String newSyllabus) {
        performCourseUpdate(course, c -> c.setSyllabus(newSyllabus), "Updated syllabus");
    }
    public void updateCourseSchedule(Course course, String newSchedule) {
        performCourseUpdate(course, c -> c.setSchedule(newSchedule), "Updated schedule");
    }
    public void updateCourseEnrollmentLimit(Course course, int newLimit) {
        performCourseUpdate(course, c -> c.setEnrollmentLimit(newLimit), "Updated enrollment limit");
    }
    public void updateCoursePrerequisites(Course course, List<String> newPrerequisites) {
        performCourseUpdate(course, c -> c.setPrerequisites(newPrerequisites), "Updated prerequisites");
    }

    // View students enrolled in an assigned course
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
}



