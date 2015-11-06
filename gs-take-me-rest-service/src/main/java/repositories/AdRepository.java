package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.AdEntity;

public interface AdRepository extends JpaRepository<AdEntity,Long> {



}
