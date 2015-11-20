package repositories;

import entities.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by avi.alima on 11/8/15.
 */
public interface WishlistRepository extends JpaRepository<WishlistEntity,Long> {

    List<WishlistEntity> findByUserId(Long userId);
    List<WishlistEntity> findByAdId(Long adId);
    

    WishlistEntity findByUserIdAndAdId(Long userId, Long adId);

}
