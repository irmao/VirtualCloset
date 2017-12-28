package vidias.virtualcloset.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.model.BodyPosition;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.model.ClosetClothing;
import vidias.virtualcloset.model.Clothing;

@Service
public class RandomClosetService {
    private static final int MAX_TRIES = 10;

    @Autowired
    private ClothingService clothingService;

    public Closet generateRandomCloset() {
        Closet closet = new Closet();
        closet.setClosetClothing(new ArrayList<>());

        Random random = new Random(new Date().getTime());

        Collection<BodyPosition> mandatoryBodyPositions = BodyPosition.getMandatoryBodyPositions();

        Collection<Clothing> allClothes = clothingService.getAll();

        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = new HashMap<>();

        for (BodyPosition bodyPosition : mandatoryBodyPositions) {
            clothesByBodyPosition.put(bodyPosition,
                    new ArrayList<>(
                            allClothes.stream().filter(c -> c.getSector().getBodyPositions().contains(bodyPosition))
                                    .collect(Collectors.toList())));
        }

        Set<BodyPosition> occupiedBodyPositions = new HashSet<>();
        for (int tryNumber = 0; tryNumber < MAX_TRIES
                && occupiedBodyPositions.size() < mandatoryBodyPositions.size(); tryNumber++) {

            for (BodyPosition bodyPosition : mandatoryBodyPositions) {
                ArrayList<Clothing> clothes = clothesByBodyPosition.get(bodyPosition);
                IntStream randomInts = random.ints(0, clothes.size());
                OptionalInt anyRandomInt = randomInts.limit(clothes.size())
                        .filter(i -> fit(clothes.get(i), occupiedBodyPositions)).findAny();

                if (anyRandomInt.isPresent()) {
                    ClosetClothing closetClothing = new ClosetClothing();
                    closetClothing.setClothing(clothes.get(anyRandomInt.getAsInt()));
                    closetClothing.setzIndex(0);
                    closet.getClosetClothing().add(closetClothing);
                    occupiedBodyPositions.addAll(closetClothing.getClothing().getSector().getBodyPositions());
                }
            }
        }

        return closet;
    }

    /**
     * Returns true if all body positions that the clothing occupies are not in the
     * occupiedBodyPositions collection
     * 
     * @param a
     * @param b
     * @return
     */
    private boolean fit(Clothing clothing, Collection<BodyPosition> occupiedBodyPositions) {
        return clothing.getSector().getBodyPositions().stream().noneMatch(b -> occupiedBodyPositions.contains(b));
    }
}
