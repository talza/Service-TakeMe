package resources;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.CreateAdRequestEntity;
import api.UpdateAdRequestEntity;
import entities.AdEntity;
import entities.PetEntity;
import entities.ResponseEntity;
import entities.UserEntity;
import exceptions.AdNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.UserUnauthorizedException;
import repositories.AdRepository;
import repositories.PetRepository;
import repositories.UserRepository;

@RestController
@RequestMapping(value="/ad")
public class AdResource {
	
	@Autowired
	AdRepository adRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PetRepository petRepository;

	@RequestMapping(value="/createAd", 
	        method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity createAdd(@RequestBody CreateAdRequestEntity request,
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
//		pet.setAdEntity(ad);
		ad.setPetEntity(pet);
		
		//save data
		petRepository.save(pet);
		adRepository.save(ad);
		
		return new ResponseEntity(user.getId());
	}
	
	@RequestMapping(value="/{id}", 
	        method = RequestMethod.GET, 
	        produces = "application/json;charset=utf-8")
	@ResponseBody
	public AdEntity getAd(@PathVariable("id") Long id, 
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
		
		return ad;
	}
	
	@RequestMapping(value="/deleteAd/{id}", 
	        method = RequestMethod.DELETE, 
	        produces = "application/json;charset=utf-8")
	@ResponseBody
	public void deleteAd(@PathVariable("id") Long id, 
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
		
		adRepository.delete(ad);
	}
	
	@RequestMapping(value="/updateAd/{id}", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity updateAd(@PathVariable("id") Long id, @RequestBody UpdateAdRequestEntity request,
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
//		pet.setGender(request.getPetGender());
		pet.setName(request.getPetName());
		pet.setPhotoUrl(request.getPetPhotoUrl());
		pet.setSize(request.getPetSize());
//		pet.setType(request.getPetType());
		
		// create ad
		Date now = new Date();
		ad.setUpdatedAt(now);
		
		// bind ad and pet
//		pet.setAdEntity(ad);
//		ad.setPetEntity(pet);
		
		//save data
		petRepository.save(pet);
		adRepository.save(ad);
		
		return new ResponseEntity(user.getId());
	}

}