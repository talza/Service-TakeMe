package api;

public class CreateAdRequestEntity {
	
	private String petName;
	private Character petGender;
	private Float petAge;
	private Integer petSize;
	private Integer petType;
	private String petPhotoUrl;
	private String petDescription;
	
	
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public Character getPetGender() {
		return petGender;
	}
	public void setPetGender(Character petGender) {
		this.petGender = petGender;
	}
	public Float getPetAge() {
		return petAge;
	}
	public void setPetAge(Float petAge) {
		this.petAge = petAge;
	}
	public Integer getPetSize() {
		return petSize;
	}
	public void setPetSize(Integer petSize) {
		this.petSize = petSize;
	}
	public Integer getPetType() {
		return petType;
	}
	public void setPetType(Integer petType) {
		this.petType = petType;
	}
	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}
	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
	}
	public String getPetDescription() {
		return petDescription;
	}
	public void setPetDescription(String petDescription) {
		this.petDescription = petDescription;
	}

}
