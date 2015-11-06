package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Ad not found")
public class AdNotFoundException extends Exception {

	public AdNotFoundException() {
	}

}
