package vidias.virtualcloset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vidias.virtualcloset.model.User;
import vidias.virtualcloset.service.UserService;

/**
 * Exposes the CSRF Token
 * 
 * @author vdias
 *
 */
@RestController
@RequestMapping("/login/api")
public class LoginController {
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "newuser", method = RequestMethod.POST)
    public void createNewUser(@RequestBody User user) {
        userService.createNewUser(user);
    }
}
