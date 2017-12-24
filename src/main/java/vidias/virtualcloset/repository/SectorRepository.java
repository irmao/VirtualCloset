package vidias.virtualcloset.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import vidias.virtualcloset.model.Sector;

/**
 * Controls repository operations related to {@link Sector}
 * 
 * @author vdias
 *
 */
public interface SectorRepository extends CrudRepository<Sector, Long> {

    @Query("select s from Sector s where s.optional is false")
    public Collection<Sector> findAllNonOptional();

}
