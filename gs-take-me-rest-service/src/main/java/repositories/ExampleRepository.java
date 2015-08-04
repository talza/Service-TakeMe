package repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import entities.ExampleEntity;

public interface ExampleRepository extends JpaRepository<ExampleEntity,Long>
{	
	 Set<ExampleEntity> findByName(String Name);
}
