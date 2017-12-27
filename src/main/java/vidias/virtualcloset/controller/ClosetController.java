package vidias.virtualcloset.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.service.ClosetService;
import vidias.virtualcloset.service.RandomClosetService;

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
    private RandomClosetService randomClosetService;

    @RequestMapping
    @ResponseBody
    public Collection<Closet> getClosets() {
        return closetService.getAll();
    }

    @RequestMapping("random")
    @ResponseBody
    public Closet getRandomCloset() {
        return randomClosetService.generateRandomCloset();
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
