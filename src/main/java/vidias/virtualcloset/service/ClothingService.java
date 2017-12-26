package vidias.virtualcloset.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.Clothing;
import vidias.virtualcloset.repository.ClothingRepository;

@Service
public class ClothingService {

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private UserService userService;

    public Collection<Clothing> getAll() {
        return clothingRepository.findByUserId(userService.getCurrentUser().getId());
    }

    public Clothing save(Clothing clothing) {
        if (clothing.getId() == null) {
            clothing.setRating(0);
            clothing.setUsageCount(0);
            clothing.setUser(userService.getCurrentUser());
        }

        return clothingRepository.save(clothing);
    }
}
