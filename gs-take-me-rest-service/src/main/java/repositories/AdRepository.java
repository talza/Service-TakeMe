package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.AdEntity;
import entities.UserEntity;

public interface AdRepository extends JpaRepository<AdEntity,Long> {
	
	List<AdEntity> findByuserEntity(UserEntity user);
}
