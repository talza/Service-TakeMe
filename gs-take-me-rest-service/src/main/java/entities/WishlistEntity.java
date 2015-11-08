package entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by avi.alima on 11/8/15.
 * Enjoy
 */
@Entity
@IdClass(WishlistPK.class)
@Table(name="wishlists")
public class WishlistEntity implements Serializable {

    @Id
    @Column(name="user_id", insertable = false, updatable = false)
    private Long userId;

    @Id
    @Column(name="ad_id", insertable = false, updatable = false)
    private Long adId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

}
