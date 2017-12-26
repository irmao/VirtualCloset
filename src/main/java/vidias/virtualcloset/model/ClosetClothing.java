package vidias.virtualcloset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*
 * travel cases
 * saved looks
 * whishlist
 * planner
 * all clothes
 */

@Entity
@Table(name = "VC_CLOSET_CLOTHING")
public class ClosetClothing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VC_CLOSET_CLOTHING_SEQ")
    @SequenceGenerator(sequenceName = "VC_CLOSET_CLOTHING_SEQ", name = "VC_CLOSET_CLOTHING_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Closet closet;
    
    @ManyToOne
    @JoinColumn
    private Clothing clothing;

    @Column(name = "zindex")
    private Integer zIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Closet getCloset() {
        return closet;
    }

    public void setCloset(Closet closet) {
        this.closet = closet;
    }

    public Clothing getClothing() {
        return clothing;
    }

    public void setClothing(Clothing clothing) {
        this.clothing = clothing;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }
    
    
}
