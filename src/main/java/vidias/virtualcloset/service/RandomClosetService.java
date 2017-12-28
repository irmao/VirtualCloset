package vidias.virtualcloset.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.exception.InvalidClosetException;
import vidias.virtualcloset.exception.RandomGeneratorException;
import vidias.virtualcloset.helper.Constants;
import vidias.virtualcloset.model.BodyPosition;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.model.ClosetClothing;
import vidias.virtualcloset.model.Clothing;

@Service
public class RandomClosetService {
    private static final int MAX_TRIES = 1000;

    @Autowired
    private ClothingService clothingService;

    public Closet generateRandomCloset() {
        Closet closet = new Closet();
        closet.setClosetClothing(new ArrayList<>());

        Random random = new Random(new Date().getTime());

        Collection<BodyPosition> mandatoryBodyPositions = BodyPosition.getMandatoryBodyPositions();

        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = getClothesByBodyPosition();

        Set<BodyPosition> occupiedBodyPositions = new HashSet<>();

        int safeCounter = 0;
        while (occupiedBodyPositions.size() < mandatoryBodyPositions.size()) {

            for (BodyPosition bodyPosition : mandatoryBodyPositions) {
                ArrayList<Clothing> clothes = clothesByBodyPosition.get(bodyPosition);

                Clothing clothing = null;
                try {
                    clothing = getRandomClothing(random, clothes, occupiedBodyPositions);    
                } catch (RandomGeneratorException e) {
                    // try again next round
                    System.err.println(e);
                }
                
                if (clothing != null) {
                    ClosetClothing closetClothing = new ClosetClothing(
                            getRandomClothing(random, clothes, occupiedBodyPositions), 0);
                    closet.getClosetClothing().add(closetClothing);
                    occupiedBodyPositions.addAll(closetClothing.getClothing().getSector().getBodyPositions());    
                }
            }

            safeCounter++;
            if (safeCounter >= MAX_TRIES) {
                throw new RandomGeneratorException(Constants.SOMETHING_WRONG_RANDOM_MESSAGE);
            }
        }

        return closet;
    }

    Clothing getRandomClothing(Random random, ArrayList<Clothing> clothes, Set<BodyPosition> occupiedBodyPositions)
            throws RandomGeneratorException {
        IntStream randomInts = random.ints(0, clothes.size());
        int index = randomInts.limit(MAX_TRIES).filter(i -> fit(clothes.get(i), occupiedBodyPositions)).findAny()
                .orElseThrow(() -> new RandomGeneratorException(Constants.SOMETHING_WRONG_RANDOM_MESSAGE));
        return clothes.get(index);
    }

    Map<BodyPosition, ArrayList<Clothing>> getClothesByBodyPosition() {
        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = new HashMap<>();

        for (BodyPosition bodyPosition : BodyPosition.getMandatoryBodyPositions()) {
            ArrayList<Clothing> clothesInThatBodyPosition = new ArrayList<>(clothingService.getAll().stream()
                    .filter(c -> c.getSector().getBodyPositions().contains(bodyPosition)).collect(Collectors.toList()));

            if (clothesInThatBodyPosition.isEmpty()) {
                throw new InvalidClosetException(Constants.generateMissingClothingForMessage(bodyPosition));
            }

            clothesByBodyPosition.put(bodyPosition, clothesInThatBodyPosition);
        }

        return clothesByBodyPosition;
    }

    /**
     * Returns true if all body positions that the clothing occupies are not in the
     * occupiedBodyPositions collection
     * 
     * @param a
     * @param b
     * @return
     */
    boolean fit(Clothing clothing, Collection<BodyPosition> occupiedBodyPositions) {
        return clothing.getSector().getBodyPositions().stream().noneMatch(b -> occupiedBodyPositions.contains(b));
    }
}
