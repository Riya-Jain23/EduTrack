package Other;

import Student.Student;
import Administrator.Administrator;
import java.util.UUID;

public class Complaint {
    private String complaintID;
    private String description;
    private String status;
    private Student student;
    private Administrator admin;
    private String resolutionDescription;

    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_RESOLVED = "Resolved";

    // Constructor
    public Complaint(String description, Student student) {
        this.complaintID = generateComplaintID();
        this.description = description;
        this.student = student;
        this.status = STATUS_PENDING;
        this.resolutionDescription="";
    }

    // Method to generate a unique complaint ID (UUID)
    private String generateComplaintID() {
        return UUID.randomUUID().toString();
    }
    public String getComplaintID() {
        return complaintID;
    }
    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            System.out.println("Description cannot be null or empty.");
        }
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Administrator getAdmin() {
        return admin;
    }
    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
    public String getStatus() {
        return status;
    }
    public String getResolutionDescription() {
        return resolutionDescription;
    }
    public void setResolutionDescription(String resolutionDescription) {
        this.resolutionDescription = resolutionDescription;
    }

    // Mark complaint as resolved
    public void resolve(String resolutionDescription) {
        if (!STATUS_RESOLVED.equals(status)) {
            this.status = STATUS_RESOLVED;
            this.resolutionDescription=resolutionDescription;
            System.out.println("Complaint " + complaintID + " resolved with resolution: "+resolutionDescription);
        } else {
            System.out.println("Complaint " + complaintID + " is already resolved.");
        }
    }
}

