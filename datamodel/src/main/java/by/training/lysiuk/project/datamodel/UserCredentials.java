package by.training.lysiuk.project.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class UserCredentials extends AbstractModel {

    @Column(updatable = false)
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(value = EnumType.ORDINAL)
    private UserRole role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserCredentials [email=" + email + ", password=" + password + ", role=" + role + "]";
    }

}
