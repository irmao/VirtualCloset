package vidias.virtualcloset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.exception.DuplicatedItemException;
import vidias.virtualcloset.exception.InvalidEntityException;
import vidias.virtualcloset.helper.Constants;
import vidias.virtualcloset.model.Role;
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
        user.setName(user.getName().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createNewUser(User user) {
        validate(user);
        user.setRole(Role.USER);
        user.setActive(true);
        return save(user);
    }

    public void validate(User user) {
        String username = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();

        if (username == null || username.length() < 3 || username.length() > 60 || !username.matches("[a-z0-9]+")) {
            throw new InvalidEntityException(Constants.INVALID_USERNAME_MESSAGE);
        }

        if (password == null || password.length() < 8 || password.length() > 60) {
            throw new InvalidEntityException(Constants.INVALID_PASSWORD_MESSAGE);
        }

        if (email == null || email.isEmpty()) {
            throw new InvalidEntityException(Constants.INVALID_EMAIL_MESSAGE);
        }

        if (!userRepository.findByName(username).isEmpty()) {
            throw new DuplicatedItemException(Constants.USERNAME_ALREADY_EXISTS_MESSAGE);
        }

        if (!userRepository.findByName(email).isEmpty()) {
            throw new DuplicatedItemException(Constants.EMAIL_ALREADY_EXISTS_MESSAGE);
        }
    }
}
