package entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Ads")
public class AdEntity extends BaseEntity<Long> {
	
	@OneToOne
	@JoinColumn(name="pet_id")
	PetEntity petEntity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	UserEntity userEntity;
	
	Date publishedAt;
	Date updatedAt;
	

	public AdEntity() {
		// TODO Auto-generated constructor stub
	}


	public PetEntity getPetEntity() {
		return petEntity;
	}


	public void setPetId(PetEntity petEntity) {
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
