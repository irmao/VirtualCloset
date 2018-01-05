package vidias.virtualcloset.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import vidias.virtualcloset.dto.RandomGeneratorOptions;
import vidias.virtualcloset.exception.InvalidEntityException;
import vidias.virtualcloset.exception.RandomGeneratorException;
import vidias.virtualcloset.helper.Constants;
import vidias.virtualcloset.model.BodyPosition;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.model.ClosetClothing;
import vidias.virtualcloset.model.Clothing;

public abstract class RandomClosetService {
    protected static final int MAX_TRIES = 1000;

    @Autowired
    private ClothingService clothingService;

    /**
     * Generate a random closet based in the given generation options
     * 
     * @param generatorOptions
     * @return
     */
    public Closet generateRandomCloset(RandomGeneratorOptions generatorOptions) {
        Closet closet = new Closet();
        closet.setClosetClothing(new ArrayList<>());

        Random random = new Random(new Date().getTime());

        Collection<BodyPosition> mandatoryBodyPositions = BodyPosition.getMandatoryBodyPositions();

        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = getClothesByBodyPosition(generatorOptions);

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

    /**
     * Returns a random clothing of the given collection of clothes. The returned
     * clothing does not occupy a body position that is already occupied
     * 
     * @param random
     * @param clothes
     * @param occupiedBodyPositions
     * @return
     * @throws RandomGeneratorException
     */
    public abstract Clothing getRandomClothing(Random random, ArrayList<Clothing> clothes, Set<BodyPosition> occupiedBodyPositions);

    /**
     * Returns a map containing all body positions that are required to have clothes
     * on and the list of all clothes that can go in each body position
     * 
     * @param generatorOptions
     * @return
     */
    Map<BodyPosition, ArrayList<Clothing>> getClothesByBodyPosition(RandomGeneratorOptions generatorOptions) {
        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = new HashMap<>();

        List<Clothing> allClothes = clothingService.getAll().stream()
                .filter(c -> generatorOptions.getFancy().equals(c.getFancy())).collect(toList());

        for (BodyPosition bodyPosition : BodyPosition.getMandatoryBodyPositions()) {
            ArrayList<Clothing> clothesInThatBodyPosition = new ArrayList<>(allClothes.stream()
                    .filter(c -> c.getSector().getBodyPositions().contains(bodyPosition)).collect(Collectors.toList()));

            if (clothesInThatBodyPosition.isEmpty()) {
                throw new InvalidEntityException(Constants.generateMissingClothingForMessage(bodyPosition));
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
