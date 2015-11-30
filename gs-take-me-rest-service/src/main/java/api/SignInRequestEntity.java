package api;

public class SignInRequestEntity 
{
	String email;
	String password;
	String registrationDeviceKey;
	
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
	public String getRegistrationDeviceKey() {
		return registrationDeviceKey;
	}
	public void setRegistrationDeviceKey(String registrationDeviceKey) {
		this.registrationDeviceKey = registrationDeviceKey;
	}
}
