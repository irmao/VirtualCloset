package vidias.virtualcloset.service;

import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.User;

@Service
public class UserService {
    private User currentUser;

    public UserService() {
        currentUser = new User();
        currentUser.setId(1L);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
