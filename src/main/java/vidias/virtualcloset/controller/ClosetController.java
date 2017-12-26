package vidias.virtualcloset.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.service.ClosetService;

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

    @RequestMapping
    @ResponseBody
    public Collection<Closet> getClosets() {
        return closetService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addCloset(@RequestBody Closet closet) {
        closetService.save(closet);
    }
}
