package vidias.virtualcloset.dto;

public class SearchClosetOptions {
    private Boolean fancy;
    private String category;
    
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
}
