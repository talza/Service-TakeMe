package resources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Beans.AdBean;
import Beans.AddAdToWishlistRequestBean;
import Beans.CreateAdRequestBean;
import Beans.ResponseBean;
import Beans.UpdateAdRequestBean;
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
	public ResponseBean createAdd(@RequestBody CreateAdRequestBean request,
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

		boolean isInWishlist = wishlistRepository.findByUserIdAndAdId(userId, ad.getId()) != null;

		AdBean adBean = new AdBean(ad);
		adBean.setIsInWishlist(isInWishlist);

		return adBean;
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
			@RequestParam(value ="inWishList", required=false, defaultValue = "0") boolean inWishList,
		    @RequestParam(value ="isMyPet", required=false, defaultValue = "0") boolean isMyPet) throws UserNotFoundException
	{
   
    	List<AdEntity> allAds;
		List<Long> userWishlist = new ArrayList<>();
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

			List<WishlistEntity> wishlist = wishlistRepository.findByUserId(userId);
			// Map to extract adId from WishlistEntity
			userWishlist.addAll(wishlist.stream().map(WishlistEntity::getAdId).collect(Collectors.toList()));
		}

		if (user != null && isMyPet) {
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
			if (inWishList && user != null && !userWishlist.contains(adEntity.getId())) {
				filteredAds.remove(adEntity);
			}
		}
		
		// convert all filtered ads to AdBean objects
		for (AdEntity adEntity : filteredAds) {
			AdBean adBean = new AdBean(adEntity);
			adBean.setIsInWishlist(userWishlist.contains(adEntity.getId()));
			result.add(adBean);
		}
		
				
		return result;
	}
	
	@RequestMapping(value="/{id}", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ResponseBean updateAd(@PathVariable("id") Long id, @RequestBody UpdateAdRequestBean request,
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
		
		//save data
		petRepository.save(pet);
		adRepository.save(ad);
		
		return new ResponseBean("ad updated successfuly", true);
	}
	    
	@RequestMapping(value="/{user_id}/wishlist",
			method = RequestMethod.POST,
			produces = "application/json;charset=utf-8",
			consumes="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, Object> addAdToWishlist(@PathVariable("user_id") Long userId, @RequestBody AddAdToWishlistRequestBean requestEntity) throws UserNotFoundException, AdNotFoundException {
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
			
			// send push notification 
			try {
				sendAndroidPushNotification(getPushNotificationRequestString(ad.getPetEntity().getName(), ad.getUserEntity().getRegistrationDeviceKey()));
			} catch (IOException e) {
				// do nothing.
			}
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
	
	private String sendAndroidPushNotification(String pushReq) throws IOException {

        URL url;
        HttpURLConnection connection = null;

        // create a connection
        url = new URL("https://android.googleapis.com/gcm/send");
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(pushReq.getBytes("UTF-8").length));
        connection.setRequestProperty("Authorization", "key=AIzaSyBztI_Lgbxs7q58RxN1g_7uR6etmLfJQjo");

        connection.setUseCaches (false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream ());
        wr.write(pushReq.getBytes("UTF-8"));
        wr.flush ();
        wr.close ();

        // get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();

        return response.toString();
	}
		
	private String getPushNotificationRequestString(String petName, String deviceToNotify){
		StringBuffer buf = new StringBuffer("{");
		buf.append("\"data\"").append(":").append("{");
		buf.append("\"title\"").append(":").append("\"Take Me:\"").append(",");
		buf.append("\"message\"").append(":").append("\"Someone likes your ").append(petName).append("\"");
		buf.append("},");
		buf.append("\"registration_ids\"").append(":");
		buf.append("[").append("\"").append(deviceToNotify).append("\"").append("]");
		buf.append("}");
		
		return buf.toString();
  
	}

}
