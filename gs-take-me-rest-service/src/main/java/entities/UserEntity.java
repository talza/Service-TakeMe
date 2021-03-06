package entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class UserEntity extends BaseEntity<Long>
{
	
	String phoneNumber;
	String firstName;
	String lastName;
	String password;
	String email;
	
	String facebookToken;
	String registrationDeviceKey;
	
	@OneToMany(mappedBy="userEntity", fetch=FetchType.LAZY)
	Set<AdEntity> ads;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="wish_list",
		      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="ad_id", referencedColumnName="id")})
	List<AdEntity> wishList;
	
	
	public UserEntity() {}
	
	public UserEntity(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
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
	public String getFacebookToken() {
		return facebookToken;
	}
	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	public String getRegistrationDeviceKey() {
		return registrationDeviceKey;
	}

	public void setRegistrationDeviceKey(String registrationDeviceKey) {
		this.registrationDeviceKey = registrationDeviceKey;
	}
}
