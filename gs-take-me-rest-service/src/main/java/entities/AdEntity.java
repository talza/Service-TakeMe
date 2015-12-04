package entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Ads")
public class AdEntity extends BaseEntity<Long> {
	
	@OneToOne (cascade={CascadeType.ALL})
	@JoinColumn(name="pet_id")
	PetEntity petEntity;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	UserEntity userEntity;
	
	Date publishedAt;
	Date updatedAt;


	public PetEntity getPetEntity() {
		return petEntity;
	}


	public void setPetEntity(PetEntity petEntity) {
		this.petEntity = petEntity;
	}


	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	public Date getPublishedAt() {
		return publishedAt;
	}


	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
