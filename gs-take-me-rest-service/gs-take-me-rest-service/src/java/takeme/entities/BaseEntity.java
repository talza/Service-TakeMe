package entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class BaseEntity<ID> 
{
	
	@Id 
	@GeneratedValue
	@Column(name="ID")	
	ID id; 
	
	public BaseEntity()
	{
		
	}	
	
	public ID getId()
	{
		return id;
	}

	public void setId(ID id)
	{
		this.id = id;
	}	
	
}
