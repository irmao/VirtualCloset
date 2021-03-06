package vidias.virtualcloset.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.dto.SearchClosetOptions;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.service.ClosetService;
import vidias.virtualcloset.service.RandomClosetServiceFactory;

/**
 * Controls the endpoints related to {@link Closet}
 * 
 * @author vdias
 *
 */
@RestController
@RequestMapping("/api/closet")
public class ClosetController {

    @Autowired
    private ClosetService closetService;

    @Autowired
    private RandomClosetServiceFactory randomClosetServiceFactory;

    @RequestMapping
    @ResponseBody
    public Collection<Closet> getClosets(SearchClosetOptions options) {
    	if (options.getCategory() != null) {
    		return closetService.getByCategory(options.getCategory());
    	}
    	
        return closetService.getAll();
    }

    @RequestMapping("random")
    @ResponseBody
    public Closet getRandomCloset(SearchClosetOptions options) {
        return randomClosetServiceFactory.getRandomClosetService().generateRandomCloset(options);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCloset(@RequestBody Closet closet) {
        closetService.save(closet);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public void deleteCloset(@PathVariable Long id) {
        closetService.delete(id);
    }
}
