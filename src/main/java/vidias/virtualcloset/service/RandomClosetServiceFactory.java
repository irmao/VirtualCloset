package vidias.virtualcloset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RandomClosetServiceFactory {
    @Autowired
    private BasicRandomClosetService basicRandomClosetService;

    public RandomClosetService getRandomClosetService() {
        return basicRandomClosetService;
    }
}
