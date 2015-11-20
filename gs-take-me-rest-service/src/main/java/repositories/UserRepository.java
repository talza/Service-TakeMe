package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>
{	
	 UserEntity findByEmail(String email);
	 UserEntity findByFacebookToken(String facebookToken);
}
