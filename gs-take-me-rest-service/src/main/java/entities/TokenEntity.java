package entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="token")
public class TokenEntity extends BaseEntity<Long>
{
	
	String token;
	
	@OneToOne
	@JoinColumn(name="userId")
	UserEntity userEntity;
	
	public TokenEntity() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
