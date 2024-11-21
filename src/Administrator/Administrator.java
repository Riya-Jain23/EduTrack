package Administrator;
import java.util.*;
import Student.Student;
import Professor.Professor;
import Other.User;
import Other.Course;
import Other.Complaint;
// Administrator.java
public class Administrator extends User {
    private String adminID;
    private List<Student> studentList;
    private List<Professor> professorList;
    private List<Complaint> complaints;

    public Administrator(String email, String password, String adminID) {
        super(email, password);
        this.adminID = adminID;
        this.studentList = new ArrayList<>();
        this.professorList = new ArrayList<>();
        this.complaints = new ArrayList<>();
    }

    public void setAdminID(String adminID) {
        if (adminID != null && !adminID.trim().isEmpty()) {
            this.adminID = adminID;
        } else {
            System.out.println("Invalid Admin ID");
        }
    }

    public String getAdminID() {
        return adminID;
    }
    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
    public List<Complaint> getComplaints() {
        return complaints;
    }
    @Override
    public void login() {
        System.out.println("Administrator " + getEmail() + " logged in.");
    }
    @Override
    public void logout() {
        System.out.println("Administrator " + getEmail() + " logged out.");
    }


    public void manageCourseCatalog(List<Course> courseList, boolean add) {
        System.out.println("Courses in the catalog:");
        for (Course course : courseList) {
            System.out.println(course);
        }
        for (Course course : courseList) {
            if (add) {
                System.out.println("Course " + course.getTitle() + " added to catalog.");
            } else {
                System.out.println("Course " + course.getTitle() + " removed from catalog.");
            }
        }
    }

    public void assignProfessorsToCourses(List<Professor> professorList, Course course) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available professors:");
        for (Professor professor : professorList) {
            System.out.println(professor.getEmail());
        }

        System.out.print("Enter professor's email to assign to this course: ");
        String professorEmail = scanner.nextLine();

        Professor selectedProfessor = null;
        for (Professor professor : professorList) {
            if (professor.getEmail().equalsIgnoreCase(professorEmail)) {
                selectedProfessor = professor;
                break;
            }
        }

        if (selectedProfessor != null) {
            course.setProfessor(selectedProfessor);
            selectedProfessor.addAssignedCourse(course);  // Ensure the course is added to professor's list
            System.out.println("Assigned Professor " + selectedProfessor.getEmail() + " to " + course.getTitle());
        } else {
            System.out.println("Professor not found.");
        }
    }

    public void handleComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints to resolve.");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        for (Complaint complaint : complaints) {
            System.out.println("Complaint ID: " + complaint.getComplaintID() + ", Status: " + complaint.getStatus());
            System.out.println("Description: " + complaint.getDescription());

            if (complaint.getStatus().equals(Complaint.STATUS_PENDING)) {
                System.out.println("Resolving complaint: " + complaint.getComplaintID());
                System.out.print("Enter resolution description: ");  // Prompt admin for resolution description
                String resolutionDescription = scanner.nextLine();
                complaint.resolve(resolutionDescription);
            }
        }
    }


    public void manageStudentRecords(Student student) {
        // Display the student's registered courses
        List<Course> registeredCourses = student.getRegisteredCourses();

        if (registeredCourses.isEmpty()) {
            System.out.println(student.getEmail() + " is not registered for any courses.");
            return;
        }

        System.out.println("Courses registered by " + student.getEmail() + ":");
        for (Course course : registeredCourses) {
            System.out.println("Course Code: " + course.getCourseCode() + " - " + course.getTitle());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course code to assign a grade: ");
        String courseCode = scanner.nextLine();

        Course courseToGrade = null;
        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                courseToGrade = course;
                break;
            }
        }
        if (courseToGrade == null) {
            System.out.println("Course not found in the student's registered courses.");
            return;
        }

        System.out.print("Enter the grade for " + courseToGrade.getTitle() + ": ");
        String grade = scanner.nextLine();

        if (isValidGrade(grade)) {
            student.getGrades().put(courseToGrade, grade);
            System.out.println("Grade " + grade + " assigned for course " + courseToGrade.getTitle() + " to student " + student.getEmail());
        } else {
            System.out.println("Invalid grade. Please enter a valid grade (A+, A, B, B+, C, D, F).");
        }
    }

    private boolean isValidGrade(String grade) {
        return grade.equalsIgnoreCase("A") ||
                grade.equalsIgnoreCase("B") ||
                grade.equalsIgnoreCase("B-") ||
                grade.equalsIgnoreCase("A+") ||
                grade.equalsIgnoreCase("C") ||
                grade.equalsIgnoreCase("D") ||
                grade.equalsIgnoreCase("F");
    }

}

