package vidias.virtualcloset.model;

import static java.util.Arrays.asList;

import java.util.Collection;

/**
 * The body positions that can be occupied by clothes or accessories.
 * 
 * @author vdias
 *
 */
public enum BodyPosition {
    HEAD, TOP, BOTTOM, FOOT;

    public static Collection<BodyPosition> getMandatoryBodyPositions() {
        return asList(TOP, BOTTOM, FOOT);
    }
}
