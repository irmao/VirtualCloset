package vidias.virtualcloset.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import vidias.virtualcloset.model.Clothing;

/**
 * Controls repository operations related to {@link Clothing}
 * 
 * @author vdias
 *
 */
public interface ClothingRepository extends CrudRepository<Clothing, Long> {
    @Query("select c from Clothing c where c.user.id = :userId")
    public Collection<Clothing> findByUserId(@Param("userId") Long userId);
}
