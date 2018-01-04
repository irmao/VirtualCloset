package vidias.virtualcloset.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VC_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VC_USER_SEQ")
    @SequenceGenerator(sequenceName = "VC_USER_SEQ", name = "VC_USER_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column
    private Boolean active;
    
    @Column
    private Date passwordExpirationDate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    public void setPasswordExpirationDate(Date passwordExpirationDate) {
        this.passwordExpirationDate = passwordExpirationDate;
    }

}
