package entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by avi.alima on 11/8/15.
 * Enjoy
 */
public class WishlistPK implements Serializable {

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

    @Override
    public int hashCode() {
        int hash = 31;
        hash += hash * userId;
        hash += hash * adId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().isAssignableFrom(WishlistPK.class)) return false;
        WishlistPK wishlistPK = (WishlistPK)obj;

        return userId.equals(wishlistPK.userId) && adId.equals(wishlistPK.adId);
    }

}
