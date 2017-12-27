package vidias.virtualcloset.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import vidias.virtualcloset.model.Closet;

/**
 * Controls repository operations related to {@link Closet}
 * 
 * @author vdias
 *
 */
public interface ClosetRepository extends CrudRepository<Closet, Long> {

    @Query("select c from Closet c where c.user.id = :userId")
    public Collection<Closet> findByUserId(@Param("userId") Long userId);

    @Query("select distinct c from Closet c, ClosetClothing cc where "
            + "cc member of c.closetClothing and "
            + "cc.clothing.id = :clothingId")
    public Collection<Closet> findByClothingId(@Param("clothingId") Long clothingId);
}
