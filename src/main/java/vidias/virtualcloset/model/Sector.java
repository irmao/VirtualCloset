package vidias.virtualcloset.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Represents the categories of the clothes. Each clothing piece belongs to a
 * sector, and each sector occupies one or more body positions. For example, the
 * sector "SHIRT" occupies the body position TOP, the sector HAT occupies HEAD
 * and the sector DRESS occupies TOP aswell as BOTTOM.
 * 
 * @author vdias
 *
 */
@Entity
@Table(name = "VC_SECTOR")
public class Sector {
    /**
     * The id of the sector
     */
    @Id
    private Long id;

    /**
     * The name of the sector
     */
    @Column
    private String name;

    /**
     * The list of body positions that the sector occupies
     */
    @ElementCollection(targetClass = BodyPosition.class)
    @JoinTable(name = "VC_SECTOR_BODY_POSITION", joinColumns = @JoinColumn(name = "SECTOR_ID"))
    @Column(name = "BODY_POSITION")
    @Enumerated(EnumType.STRING)
    private Collection<BodyPosition> bodyPositions;

    /**
     * Indicates whether is optional to use a clothing piece in that section or not.
     */
    @Column
    @Type(type = "numeric_boolean")
    private Boolean optional;

    /**
     * Indicates whether a piece of clothing can be used on top of this section. If
     * topMost is true, no section can occupy the same body position as this
     * section's if they are on top of this one. For example, a jacket can be used
     * on top of a shirt, and no other section can be used on top of the jacket. So
     * topMost=true for Jackets and topMost=false for shirts.
     */
    @Column
    @Type(type = "numeric_boolean")
    private Boolean topMost;

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

    public Collection<BodyPosition> getBodyPositions() {
        return bodyPositions;
    }

    public void setBodyPositions(Collection<BodyPosition> bodyPositions) {
        this.bodyPositions = bodyPositions;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Boolean getTopMost() {
        return topMost;
    }

    public void setTopMost(Boolean topMost) {
        this.topMost = topMost;
    }
}
