package entities;

public class ResponseEntity {
	
	private Long token;
	
	public ResponseEntity(Long token) {
		this.token = token;
	}

	public Long getId() {
		return token;
	}

	public void setId(Long token) {
		this.token = token;
	}

	
	
	

}
