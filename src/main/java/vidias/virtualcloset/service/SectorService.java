package vidias.virtualcloset.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.Sector;
import vidias.virtualcloset.repository.SectorRepository;

@Service
public class SectorService {
    
    @Autowired
    private SectorRepository sectorRepository;
    
    public Collection<Sector> getAll() {
        List<Sector> sectors = new LinkedList<>();
        sectorRepository.findAll().forEach(sectors::add);
        return sectors;    
    }
    
    public Collection<Sector> getAllNonOptional() {
        return sectorRepository.findAllNonOptional();
    }
}
