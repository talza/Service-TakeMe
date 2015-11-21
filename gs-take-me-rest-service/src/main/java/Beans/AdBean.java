package Beans;

import entities.AdEntity;
import entities.PetEntity;
import entities.UserEntity;

public class AdBean {
	
	private Long id;
	private String petName;
	private Integer petGender;
	private Integer petAge;
	private Integer petSize;
	private Integer petType;
	private String petPhotoUrl;
	private String petDescription;
	private boolean isInWishlist;
	private OwnerBean petOwner;

	public AdBean() {
	}
	
	public AdBean(AdEntity adEntity) {
		if (adEntity != null){
			this.id = adEntity.getId();
			
			PetEntity petEntity = adEntity.getPetEntity();
			
			if (petEntity != null){
				this.petName = petEntity.getName();
				this.petAge = petEntity.getAge();
				this.petGender = petEntity.getGender();
				this.petSize = petEntity.getSize();
				this.petType = petEntity.getType();
				this.petPhotoUrl = petEntity.getPhotoUrl();
				this.petDescription = petEntity.getDescription();
			}
			
			UserEntity userEntity = adEntity.getUserEntity();
			
			if (userEntity != null){
				OwnerBean owner = new OwnerBean();
				owner.setOwnerId(userEntity.getId());
				owner.setOwnerFirstName(userEntity.getFirstName());
				owner.setOwnerLastName(userEntity.getLastName());
				owner.setOwnerEmail(userEntity.getEmail());
				owner.setOwnerPhone(userEntity.getPhoneNumber());
				
				this.petOwner = owner;
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getPetGender() {
		return petGender;
	}

	public void setPetGender(Integer petGender) {
		this.petGender = petGender;
	}

	public Integer getPetAge() {
		return petAge;
	}

	public void setPetAge(Integer petAge) {
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

	public boolean isInWishlist() {
		return isInWishlist;
	}

	public void setIsInWishlist(boolean isInWishlist) {
		this.isInWishlist = isInWishlist;
	}

	public OwnerBean getPetOwner() {
		return petOwner;
	}

	public void setPetOwner(OwnerBean petOwner) {
		this.petOwner = petOwner;
	}
	
	

}
