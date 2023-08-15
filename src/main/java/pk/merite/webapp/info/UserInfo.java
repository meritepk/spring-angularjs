package pk.merite.webapp.info;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserInfo {

    @Id
    private String id;
    private String userName;
    private String password;
    private String roles;
    private LocalDateTime lastLogggedIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public LocalDateTime getLastLogggedIn() {
        return lastLogggedIn;
    }

    public void setLastLogggedIn(LocalDateTime lastLogggedIn) {
        this.lastLogggedIn = lastLogggedIn;
    }
}
