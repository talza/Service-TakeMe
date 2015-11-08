package repositories;

import entities.AdEntity;
import entities.UserEntity;
import entities.WhishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by avi.alima on 11/8/15.
 */
public interface WhishlistRepository extends JpaRepository<WhishlistEntity,Long> {

    List<WhishlistEntity> findByUserEntity(UserEntity userEntity);
    List<WhishlistEntity> findByAdEntity(AdEntity adEntity);

    WhishlistEntity findByUserEntityAndAdEntity(UserEntity userEntity, AdEntity adEntity);

}
