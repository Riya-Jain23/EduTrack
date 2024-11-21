package Student;

import Exceptions.CourseFullException;
import Exceptions.DropDeadlinePassedException;
import Other.Course;
import Other.User;
import Other.Complaint;
import Other.Feedback;
import Administrator.Administrator;

import java.time.LocalDate;
import java.util.*;

public class Student extends User {
    private String studentID;
    private int semester;
    private List<Course> registeredCourses;
    protected Map<Course, String> grades;
    private List<Complaint> complaints;
    private Map<String, String> completedCourses = new HashMap<String, String>(); // To store past completed courses and grades
    private Map<Course, List<Feedback<?>>> feedbackHistory;
    private static final int MAX_CREDITS = 20;
    private static final int MIN_COURSES = 4;

    public Student(String email, String password, String studentID, int semester) {
        super(email, password);
        this.studentID = studentID;
        this.semester = semester;
        this.completedCourses = new HashMap<String, String>();
        this.registeredCourses = new ArrayList<>();
        this.grades = new HashMap<>();
        this.complaints = new ArrayList<>();
        this.feedbackHistory = new HashMap<>();
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getStudentID() {
        return studentID;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public int getSemester() {
        return semester;
    }
    public List<Complaint> getComplaints() {
        return complaints;
    }
    public Map<Course, String> getGrades() {
        return grades;
    }
    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }
    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }
    public void setGrades(Map<Course, String> grades) {
        this.grades = grades;
    }
    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
    public Map<Course, List<Feedback<?>>> getFeedbackHistory() {
        return feedbackHistory;
    }
    public void setGrade(Course course, String grade) {
        this.grades.put(course, grade);  // Assuming grades is a Map<Course, String>
    }
    @Override
    public void login() {
        System.out.println("Student " + getEmail() + " logged in.");
    }
    @Override
    public void logout() {
        System.out.println("Student " + getEmail() + " logged out.");
    }

    // View available courses
    public void viewAvailableCourses(List<Course> courseList) {
        System.out.println("Available courses for Semester " + semester + ":");
        for (Course course : courseList) {
            if (course.getSemester() == semester) {
                System.out.println(course);
            }
        }
    }

    public boolean canProceedToNextSemester() {
        if (grades.isEmpty()) {
            System.out.println("You have not completed any courses yet.");
            return false;
        }
        for (Course course : registeredCourses) {
            if (!grades.containsKey(course)) {
                System.out.println("You need grades for all courses to proceed to the next semester.");
                return false;
            }
        }
        setSemester(semester + 1);
        System.out.println("Proceeding to Semester " + semester);
        registeredCourses.clear();  // Clear registered courses for the next semester
        return true;
    }

    // Ensure student registers for at least 4 courses and does not exceed 20 credits
    public void registerForCourse(List<Course> courseList, String courseCode) throws CourseFullException {
        try {
            // Step 1: Find the course
            Course courseToRegister = null;
            for (Course course : courseList) {
                if (course.getCourseCode().equalsIgnoreCase(courseCode) && course.getSemester() == semester) {
                    courseToRegister = course;
                    break;
                }
            }

            // Step 2: Check if the course is available for the semester
            if (courseToRegister == null) {
                System.out.println("Course not found or not available for the current semester.");
                return;
            }

            // Step 3: Check prerequisites
            List<String> prerequisites = courseToRegister.getPrerequisites();
            if (!prerequisites.isEmpty() && !arePrerequisitesMet(prerequisites)) {
                System.out.println("You must complete the prerequisites before registering for this course.");
                return;
            }

            // Step 4: Check if registering will exceed the credit limit
            int totalCredits = registeredCourses.stream().mapToInt(Course::getCredits).sum();
            if (totalCredits + courseToRegister.getCredits() > MAX_CREDITS) {
                System.out.println("Cannot register for " + courseToRegister.getTitle() + ". Credit limit exceeded.");
                return;
            }

            // Step 5: Add the course to the registered courses if all conditions are met
            registeredCourses.add(courseToRegister);
            courseToRegister.addStudent(this);  // Add student to the course's enrolled list
            System.out.println("Registered for " + courseToRegister.getTitle());

            // Step 6: Ensure the student registers for at least the minimum number of courses
            if (registeredCourses.size() < MIN_COURSES) {
                System.out.println("You need to register for at least " + MIN_COURSES + " courses this semester.");
            }
        }
        catch (CourseFullException ex){
            throw new CourseFullException("CourseFullException Occured "+ex.getMessage());
        }
    }


    private boolean arePrerequisitesMet(List<String> prerequisites) {
        for (String prerequisiteCode : prerequisites) {
            boolean prerequisiteMet = false;

            for (Course course : grades.keySet()) {
                if (course.getCourseCode().equalsIgnoreCase(prerequisiteCode)) {
                    String grade = grades.get(course);
                    if (isPassingGrade(grade)) {
                        prerequisiteMet = true;
                        break;
                    }
                }
            }
            // Check if the prerequisite is completed in past semesters (completedCourses map)
            if (!prerequisiteMet) {
                String completedGrade = completedCourses.get(prerequisiteCode);
                if (completedGrade != null && isPassingGrade(completedGrade)) {
                    prerequisiteMet = true;
                }
            }
            // If prerequisite not met, return false
            if (!prerequisiteMet) {
                System.out.println("Prerequisite not met: " + prerequisiteCode);
                return false;
            }
        }
        return true;  // All prerequisites met
    }


