# EduTrack

## Project Overview
The EduTrack Registration System is a Java-based application that is designed to manage course registration, student records, professor assignments, and administrative tasks for a university setting. The system allows students to register for courses, professors to manage their courses, and administrators to oversee and manage the entire system. It is a command line interface and it utilises popular OOPs concepts. It introduces new features such as generic feedback handling, extended user role management via inheritance, and robust exception handling.

## Files Overview
### Main.java
The main entry point of the application. This file initializes the system, including sample data for students, professors, and courses. It provides the user interface for signing up, logging in, and navigating through the application’s features based on user roles (Student, Professor, Administrator).

### Administrator.java
Defines the Administrator class, which extends the User class. Administrators can manage the course catalog, student records, and professor assignments. They can also handle complaints and oversee various administrative tasks.

### Student.java
Defines the Student class, which extends the User class. Students can sign up for courses, view their schedules, track academic progress, drop courses, and manage complaints. The class includes methods for interacting with the course list and performing various student-related actions.

### Professor.java
Defines the Professor class, which extends the User class. Professors can be assigned to courses, manage course details (e.g., schedule, syllabus, enrollment limits), and view enrolled students. The class includes methods for updating course information and interacting with the courses they are assigned to.

### User.java
Defines the User class, which serves as a base class for Student and Professor. It includes basic user properties such as email and password, along with methods for login and logout.

### Course.java
Defines the Course class, representing a university course. It includes properties such as course code, title, credits, schedule, enrollment limit, semester, and location. The class also manages course prerequisites and enrolled students.

### Complaint.java
Defines the Complaint class, which represents a student complaint. It includes properties for the complaint description and status, and methods for managing complaints.

### UserInterface.java
Defines an interface that includes login and logout methods, which are implemented by the User class and its subclasses (Student, Professor, and Administrator).

## Additional Features

### 1. Generic Feedback System
- **Description**: Students can provide feedback on courses they have completed. Feedback can be either numeric ratings (1–5) or textual comments. They can also view their submitted feedback.
- **Implementation**:
   - A generic class `Feedback<T>` is used to handle feedback, supporting different data types for feedback entries.
   - Professors can view the feedback for their courses.

### 2. Enhanced User Role Management
- **New Role: Teaching Assistant (TA)**:
   - Teaching Assistants (TAs) have all the capabilities of students, with added permissions to view and manage student grades. They cannot assign a new grade to a student as that is done by the admin, but they can update a pre-assigned grade.
   - The `TeachingAssistant` class extends the `Student` class, inheriting all its functionalities and adding grading-related methods.
   - TAs cannot modify course details, unlike professors.

### 3. Robust Exception Handling
- **Custom Exceptions implemented in my code**:
   - `CourseFullException`: Thrown when a student tries to register for a course that is already full.
   - `InvalidLoginException`: Thrown when incorrect login credentials are provided. Applies for all (Student, Professor, Admin, Teacher Assistant)
   - `DropDeadlinePassedException`: Thrown when a student tries to drop a course after the allowed deadline. For this we will consider the current date of performing drop and match it with the defined date by admin.
- **Usage**: Exception handling is implemented using `try-catch` blocks, ensuring that the system properly handles errors without crashing.


## How to Run
1. Compile and run the `Main.java` file.
2. Log in using the credentials for a Student, Professor, TA, or Administrator.
3. Use the menu to register for courses, submit feedback, manage student grades, etc.

### NOTE
1. Feedback can only be submitted for courses that have been completed by the student.
2. CS101 course has an enrollment limit of 2 so that it can verify the functioning of CourseFullException during demonstration.
3. Only one TA has been hardcoded currently. More can be added via sign up and login.

## Enhancements Overview
- **Generic Programming**: Implemented in the `Feedback<T>` class to allow multiple types of feedback.
- **Inheritance**: The `TeachingAssistant` class extends the `Student` class, adding new functionalities specific to TAs.
- **Exception Handling**: Custom exceptions (`CourseFullException`, `InvalidLoginException`, `DropDeadlinePassedException`) manage edge cases for course registration, login, and course drop failures.

## Requirements
- **JDK 8 or higher** is required to compile and run the program.

## Made By
Riya Jain, (2023441), BTech CSD 2027
