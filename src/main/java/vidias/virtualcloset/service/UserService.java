package vidias.virtualcloset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.User;
import vidias.virtualcloset.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    private User currentUser;

    public UserService() {
        currentUser = new User();
        currentUser.setId(1L);
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
