package Beans;

public class TokenResponseBean {
	
	private Long token;
	
	public TokenResponseBean(Long token) {
		this.token = token;
	}

	public Long getId() {
		return token;
	}

	public void setId(Long token) {
		this.token = token;
	}
}
