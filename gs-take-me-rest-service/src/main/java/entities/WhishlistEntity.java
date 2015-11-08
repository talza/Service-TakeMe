package entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by avi.alima on 11/8/15.
 * Enjoy
 */
@Entity
@IdClass(WhishlistPK.class)
@Table(name="wishlists")
public class WhishlistEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity  userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name="ad_id")
    private AdEntity    adEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public AdEntity getAdEntity() {
        return adEntity;
    }

    public void setAdEntity(AdEntity adEntity) {
        this.adEntity = adEntity;
    }

}
