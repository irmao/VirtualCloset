package vidias.virtualcloset.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes the CSRF Token
 * 
 * @author vdias
 *
 */
@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @RequestMapping
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
