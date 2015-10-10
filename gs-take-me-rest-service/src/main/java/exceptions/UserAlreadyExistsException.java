package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="A user with this email already exsists")
public class UserAlreadyExistsException extends Exception {

	public UserAlreadyExistsException() {
	}
}
