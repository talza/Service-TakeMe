package entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Pets")
public class PetEntity extends BaseEntity<Long> {
	
	String name;
	Integer age;
	Integer gender;
	String photoUrl;
	String description;
	Integer size;
	Integer type;
	
	@JsonIgnore
	@OneToOne (mappedBy="petEntity")
	AdEntity adEntity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	public AdEntity getAdEntity() {
		return adEntity;
	}

	public void setAdEntity(AdEntity adEntity) {
		this.adEntity = adEntity;
	}
	
	

}
