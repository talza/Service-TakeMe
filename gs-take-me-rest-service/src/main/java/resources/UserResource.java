package resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.SignInRequestEntity;
import api.SignUpRequestEntity;
import api.UpdateUserRequestEntity;
import entities.ExampleEntity;
import entities.UserEntity;
import exceptions.UserAlreadyExistsException;
import exceptions.UserUnauthorizedException;
import repositories.UserRepository;


@RestController
@RequestMapping(value="/user")
public class UserResource
{
	
	@Autowired
	UserRepository userRepository;
		
    @RequestMapping(value="/signIn", 
	        method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public long signIn(@RequestBody SignInRequestEntity signInRequestEntity) throws UserUnauthorizedException 
	{	
    	UserEntity user = userRepository.findByEmail(signInRequestEntity.getEmail());
    	
    	if (user.getPassword().equals(signInRequestEntity.getPassword())) {
    		return user.getId();
    	} else {
    		throw new UserUnauthorizedException();
    	}
    		
    //	return userRepository.findOne(id);
	}	    
    
    @RequestMapping(value="/signUp", 
	        method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public long signUp(@RequestBody SignUpRequestEntity entity) throws UserAlreadyExistsException 
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
    		return userRepository.save(user).getId();
    	}
    }	    
    
    @RequestMapping(value="/Update/{id}", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public long update(@PathVariable("id") long id, @RequestBody UpdateUserRequestEntity entity) 
    {	
    	UserEntity user = userRepository.findOne(id);
    	user.setFirstName(entity.getFirstName());
    	user.setLastName(entity.getLastName());
    	user.setPassword(entity.getPassword());
    	user.setPhoneNumber(entity.getPhoneNumber());
        return userRepository.save(user).getId();
    }	 
    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.DELETE, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public void delete(@RequestBody UserEntity entity) 
    {	
//        userRepository.delete(entity);
        userRepository.delete(entity.getId());
    }	        
	    
 }
