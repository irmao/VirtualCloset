package vidias.virtualcloset.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.model.Clothing;
import vidias.virtualcloset.service.ClothingService;

/**
 * Controls the endpoints related to {@link Clothing}
 * 
 * @author vdias
 *
 */
@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @RequestMapping
    @ResponseBody
    public Collection<Clothing> getClothes() {
        return clothingService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addClothing(@RequestBody Clothing clothing) {
        clothingService.save(clothing);
    }
    
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public void deleteCloset(@PathVariable Long id) {
        clothingService.delete(id);
    }
}
