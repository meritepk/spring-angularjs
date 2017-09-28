package pk.merite.webapp.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class UserInfo {

    @Id
    private String id;

    private String userName;
    private String password;
    private String roles;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLogggedIn;

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

    public Date getLastLogggedIn() {
        return lastLogggedIn;
    }

    public void setLastLogggedIn(Date lastLogggedIn) {
        this.lastLogggedIn = lastLogggedIn;
    }

}
