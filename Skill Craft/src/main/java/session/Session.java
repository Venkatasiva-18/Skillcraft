package session;
import java.sql.Timestamp;

public class Session {
    private int id;
    private String name;
    private String email;
    private String subject;
    private String link;
    private Timestamp createdAt;

    // Getters and setters for each field
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}

