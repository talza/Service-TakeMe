package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity,Long> {

	
}
