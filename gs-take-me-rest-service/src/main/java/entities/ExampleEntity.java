package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Example")
public class ExampleEntity extends BaseEntity<Long>
{
	
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
