package vidias.virtualcloset.repository;

import org.springframework.data.repository.CrudRepository;

import vidias.virtualcloset.model.User;

/**
 * Controls repository operations related to {@link User}
 * 
 * @author vdias
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {
    
}
