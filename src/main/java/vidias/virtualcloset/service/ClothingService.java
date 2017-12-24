package vidias.virtualcloset.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.Clothing;
import vidias.virtualcloset.model.User;
import vidias.virtualcloset.repository.ClothingRepository;

@Service
public class ClothingService {
    
    @Autowired
    private ClothingRepository clothingRepository;
    
    public Collection<Clothing> getAll() {
        List<Clothing> clothes = new LinkedList<>();
        clothingRepository.findAll().forEach(clothes::add);
        return clothes;    
    }
    
    public Clothing save(Clothing clothing) {
        if (clothing.getId() == null) {
            User user = new User();
            user.setId(1L);
            
            clothing.setRating(0);
            clothing.setUsageCount(0);
            clothing.setUser(user);
        }
        
        return clothingRepository.save(clothing);
    }
}
