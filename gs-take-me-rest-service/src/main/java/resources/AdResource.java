package resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Beans.AdBean;
import Beans.ResponseBean;
import api.CreateAdRequestEntity;
import api.UpdateAdRequestEntity;
import entities.AdEntity;
import entities.PetEntity;
import entities.UserEntity;
import entities.WishlistEntity;
import exceptions.AdNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.UserUnauthorizedException;
import repositories.AdRepository;
import repositories.PetRepository;
import repositories.UserRepository;
import repositories.WishlistRepository;

@RestController
@RequestMapping(value="/ad")
public class AdResource {
	
	@Autowired
	AdRepository adRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PetRepository petRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@RequestMapping(method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ResponseBean createAdd(@RequestBody CreateAdRequestEntity request,
			@RequestParam(value ="userId", required=true) Long userId) throws UserNotFoundException 
	{	
    	UserEntity user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		throw new UserNotFoundException();
    	}
    	
		// create pet
		PetEntity pet = new PetEntity();
		pet.setAge(request.getPetAge());
		pet.setDescription(request.getPetDescription());
		pet.setGender(request.getPetGender());
		pet.setName(request.getPetName());
		pet.setPhotoUrl(request.getPetPhotoUrl());
		pet.setSize(request.getPetSize());
		pet.setType(request.getPetType());
		
		// create ad
		Date now = new Date();
		AdEntity ad = new AdEntity();
		ad.setPublishedAt(now);
		ad.setUpdatedAt(now);
		ad.setUserEntity(user);
		
		// bind ad and pet
		ad.setPetEntity(pet);
		
		//save data
		petRepository.save(pet);
		adRepository.save(ad);
		
		return new ResponseBean("ad added successfuly", true);
	}
	
	@RequestMapping(value="/{id}", 
	        method = RequestMethod.GET, 
	        produces = "application/json;charset=utf-8")
	@ResponseBody
	public AdBean getAd(@PathVariable("id") Long id, 
			@RequestParam(value ="userId", required=true) Long userId) throws UserNotFoundException, AdNotFoundException, UserUnauthorizedException 
	{	
    	UserEntity user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		throw new UserNotFoundException();
    	}
    	
		// get the ad
		AdEntity ad = adRepository.findOne(id);
		
		if (ad == null){
			throw new AdNotFoundException();
		}
		
		// check validation
		if (ad.getUserEntity() == null || !userId.equals(ad.getUserEntity().getId())){
			throw new UserUnauthorizedException();
		}
		
		return new AdBean(ad);
	}
		
	@RequestMapping(value="/{id}", 
	        method = RequestMethod.DELETE, 
	        produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseBean deleteAd(@PathVariable("id") Long id, 
			@RequestParam(value ="userId", required=true) Long userId) throws UserNotFoundException, AdNotFoundException, UserUnauthorizedException 
	{	
    	UserEntity user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		throw new UserNotFoundException();
    	}
    	
		// get the ad
		AdEntity ad = adRepository.findOne(id);
		
		if (ad == null){
			throw new AdNotFoundException();
		}
		
		// check validation
		if (ad.getUserEntity() == null || !userId.equals(ad.getUserEntity().getId())){
			throw new UserUnauthorizedException();
		}
		
		// check if the add is on a wish list
		List<WishlistEntity> wishs = wishlistRepository.findByAdId(id);
		
		if (wishs != null && !wishs.isEmpty()){
			wishlistRepository.delete(wishs);
		}
		
		adRepository.delete(ad);
		
		return new ResponseBean("ad deleted successfuly", true);
	}
	
	@RequestMapping(method = RequestMethod.GET, 
	        produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<AdBean> searchAds( @RequestParam(value ="userId", required=false) Long userId,
			@RequestParam(value ="petType", required=false) Integer petType,
			@RequestParam(value ="petSize", required=false) Integer petSize,
			@RequestParam(value ="petGender", required=false) Integer petGender,
			@RequestParam(value ="ageFrom", required=false) Long ageFrom,
			@RequestParam(value ="ageTo", required=false) Long ageTo,
			@RequestParam(value ="inWishList", required=false, defaultValue = "0") boolean inWishList) throws UserNotFoundException
	{	
   
    	List<AdEntity> allAds;
    	List<AdEntity> filteredAds = new ArrayList<AdEntity>();
    	List<AdBean> result = new ArrayList<AdBean>();
		UserEntity user = null;

    	// check if filtering by user id is needed 
    	if (userId != null){
    		
    		// check that the user exists
    		user = userRepository.findOne(userId);
        	
        	if (user == null) {
        		throw new UserNotFoundException();
        	}
        	
        	// get all ads for user
    		allAds = adRepository.findByuserEntity(user);
    	} else {
    		// get all ads
    		allAds = adRepository.findAll();
    	}
    	
    	filteredAds.addAll(allAds);
		
		// filter ads by parameters
		for (AdEntity adEntity : allAds) {
			PetEntity currentPet = adEntity.getPetEntity();
			
			// check pet's type
			if (petType != null && !petType.equals(currentPet.getType())){
				filteredAds.remove(adEntity);
				continue;
			}
			
			// check pet's size
			if (petSize != null && !petSize.equals(currentPet.getSize())){
				filteredAds.remove(adEntity);
				continue;
			}
			
			// check pet's gender
			if (petGender != null && !petGender.equals(currentPet.getGender())){
				filteredAds.remove(adEntity);
				continue;
			}
			
			// check pet's age
			if (ageFrom != null && ageFrom > currentPet.getAge()){
				filteredAds.remove(adEntity);
				continue;
			}
			if (ageTo != null && ageTo < currentPet.getAge()){
				filteredAds.remove(adEntity);
				continue;
			}
			if (inWishList && user != null) {
				WishlistEntity wishlistEntity =
						wishlistRepository.findByUserIdAndAdId(user.getId(), adEntity.getId());
				if (wishlistEntity == null) {
					filteredAds.remove(adEntity);
				}
			}
		}
		
		// convert all filtered ads to AdBean objects
		for (AdEntity adEntity : filteredAds) {
			AdBean adBean = new AdBean(adEntity);
			result.add(adBean);
		}
		
				
		return result;
	}
	
	@RequestMapping(value="/{id}", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ResponseBean updateAd(@PathVariable("id") Long id, @RequestBody UpdateAdRequestEntity request,
			@RequestParam(value ="userId", required=true) Long userId) throws UserNotFoundException, AdNotFoundException, UserUnauthorizedException 
	{	
    	UserEntity user = userRepository.findOne(userId);
    	
    	if (user == null) {
    		throw new UserNotFoundException();
    	}
    	
    	AdEntity ad = adRepository.findOne(id);
		
		if (ad == null){
			throw new AdNotFoundException();
		}
		
		// check validation
		if (ad.getUserEntity() == null || !userId.equals(ad.getUserEntity().getId())){
			throw new UserUnauthorizedException();
		}
		
    	
		// get pet
		PetEntity pet = ad.getPetEntity();
		pet.setAge(request.getPetAge());
		pet.setDescription(request.getPetDescription());
		pet.setName(request.getPetName());
		pet.setPhotoUrl(request.getPetPhotoUrl());
		pet.setSize(request.getPetSize());
		
		// create ad
		Date now = new Date();
		ad.setUpdatedAt(now);
		
		// bind ad and pet
//		pet.setAdEntity(ad);
//		ad.setPetEntity(pet);
		
		//save data
		petRepository.save(pet);
		adRepository.save(ad);
		
		return new ResponseBean("ad updated successfuly", true);
	}

}
