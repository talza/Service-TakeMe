package entities;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by avi.alima on 11/8/15.
 * Enjoy
 */
public class WhishlistPK implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity  userEntity;

    @Id
    @ManyToOne
    @JoinColumn(name="ad_id")
    private AdEntity    adEntity;

    @Override
    public int hashCode() {
        int hash = 31;
        if (adEntity != null)   hash *= adEntity.hashCode();
        if (userEntity != null) hash *= userEntity.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        WhishlistPK whishlistPK = (WhishlistPK)obj;
        if (adEntity == null || !adEntity.equals(whishlistPK.adEntity))       return false;
        if (userEntity == null || !userEntity.equals(whishlistPK.userEntity)) return false;

        return true;
    }

}
