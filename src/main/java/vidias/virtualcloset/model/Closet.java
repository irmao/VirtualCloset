package vidias.virtualcloset.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
 * travel cases
 * saved looks
 * whishlist
 * planner
 * all clothes
 */

@Entity
@Table(name = "VC_CLOSET")
public class Closet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VC_CLOSET_SEQ")
    @SequenceGenerator(sequenceName = "VC_CLOSET_SEQ", name = "VC_CLOSET_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Boolean bodyPositionOverlap;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "closet")
    @JsonManagedReference
    private Collection<ClosetClothing> closetClothing;

    @ManyToOne
    @JoinColumn
    private User user;
    
    @Column
    private String category;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getBodyPositionOverlap() {
        return bodyPositionOverlap;
    }

    public void setBodyPositionOverlap(Boolean bodyPositionOverlap) {
        this.bodyPositionOverlap = bodyPositionOverlap;
    }

    public Collection<ClosetClothing> getClosetClothing() {
        return closetClothing;
    }

    public void setClosetClothing(Collection<ClosetClothing> closetClothing) {
        this.closetClothing = closetClothing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
