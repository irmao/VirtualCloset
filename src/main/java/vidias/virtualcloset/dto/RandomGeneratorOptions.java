package vidias.virtualcloset.dto;

public class RandomGeneratorOptions {
    private Boolean fancy;
    
    public RandomGeneratorOptions( ) {
        fancy = false;
    }

    public Boolean getFancy() {
        return fancy;
    }

    public void setFancy(Boolean fancy) {
        this.fancy = fancy;
    }
}
