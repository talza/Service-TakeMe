package resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.SignInRequestEntity;
import entities.ExampleEntity;
import entities.UserEntity;
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
	public UserEntity signIn(@RequestBody SignInRequestEntity signInRequestEntity) 
	{	
    	UserEntity user = userRepository.findByEmail(signInRequestEntity.getEmail());
    	
    	if (user.getPassword().equals(signInRequestEntity.getPassword())) {
    		return user;
    	} else {
    		return new UserEntity("Not found");
    	}
    		
    //	return userRepository.findOne(id);
//    	return null;
	}	    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public ExampleEntity create(@RequestBody ExampleEntity entity) 
    {	
     //   return userRepository.save(entity);
    	return null;
    }	    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public ExampleEntity update(@RequestBody ExampleEntity entity) 
    {	
   //     return userRepository.save(entity);
    	return null;
    }	 
    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.DELETE, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public void delete(@RequestBody ExampleEntity entity) 
    {	
        //userRepository.delete(entity);
    }	        
	    
 }
