package resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Beans.SignInRequestBean;
import Beans.SignUpRequestBean;
import Beans.TokenResponseBean;
import Beans.UpdateUserRequestBean;
import entities.UserEntity;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import exceptions.UserUnauthorizedException;
import repositories.AdRepository;
import repositories.UserRepository;
import repositories.WishlistRepository;


@RestController
@RequestMapping(value="/user")
public class UserResource
{

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdRepository adRepository;

	@Autowired
	WishlistRepository wishlistRepository;

    @RequestMapping(value="/signIn",
	        method = RequestMethod.POST,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public TokenResponseBean signIn(@RequestBody SignInRequestBean signInRequestEntity) throws UserUnauthorizedException
	{
    	UserEntity user = userRepository.findByEmail(signInRequestEntity.getEmail());

    	if (user == null || !user.getPassword().equals(signInRequestEntity.getPassword())) {
    		throw new UserUnauthorizedException();
    	} else {
    		// register user's device
    		user.setRegistrationDeviceKey(signInRequestEntity.getRegistrationDeviceKey());
    		userRepository.save(user);
    		return new TokenResponseBean(user.getId());
    	}
	}

    @RequestMapping(value="/signUp",
	        method = RequestMethod.POST,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean signUp(@RequestBody SignUpRequestBean entity) throws UserAlreadyExistsException
    {
    	UserEntity user = userRepository.findByEmail(entity.getEmail());

    	if (user != null){
    		throw new UserAlreadyExistsException();
    	} else {
    		user = new UserEntity();
    		user.setFirstName(entity.getFirstName());
    		user.setLastName(entity.getLastName());
    		user.setEmail(entity.getEmail());
    		user.setPhoneNumber(entity.getPhoneNumber());
    		user.setPassword(entity.getPassword());
    		user.setFacebookToken(entity.getFacebookToken());
    		user.setRegistrationDeviceKey(entity.getRegistrationDeviceKey());
    		return new TokenResponseBean((userRepository.save(user)).getId());
    	}
    }
    
    @RequestMapping(value="/signViaFacebook",
	        method = RequestMethod.POST,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean signViaFacebook(@RequestBody SignUpRequestBean entity) throws UserAlreadyExistsException
    {
    	UserEntity user = userRepository.findByEmail(entity.getEmail());

    	if (user != null){
    		// update facebook token
    		user.setFacebookToken(entity.getFacebookToken());
    		user.setRegistrationDeviceKey(entity.getRegistrationDeviceKey());
    		userRepository.save(user);
    		return new TokenResponseBean(user.getId());
    	} else {
    		return signUp(entity);
    	}
    }

    @RequestMapping(value="/{id}",
	        method = RequestMethod.PUT,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean update(@PathVariable("id") Long id, @RequestBody UpdateUserRequestBean entity)
    {
    	UserEntity user = userRepository.findOne(id);
    	user.setFirstName(entity.getFirstName());
    	user.setLastName(entity.getLastName());
    	user.setPhoneNumber(entity.getPhoneNumber());
        return new TokenResponseBean(userRepository.save(user).getId());
    }

    @RequestMapping(value="/{token}",
	        method = RequestMethod.GET,
	        produces = "application/json;charset=utf-8")
    @ResponseBody
    public UserEntity getUser(@PathVariable("token") Long token) throws UserNotFoundException
    {
    	UserEntity user = userRepository.findOne(token);

    	if (user != null){
    		return user;
    	} else {
    		throw new UserNotFoundException();
    	}
    }

    @RequestMapping(value="",
	        method = RequestMethod.GET,
	        produces = "application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean getUserByFacebook(@RequestParam(value ="facebookToken", required=true)String facebookToken) throws UserNotFoundException
    {
    	UserEntity user = userRepository.findByFacebookToken(facebookToken);

    	if (user != null){
    		return new TokenResponseBean(user.getId());
    	} else {
    		throw new UserNotFoundException();
    	}
    }
 }
