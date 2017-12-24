package vidias.virtualcloset.repository;

import org.springframework.data.repository.CrudRepository;

import vidias.virtualcloset.model.Clothing;

/**
 * Controls repository operations related to {@link Clothing}
 * 
 * @author vdias
 *
 */
public interface ClothingRepository extends CrudRepository<Clothing, Long> {

}
