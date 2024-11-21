# IIITD Course Registration System (Enhanced Version)

## Introduction
This project is an enhanced version of the IIITD Course Registration System made previously. It introduces new features such as generic feedback handling, extended user role management via inheritance, and robust exception handling. These enhancements are implemented while maintaining the core functionality of the original system.

## Features

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