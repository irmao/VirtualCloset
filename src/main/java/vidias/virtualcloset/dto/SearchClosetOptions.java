package vidias.virtualcloset.dto;

public class SearchClosetOptions {
    private Boolean fancy;
    private String category;
    private Long closetId;
    
    public SearchClosetOptions( ) {
        fancy = false;
    }

    public Boolean getFancy() {
        return fancy;
    }

    public void setFancy(Boolean fancy) {
        this.fancy = fancy;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getClosetId() {
		return closetId;
	}

	public void setClosetId(Long closetId) {
		this.closetId = closetId;
	}
}
