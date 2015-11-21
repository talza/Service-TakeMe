package resources;

import api.AddAdToWishlistRequestEntity;
import entities.AdEntity;
import entities.WishlistEntity;
import exceptions.AdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import Beans.TokenResponseBean;
import api.SignInRequestEntity;
import api.SignUpRequestEntity;
import api.UpdateUserRequestEntity;
import entities.UserEntity;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import exceptions.UserUnauthorizedException;
import repositories.AdRepository;
import repositories.UserRepository;
import repositories.WishlistRepository;

import java.util.HashMap;
import java.util.Map;


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
	public TokenResponseBean signIn(@RequestBody SignInRequestEntity signInRequestEntity) throws UserUnauthorizedException
	{
    	UserEntity user = userRepository.findByEmail(signInRequestEntity.getEmail());

    	if (user == null || !user.getPassword().equals(signInRequestEntity.getPassword())) {
    		throw new UserUnauthorizedException();
    	} else {
    		return new TokenResponseBean(user.getId());
    	}
	}

    @RequestMapping(value="/signUp",
	        method = RequestMethod.POST,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean signUp(@RequestBody SignUpRequestEntity entity) throws UserAlreadyExistsException
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
    		return new TokenResponseBean((userRepository.save(user)).getId());
    	}
    }
    
    @RequestMapping(value="/signViaFacebook",
	        method = RequestMethod.POST,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public TokenResponseBean signViaFacebook(@RequestBody SignUpRequestEntity entity) throws UserAlreadyExistsException
    {
    	UserEntity user = userRepository.findByEmail(entity.getEmail());

    	if (user != null){
    		// update facebook token
    		user.setFacebookToken(entity.getFacebookToken());
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
    public TokenResponseBean update(@PathVariable("id") Long id, @RequestBody UpdateUserRequestEntity entity)
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

    @RequestMapping(value="/Delete/{id}",
	        method = RequestMethod.DELETE,
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public void delete(@PathVariable("id") Long id)
    {
//        userRepository.delete(entity);
        userRepository.delete(id);
    }
    
	@RequestMapping(value="/{user_id}/wishlist",
			method = RequestMethod.POST,
			produces = "application/json;charset=utf-8",
			consumes="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> addAdToWishlist(@PathVariable("user_id") Long userId, @RequestBody AddAdToWishlistRequestEntity requestEntity) throws UserNotFoundException, AdNotFoundException {
		Map<String, Object> response = new HashMap<>();

		UserEntity user = userRepository.findOne(userId);
		if (user == null){
			throw new UserNotFoundException();
		}

		AdEntity ad = adRepository.findOne(requestEntity.getAdId());
		if (ad == null) {
			throw new AdNotFoundException();
		}

		WishlistEntity wishlistEntity = new WishlistEntity();
		wishlistEntity.setUserId(user.getId());
		wishlistEntity.setAdId(ad.getId());

		if (wishlistRepository.save(wishlistEntity) != null) {
			response.put("success", "Ad was added successfully to your wishlist.");
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("message", "Unable to add the ad to your wishlist.");
			response.put("error", error);
		}

		return response;
	}

	@RequestMapping(value="/{user_id}/wishlist/{ad_id}",
			method = RequestMethod.DELETE,
			produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> removeAdFromWishlist(@PathVariable("user_id") Long userId, @PathVariable("ad_id") Long adId) throws UserNotFoundException, AdNotFoundException {
		Map<String, Object> response = new HashMap<>();

		UserEntity user = userRepository.findOne(userId);
		if (user == null){
			throw new UserNotFoundException();
		}

		WishlistEntity wishlistEntity = wishlistRepository.findByUserIdAndAdId(userId, adId);
		if (wishlistEntity != null) {
			wishlistRepository.delete(wishlistEntity);
			response.put("success", "Ad was removed successfully from your wishlist.");
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("message", "Unable to remove the ad from your wishlist.");
			response.put("error", error);
		}

		return response;
	}
 }
