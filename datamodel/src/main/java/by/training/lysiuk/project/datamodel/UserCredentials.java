package by.training.lysiuk.project.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserCredentials extends AbstractModel {

	@Column(updatable = false)
	private String email;
	
	@Column
	private String password;

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

	@Override
	public String toString() {
		return "UserCredentials [email=" + email + ", password=" + password + "]";
	}

}
