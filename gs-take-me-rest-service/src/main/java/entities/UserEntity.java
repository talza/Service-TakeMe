package entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class UserEntity extends BaseEntity<Long>
{
	
	String phoneNumber;
	String fullName;
	String password;
	String email;
	
	@OneToOne(mappedBy="userEntity")
	TokenEntity tokenEntity;
	
	public UserEntity() {}
	
	public UserEntity(String fullName) {
		super();
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
