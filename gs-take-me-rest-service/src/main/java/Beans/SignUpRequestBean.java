package Beans;

public class SignUpRequestBean 
{
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String password;
	
	private String facebookToken;
	String registrationDeviceKey;

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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
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
