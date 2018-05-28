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
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import vidias.virtualcloset.dto.SearchClosetOptions;
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
    
    @Autowired
    private ClosetService closetService;

    /**
     * Generate a random closet based in the given generation options
     * 
     * @param searchClosetOptions
     * @return
     */
    public Closet generateRandomCloset(SearchClosetOptions searchClosetOptions) {
        Closet closet = new Closet();
        closet.setClosetClothing(new ArrayList<>());

        Random random = new Random(new Date().getTime());

        Collection<BodyPosition> mandatoryBodyPositions = BodyPosition.getMandatoryBodyPositions();

        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = getClothesByBodyPosition(searchClosetOptions);

        Set<BodyPosition> occupiedBodyPositions = new HashSet<>();

        Map<BodyPosition, List<ClosetClothing>> occupiedClothesByBodyPosition = new HashMap<>();
        mandatoryBodyPositions.forEach(bp -> occupiedClothesByBodyPosition.put(bp, new ArrayList<ClosetClothing>()));

        int safeCounter = 0;
        while (occupiedBodyPositions.size() < mandatoryBodyPositions.size()) {

            for (BodyPosition bodyPosition : mandatoryBodyPositions) {
                ArrayList<Clothing> clothes = clothesByBodyPosition.get(bodyPosition);

                Clothing clothing = null;
                try {
                    clothing = getRandomClothing(random, clothes, occupiedBodyPositions, closet);
                } catch (RandomGeneratorException e) {
                    // try again next round
                    System.err.println(e);
                }

                if (clothing != null) {
                    int zIndex = 0;
                    for (BodyPosition bp : clothing.getSector().getBodyPositions()) {
                        if (occupiedClothesByBodyPosition.get(bp).size() > zIndex) {
                            zIndex = occupiedClothesByBodyPosition.get(bp).size();
                        }
                    }
                    
                    ClosetClothing closetClothing = new ClosetClothing(clothing, zIndex);
                    closet.getClosetClothing().add(closetClothing);
                    clothing.getSector().getBodyPositions().forEach(bp -> occupiedClothesByBodyPosition.get(bp).add(closetClothing));
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
     * @param closet
     * @return
     * @throws RandomGeneratorException
     */
    public abstract Clothing getRandomClothing(Random random, ArrayList<Clothing> clothes, Set<BodyPosition> occupiedBodyPositions, Closet alreadyChosenClothes);

    /**
     * Returns a map containing all body positions that are required to have clothes
     * on and the list of all clothes that can go in each body position
     * 
     * @param searchClosetOptions
     * @return
     */
    Map<BodyPosition, ArrayList<Clothing>> getClothesByBodyPosition(SearchClosetOptions searchClosetOptions) {
        Map<BodyPosition, ArrayList<Clothing>> clothesByBodyPosition = new HashMap<>();

        // if searchClosetOptions.closetId exists, gets only the clothes present in that closet.
        // otherwise, gets all the user's clothes.
        Stream<Clothing> allClothesStream;

        if (searchClosetOptions.getClosetId() != null) {
        	allClothesStream = closetService.getById(searchClosetOptions.getClosetId()).getClosetClothing().stream().map(cc -> cc.getClothing());
        } else {
        	allClothesStream = clothingService.getAll().stream();
        }

        List<Clothing> allClothes = allClothesStream.filter(c -> searchClosetOptions.getFancy().equals(c.getFancy())).collect(toList());

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