    // Helper method to check if the grade is passing (assuming grades A to D are passing)
    private boolean isPassingGrade(String grade) {
        return !grade.equalsIgnoreCase("F");
    }


    public void viewSchedule() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("Your schedule: ");
            for (Course course : registeredCourses) {
                System.out.println("Course: " + course.getTitle() + ", Time: " + course.getSchedule() +
                        ", Location: " + course.getLocation() +
                        ", Professor: " + (course.getProfessor() != null ? course.getProfessor().getEmail() : "TBD"));
            }
        }
    }

    // Calculate CGPA across all semesters and SGPA for current semester
    public void trackAcademicProgress() {
        System.out.println("Tracking academic progress...");

        double totalCredits = 0;
        double totalGradePoints = 0;
        double semesterCredits = 0;
        double semesterGradePoints = 0;

        for (Map.Entry<Course, String> entry : grades.entrySet()) {
            Course course = entry.getKey();
            String grade = entry.getValue();
            double gradePoints = convertGradeToPoints(grade);
            totalCredits += course.getCredits();
            totalGradePoints += gradePoints * course.getCredits();

            if (course.getSemester() == this.semester) {
                semesterCredits += course.getCredits();
                semesterGradePoints += gradePoints * course.getCredits();
            }
            System.out.println("Course: " + course.getTitle() + " - Grade: " + grade);
        }

        if (totalCredits > 15) {
            double cgpa = totalGradePoints / totalCredits;
            System.out.println("CGPA: " + cgpa);
        }

        if (semesterCredits > 15) {
            double sgpa = semesterGradePoints / semesterCredits;
            System.out.println("SGPA for Semester " + semester + ": " + sgpa);
        } else {
            System.out.println("No grades available for this semester yet. Make sure you have registered for all courses or check with admin regarding course grading.");
        }
    }

    // Convert grade to GPA points
    private double convertGradeToPoints(String grade) {
        switch (grade) {
            case "A+": return 10.0;
            case "A": return 9.0;
            case "B": return 8.0;
            case "B-": return 7.0;
            case "C": return 6.0;
            case "D": return 5.0;
            case "F": return 2.0;
            default: return 0.0;
        }
    }

    // dropCourse method
    public void dropCourse() throws DropDeadlinePassedException {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses to drop.");
            return;
        }
        System.out.println("Your registered courses:");
        for (int i = 0; i < registeredCourses.size(); i++) {
            System.out.println((i + 1) + ". " + registeredCourses.get(i).getTitle());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the course you want to drop: ");
        int courseNumber = scanner.nextInt();

        if (courseNumber < 1 || courseNumber > registeredCourses.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        for (Course course : registeredCourses) {
            if (!course.getDropDateDeadline().isBefore(LocalDate.now())) {
                Course courseToDrop = registeredCourses.get(courseNumber - 1);
                registeredCourses.remove(courseToDrop);
                System.out.println("Dropped " + courseToDrop.getTitle());
                break;
            }
            else{
                throw new DropDeadlinePassedException("You have exceeded the drop deadline");
            }


        }

        //throw new DropDeadlinePassedException("You have exceeded the drop deadline");



        //Course courseToDrop = registeredCourses.get(courseNumber - 1);
        //registeredCourses.remove(courseToDrop);
        //System.out.println("Dropped " + courseToDrop.getTitle());
    }

    // Submit complaint
    public void submitComplaint(String description, Administrator admin) {
        if (description != null && !description.trim().isEmpty()) {
            Complaint complaint = new Complaint(description, this);
            complaints.add(complaint);  // Add to student's personal complaint list
            admin.getComplaints().add(complaint);  // Add to admin's list of complaints
            System.out.println("Complaint submitted with ID: " + complaint.getComplaintID());
        } else {
            System.out.println("Complaint description cannot be empty.");
        }
    }

    // View complaint status
    public void viewComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints submitted.");
        } else {
            for (Complaint complaint : complaints) {
                System.out.println("Complaint ID: " + complaint.getComplaintID() + ", Status: " + complaint.getStatus());
                System.out.println("Description: " + complaint.getDescription());
                if (Complaint.STATUS_RESOLVED.equals(complaint.getStatus())) {
                     System.out.println("Resolution: " + complaint.getResolutionDescription());
                }
            }
        }
    }

    public void submitFeedback(Course course, Feedback<?> feedback) {
        course.addFeedback(feedback); // Assuming Course has a method to handle feedback
        feedbackHistory.putIfAbsent(course, new ArrayList<>());
        feedbackHistory.get(course).add(feedback);
        System.out.println("Feedback submitted for course: " + course.getTitle());
    }

    // Optionally, add a method to view feedback history
    public void viewFeedbackHistory() {
        for (Map.Entry<Course, List<Feedback<?>>> entry : feedbackHistory.entrySet()) {
            Course course = entry.getKey();
            System.out.println("Feedback for course: " + course.getTitle());
            for (Feedback<?> feedback : entry.getValue()) {
                System.out.println("Feedback: " + feedback);
            }
        }
    }
}

