package vidias.virtualcloset.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "VC_CLOTHING")
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VC_CLOTHING_SEQ")
    @SequenceGenerator(name = "VC_CLOTHING_SEQ", sequenceName = "VC_CLOTHING_SEQ")
    private Long id;

    @Column
    private String name;

    @Column
    private String pictureUrl;

    @Column
    @Type(type = "numeric_boolean")
    private Boolean fancy;

    @Column
    private Integer usageCount;

    @Column
    private Integer rating;

    @Transient
    private Collection<Weather> weather;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn
    private Sector sector;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn
    private User user;

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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Boolean getFancy() {
        return fancy;
    }

    public void setFancy(Boolean fancy) {
        this.fancy = fancy;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Collection<Weather> getWeather() {
        return weather;
    }

    public void setWeather(Collection<Weather> weather) {
        this.weather = weather;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
