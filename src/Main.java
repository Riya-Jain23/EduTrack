//All needed imports
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Administrator.Administrator;
import Exceptions.CourseFullException;
import Exceptions.DropDeadlinePassedException;
import Exceptions.InvalidLoginException;
import Student.Student;
import Professor.Professor;
import Other.User;
import Other.Course;
import Other.Complaint;
import TeachingAssistant.TeachingAssistant;
import Other.Feedback;

public class Main {
    public static void main(String[] args) throws InvalidLoginException, CourseFullException, DropDeadlinePassedException {
        //Initialisation
        Administrator admin = new Administrator("admin@iiitd", "admin123", "A1");
        List<Course> courseList = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();
        List<Professor> professorList = new ArrayList<>();
        List<Complaint> complaints = new ArrayList<>();
        List<TeachingAssistant> teachingAssistantList = new ArrayList<>();

        // Adding sample studentList (3 students) and sample professorList (3 professors)
        studentList.add(new Student("aditya@iiitd.ac.in", "aditya", "Aditya Mehra", 1));
        studentList.add(new Student("anya@iiitd.ac.in", "anya", "Anya Goyal", 1));
        TeachingAssistant riri = new TeachingAssistant("riri@iiitd.ac.in", "riri", "Riri Sharma", 1);
        teachingAssistantList.add(riri);
        studentList.add(riri);
        professorList.add(new Professor("prof1@iiitd.ac.in", "pass123", "P1", "3-4:30 PM Mon/Thurs"));
        professorList.add(new Professor("prof2@iiitd.ac.in", "pass123", "P2", "1-3PM Wed/Tues"));
        professorList.add(new Professor("prof3@iiitd.ac.in", "pass123", "P3", "2-3 PM Fri/Sat/Mon"));

        // Adding courses, semester 1, semester 2, semester 3
        courseList.add(new Course("CS101", "Intro to Programming", 4, "Mon/Wed 9-11 AM", 2, 1, "Room 101", new ArrayList<>(),LocalDate.of(2024, 07, 1)));
        courseList.add(new Course("MTH101", "Linear Algebra", 4, "Tues/Thurs 9-11 AM", 360, 1, "Room 102", new ArrayList<>(),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("DES101", "Introduction to HCI", 4, "Thurs/Fri 1-3 PM", 360, 1, "Room C01", new ArrayList<>(),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("SSH101", "Communication Skills", 4, "Tues/Wed 3-6 PM", 360, 1, "Room 102", new ArrayList<>(),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("ECE101", "Digital Circuits", 4, "Mon/Fri 11-1 PM", 360, 1, "Room 201", new ArrayList<>(),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("CS102", "Data Structures", 4, "Tue/Thu 10-12 AM", 360, 2, "Room 102", Arrays.asList("CS101"),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("MTH102", "Probability and Statistics", 4, "Mon/Wed 10-12 AM", 180, 2, "Room B-003 ", Arrays.asList("MTH101"),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("CS103", "Advanced Programming", 4, "Tue/Thu 10-12 AM", 360, 3, "Room 102", Arrays.asList("CS101, CS102"),LocalDate.of(2024, 12, 1)));
        courseList.add(new Course("MTH103", "Discrete Structures", 4, "Mon/Wed 10-12 AM", 180, 3, "Room B-003 ", Arrays.asList("MTH101, MTH102"),LocalDate.of(2024, 12, 1)));


        // For user input
        Scanner scanner = new Scanner(System.in);

        //This is the first menu for the User. You can sign up as a new student or a new professor. Since admin is defined and hardcoded, admin can only login.
        while (true) {
            System.out.println("Welcome to IIITD Registration System!\n1. Sign Up\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleSignUp(scanner, studentList, professorList,teachingAssistantList);
                    break;
                case 2:
                    handleLogin(scanner, studentList, professorList, admin, courseList, complaints,teachingAssistantList);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // This is for signing up. You can sign up as student or professor or Teaching Assistant and it asks for information accordingly.
    private static void handleSignUp(Scanner scanner, List<Student> studentList, List<Professor> professorList, List<TeachingAssistant> teachingAssistantList) {
        System.out.println("Sign Up as: \n1. Student \n2. Professor \n3. Teaching Assistant");
        int signUpChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (signUpChoice == 1) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter student ID: ");
            String studentID = scanner.nextLine();
            System.out.print("Enter semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine();

            Student student = User.signUpStudent(email, password, studentID, semester);
            studentList.add(student);
            System.out.println("Student signed up successfully.");
        } else if (signUpChoice == 2) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter professor ID: ");
            String professorID = scanner.nextLine();
            System.out.print("Enter office hours: ");
            String officeHours = scanner.nextLine();

            Professor professor = User.signUpProfessor(email, password, professorID, officeHours);
            professorList.add(professor);
            System.out.println("Professor signed up successfully.");
        }
        else if(signUpChoice == 3){
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.println(("Enter name: "));
            String name = scanner.nextLine();
            System.out.print("Enter semester: ");
            int semester = scanner.nextInt();
            scanner.nextLine();

            TeachingAssistant teachingAssistant = User.signUpTeachingAssistant(email,password,name,semester);
            teachingAssistantList.add(teachingAssistant);

            System.out.println("Teaching Assistant signed up successfully.");
            studentList.add(teachingAssistant);
            System.out.println("Teaching Assistant has been added to student list successfully");
        }
        else {
            System.out.println("Invalid option. Sign-up failed.");
        }
    }

    // Menu for the login process for studentList, professorList, and administrators
    private static void handleLogin(Scanner scanner, List<Student> studentList, List<Professor> professorList, Administrator admin, List<Course> courseList, List<Complaint> complaints, List<TeachingAssistant> teachingAssistantList) throws InvalidLoginException, CourseFullException, DropDeadlinePassedException {

        System.out.println("Login as: \n1. Student \n2. Professor \n3. Administrator \n4. Teaching Assistant");
        int loginChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        try {
            switch (loginChoice) {
                case 1:
                    handleStudentLogin(scanner, studentList, courseList, admin);
                    break;
                case 2:
                    handleProfessorLogin(scanner, professorList, courseList, teachingAssistantList);
                    break;
                case 3:
                    handleAdministratorLogin(scanner, admin, courseList, studentList, professorList, complaints);
                    break;
                    case 4:
                        handleTeachingAssistantLogin(scanner,teachingAssistantList,studentList,courseList, admin);
                        break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        catch(InvalidLoginException e) {
            throw new InvalidLoginException("InvalidLoginException Occured "+e.getMessage());
        }

    }

    private static void handleStudentLogin(Scanner scanner, List<Student> studentList, List<Course> courseList, Administrator admin) throws InvalidLoginException, CourseFullException, DropDeadlinePassedException {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Student student = findStudentByEmail(studentList, email);
        if (student != null && student.getPassword().equals(password)) {
            studentMenu(scanner, student, courseList, admin);
        } else {
            throw new InvalidLoginException("Invalid credentials. Please try again.");
            //System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void handleTeachingAssistantLogin(Scanner scanner, List<TeachingAssistant> teachingAssistantList, List<Student> studentList, List<Course> courseList, Administrator admin) throws InvalidLoginException, DropDeadlinePassedException, CourseFullException {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        TeachingAssistant teachingAssistant = findTAByEmail(teachingAssistantList,email);
        if (teachingAssistant != null && teachingAssistant.getPassword().equals(password)) {
            teachingAssistantMenu(scanner, teachingAssistant, courseList,studentList,admin);
        } else {
            throw new InvalidLoginException("Invalid credentials. Please try again.");
            //System.out.println("Invalid credentials. Please try again.");
        }

    }

    private static void handleProfessorLogin(Scanner scanner, List<Professor> professorList, List<Course> courseList, List<TeachingAssistant> teachingAssistantList) throws InvalidLoginException{
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Professor professor = findProfessorByEmail(professorList, email);
        if (professor != null && professor.getPassword().equals(password)) {
            professorMenu(scanner, professor, courseList,teachingAssistantList);
        } else {
            throw new InvalidLoginException("Invalid credentials. Please try again.");
        }
    }

    private static void handleAdministratorLogin(Scanner scanner, Administrator admin, List<Course> courseList, List<Student> studentList, List<Professor> professorList, List<Complaint> complaints) throws InvalidLoginException{
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (email.equals(admin.getEmail()) && password.equals(admin.getPassword())) {
            adminMenu(admin, courseList, studentList, professorList, complaints);
        } else {
            throw new InvalidLoginException("Invalid credentials. Please try again.");
        }
    }

    public static void studentMenu(Scanner scanner, Student student, List<Course> courseList, Administrator admin) throws CourseFullException, DropDeadlinePassedException {
        boolean loggedIn = true;
        student.login();
        while (loggedIn) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View Schedule");
            System.out.println("4. Track Academic Progress");
            System.out.println("5. Drop Course");
            System.out.println("6. Submit Complaint");
            System.out.println("7. View Complaints");
            System.out.println("8. Proceed to Next Semester");
            System.out.println("9. Submit Feedback");
            System.out.println("10. View Feedback");
            System.out.println("11. Logout");
            System.out.print("Select an option: ");
            int studentChoice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (studentChoice) {
                    case 1:
                        student.viewAvailableCourses(courseList);
                        break;
                    case 2:
                        System.out.print("Enter course code to register: ");
                        String courseCode = scanner.nextLine();
                        if (courseList != null) {
                            student.registerForCourse(courseList, courseCode);
                        } else {
                            System.out.println("Course not found.");
                        }
                        break;
                    case 3:
                        student.viewSchedule();
                        break;
                    case 4:
                        student.trackAcademicProgress();
                        break;
                    case 5:
                        student.dropCourse();
                        break;
                    case 6:
                        System.out.print("Enter complaint description: ");
                        String description = scanner.nextLine();
                        student.submitComplaint(description, admin);
                        break;
                    case 7:
                        student.viewComplaints();
                        break;

                    case 8:
                        if (student.canProceedToNextSemester()) {
                            System.out.println("Moved to next semester.");
                        }
                        break;
                    case 9:
                        submitFeedback(scanner, student, courseList);
                        break;
                    case 10:
                        student.viewFeedbackHistory();
                        break;
                    case 11:
                        student.logout();
                        loggedIn = false;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            catch(DropDeadlinePassedException e){
                throw  new DropDeadlinePassedException("DropDeadlinePassedException Occured "+e.getMessage());
            }
        }
    }

    public static void professorMenu(Scanner scanner, Professor professor, List<Course> courseList, List<TeachingAssistant> teachingAssistantList) {
        boolean loggedIn = true;
        professor.login();
        while (loggedIn) {
            System.out.println("\n--- Professor Menu ---");
            System.out.println("1. Manage Courses");
            System.out.println("2. View Enrolled Students");
            System.out.println("3. Assign TA to Courses");
            System.out.println("4. View Course Feedback");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter course code to update: ");
                    String courseCode = scanner.nextLine();
                    Course courseToUpdate = findCourseByCode(courseList, courseCode);

                    if (courseToUpdate != null && professor.getAssignedCourses().contains(courseToUpdate)) {
                        // Update schedule
                        System.out.print("Enter new schedule: ");
                        String newSchedule = scanner.nextLine();
                        professor.updateCourseSchedule(courseToUpdate, newSchedule);
                        // Update syllabus
                        System.out.print("Enter new syllabus: ");
                        String newSyllabus = scanner.nextLine();
                        professor.updateCourseSyllabus(courseToUpdate, newSyllabus);
                        // Update enrollment limit
                        System.out.print("Enter new enrollment limit: ");
                        int newLimit = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        professor.updateCourseEnrollmentLimit(courseToUpdate, newLimit);

                    } else {
                        System.out.println("You are not assigned to this course or the course does not exist.");
                    }
                    break;
                case 2:
                    System.out.print("Enter course code to view enrolled students: ");
                    String courseCodeToView = scanner.nextLine();
                    Course courseToView = findCourseByCode(courseList, courseCodeToView);
                    if (courseToView != null) {
                        professor.displayEnrolledStudents(courseToView);
                    }
                    break;

                case 3:
                    System.out.print("Enter course code to assign TA to: ");
                    String courseCodeToAssign = scanner.nextLine();
                    Course courseToAssign = findCourseByCode(courseList, courseCodeToAssign);
                    if (courseToAssign != null) {
                        professor.assignTAToCourses(teachingAssistantList, courseToAssign);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter course code to view feedback: ");
                    String courseCodeToFeedback = scanner.nextLine();
                    Course courseToFeedback = findCourseByCode(courseList, courseCodeToFeedback);
                    if (courseToFeedback != null && professor.getAssignedCourses().contains(courseToFeedback)) {
                        professor.viewCourseFeedback(courseToFeedback); // Call the feedback viewing method
                    } else {
                        System.out.println("You are not assigned to this course or the course does not exist.");
                    }
                    break;
                case 5:
                    professor.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void teachingAssistantMenu(Scanner scanner, TeachingAssistant teachingAssistant, List<Course> courseList, List<Student> studentList ,Administrator admin) throws CourseFullException, DropDeadlinePassedException {
        boolean loggedIn = true;
        teachingAssistant.login();
        while (loggedIn) {
            System.out.println("\n--- Teaching Assistant Menu ---");
            System.out.println("1. View Enrolled Students of my TA Course");
            System.out.println("2. View Grades of Enrolled Students");
            System.out.println("3. Update Grades of Enrolled Students");
            System.out.println("4. View Available Courses");
            System.out.println("5. Register for Course");
            System.out.println("6. View Schedule");
            System.out.println("7. Track Academic Progress");
            System.out.println("8. Drop Course");
            System.out.println("9. Submit Complaint");
            System.out.println("10. View Complaints");
            System.out.println("11. Proceed to Next Semester");
            System.out.println("12. Submit Feedback");
            System.out.println("13. View Feedback");
            System.out.println("14. Logout");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: System.out.print("Enter course code to view enrolled students: ");
                    String courseCodeToView = scanner.nextLine();
                    Course courseToView = findCourseByCode(courseList, courseCodeToView);
                    if (courseToView != null) {
                        teachingAssistant.displayEnrolledStudents(courseToView);
                    }
                    break;
                case 2:
                    System.out.println("Enter student email to view grades:");
                    String studentEmail = scanner.nextLine();
                    Student student = findStudentByEmail(studentList, studentEmail);
                    if (student != null) {
                        teachingAssistant.viewGrades(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    //manageGrades(scanner,teachingAssistant,courseList);
                    break;
                case 3:
                    System.out.println("Enter student email to update grades:");
                    String studentEmailId = scanner.nextLine();
                    Student studentName = findStudentByEmail(studentList, studentEmailId);
                    if (studentName != null) {
                        teachingAssistant.updateGrades(studentName);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    teachingAssistant.viewAvailableCourses(courseList);
                    break;
                case 5:
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.nextLine();
                    if (courseList != null) {
                        teachingAssistant.registerForCourse(courseList, courseCode);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 6:
                    teachingAssistant.viewSchedule();
                    break;
                case 7:
                    teachingAssistant.trackAcademicProgress();
                    break;
                case 8:
                    teachingAssistant.dropCourse();
                    break;
                case 9:
                    System.out.print("Enter complaint description: ");
                    String description = scanner.nextLine();
                    teachingAssistant.submitComplaint(description, admin);
                    break;
                case 10:
                    teachingAssistant.viewComplaints();
                    break;

                case 11:
                    if (teachingAssistant.canProceedToNextSemester()) {
                        System.out.println("Moved to next semester.");
                    }
                    break;
                case 12:
                    submitFeedback(scanner, teachingAssistant, courseList);
                    break;
                case 13:
                    teachingAssistant.viewFeedbackHistory();
                    break;
                case 14:
                    teachingAssistant.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");

            }
        }
    }


    public static void adminMenu(Administrator admin, List<Course> courseList, List<Student> studentList, List<Professor> professorList, List<Complaint> complaints) {
        Scanner scanner = new Scanner(System.in);
        admin.login();
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professors to Courses");
            System.out.println("4. Handle Complaints");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();


            switch (adminChoice) {
                case 1:
                    admin.manageCourseCatalog(courseList, true);
                    System.out.println("1. Add Course");
                    System.out.println("2. Remove Course");
                    int catalogChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (catalogChoice == 1) {
                        System.out.print("Enter new course code: ");
                        String newCourseCode = scanner.nextLine();
                        System.out.print("Enter course title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter credits: ");
                        int credits = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter schedule: ");
                        String schedule = scanner.nextLine();
                        System.out.print("Enter enrollment limit: ");
                        int limit = scanner.nextInt();
                        System.out.print("Enter semester: ");
                        int semester = scanner.nextInt();
                        System.out.print("Enter location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter prerequisites (comma-separated course codes, or 'none' if no prerequisites): ");
                        String prerequisitesInput = scanner.nextLine();
                        List<String> prerequisites = new ArrayList<>();
                        if (!prerequisitesInput.equalsIgnoreCase("none")) {
                            prerequisites = Arrays.asList(prerequisitesInput.split("\\\\s*,\\\\s*"));
                        }
                        scanner.nextLine();
                        System.out.println("Enter drop deadline (YYYY-MM-DD): ");
                        String input = scanner.nextLine();
                        LocalDate DropDateDeadline = LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
                        Course newCourse = new Course(newCourseCode, title, credits, schedule, limit, semester, location, prerequisites,DropDateDeadline);
                        courseList.add(newCourse);
                        admin.manageCourseCatalog(courseList, true);
                    } else if (catalogChoice == 2) {
                        System.out.print("Enter course code to remove: ");
                        String courseCodeToRemove = scanner.nextLine();
                        Course courseToRemove = findCourseByCode(courseList, courseCodeToRemove);
                        if (courseToRemove != null) {
                            courseList.remove(courseToRemove);
                            admin.manageCourseCatalog(courseList, false);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;
                case 2:
                    System.out.println("Enter student email to manage records:");
                    String studentEmail = scanner.nextLine();
                    Student student = findStudentByEmail(studentList, studentEmail);
                    if (student != null) {
                        admin.manageStudentRecords(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter course code to assign professor to: ");
                    String courseCodeToAssign = scanner.nextLine();
                    Course courseToAssign = findCourseByCode(courseList, courseCodeToAssign);
                    if (courseToAssign != null) {
                        admin.assignProfessorsToCourses(professorList, courseToAssign);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;

                case 4:
                    admin.handleComplaints();
                    break;
                case 5:
                    admin.logout();
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper functions
    public static Student findStudentByEmail(List<Student> studentList, String email) {
        for (Student student : studentList) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;
    }

    private static void submitFeedback(Scanner scanner, Student student, List<Course> courseList) {
        System.out.println("Enter course code for feedback:");
        String courseCode = scanner.nextLine();
        Course course = findCourseByCode(courseList, courseCode); // Method to find course by code
        Map<Course, String> grades = student.getGrades();
        if (course != null) {
            String gradesOfStudent = grades.get(course);

            if(gradesOfStudent != null) {
                System.out.println("Enter your feedback:");
                String feedbackContent = scanner.nextLine(); // Get feedback content from user
                Feedback<String> feedback = new Feedback<>(feedbackContent); // Create a Feedback instance
                student.submitFeedback(course, feedback);
            }
            else{
                System.out.println("No grade has been found for "+ student.getEmail() + "for course " + courseCode );
                System.out.println("So, feedback cannot be submitted");
            }
        }
    }

    public static Professor findProfessorByEmail(List<Professor> professorList, String email) {
        for (Professor professor : professorList) {
            if (professor.getEmail().equalsIgnoreCase(email)) {
                return professor;
            }
        }
        return null;
    }

    public static TeachingAssistant findTAByEmail(List<TeachingAssistant> teachingAssistantList, String email) {
        for (TeachingAssistant teachingAssistant : teachingAssistantList) {
            if (teachingAssistant.getEmail().equalsIgnoreCase(email)) {
                return teachingAssistant;
            }
        }
        return null;
    }

    private static Course findCourseByCode(List<Course> courseList, String code) {
        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }
}
