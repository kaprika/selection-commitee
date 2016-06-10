package by.training.lysiuk.project.datamodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class UserProfile extends AbstractModel {

    // TODO improve/fix if possible
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(nullable = false, updatable = false, name = "id")
    private UserCredentials credentials;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Date created;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

}
