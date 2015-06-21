package resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import repositories.ExampleRepository;
import entities.ExampleEntity;


@RestController
public class TamplateResource 
{
	
	@Autowired
	ExampleRepository exampleRepository;
		
    @RequestMapping(value="/Example", 
    		        method = RequestMethod.GET, 
    		        produces = "application/json;charset=utf-8",
    		        consumes="application/json;charset=utf-8")
    @ResponseBody
    public Iterable<ExampleEntity> findAll(@RequestParam(value="name", required=false, defaultValue="World") String name) 
    {	
    	Iterable<ExampleEntity> list = null;
    	
    	if(name != null && 
    	   name != "" )
	    {
    		list = exampleRepository.findByName(name);
	    }
    	else
    	{
    		list = exampleRepository.findAll();
    	}
    
        return list;
    }

    @RequestMapping(value="/Example/{id}", 
	        method = RequestMethod.GET, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
	@ResponseBody
	public ExampleEntity findByID(@PathVariable("id") Long id) 
	{	
	
    	return exampleRepository.findOne(id);
	}	    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.POST, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public ExampleEntity create(@RequestBody ExampleEntity entity) 
    {	
        return exampleRepository.save(entity);
    }	    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.PUT, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public ExampleEntity update(@RequestBody ExampleEntity entity) 
    {	
        return exampleRepository.save(entity);
    }	 
    
    
    @RequestMapping(value="/Example", 
	        method = RequestMethod.DELETE, 
	        produces = "application/json;charset=utf-8",
	        consumes="application/json;charset=utf-8")
    @ResponseBody
    public void delete(@RequestBody ExampleEntity entity) 
    {	
        exampleRepository.delete(entity);
    }	        
	    
	
}
