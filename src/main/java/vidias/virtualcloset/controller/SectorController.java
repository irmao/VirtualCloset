package vidias.virtualcloset.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.model.Sector;
import vidias.virtualcloset.service.SectorService;

/**
 * Controls the endpoints related to {@link Sector}
 * 
 * @author vdias
 *
 */
@RestController
@RequestMapping("/api/sector")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @RequestMapping
    @ResponseBody
    public Collection<Sector> getSectors() {
        return sectorService.getAll();
    }
}
