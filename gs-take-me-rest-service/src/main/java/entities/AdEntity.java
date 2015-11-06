package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Ads")
public class AdEntity extends BaseEntity<Long> {
	
	@OneToOne (cascade={CascadeType.ALL})
	@JoinColumn(name="pet_id")
	PetEntity petEntity;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	UserEntity userEntity;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="wish_list",
		      joinColumns={@JoinColumn(name="ad_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})
	List<UserEntity> wishedByList;
	
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
