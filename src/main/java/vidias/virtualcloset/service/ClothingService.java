package vidias.virtualcloset.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.Clothing;
import vidias.virtualcloset.repository.ClothingRepository;

@Service
@Transactional
public class ClothingService {

    @Autowired
    private ClothingRepository clothingRepository;
    
    @Autowired
    private ClosetService closetService;

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
    
    public void delete(Long clothingId) {
        Clothing clothing = clothingRepository.findOne(clothingId);
        
        if (clothing == null) {
            throw new IllegalArgumentException("Id " + clothingId + " not found");
        }
        
        closetService.deleteAllClosetsUsingClothing(clothing);
        clothingRepository.delete(clothing);
    }

    public Clothing update(Long clothingId, Clothing newClothing) {
        Clothing clothing = clothingRepository.findOne(clothingId);
        
        if (clothing == null) {
            throw new IllegalArgumentException("Id " + clothingId + " not found");
        }
        
        newClothing.setId(clothingId);
        return clothingRepository.save(newClothing);
    }
}
