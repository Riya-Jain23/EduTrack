package Other;
import Student.Student;
import Professor.Professor;
import TeachingAssistant.TeachingAssistant;

public abstract class User implements UserInterface {
    private String email;
    private String password;

    public User(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    @Override
    public abstract void login();
    @Override
    public abstract void logout();

    public static Student signUpStudent(String email, String password, String studentID, int year) {
        return new Student(email, password, studentID, year);
    }

    public static Professor signUpProfessor(String email, String password, String professorID, String schedule) {
        return new Professor(email, password, professorID, schedule);
    }

    public static TeachingAssistant signUpTeachingAssistant(String email, String password, String name, int semester){
        return new TeachingAssistant(email, password, name, semester);
    }
}

