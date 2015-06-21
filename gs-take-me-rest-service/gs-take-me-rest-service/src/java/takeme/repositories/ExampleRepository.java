package repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import entities.ExampleEntity;

public interface ExampleRepository extends CrudRepository<ExampleEntity,Long>
{	
	@Query("<JPQ statement here>")
	 Set<ExampleEntity> findByName(String Name);
}
