package vidias.virtualcloset.service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import vidias.virtualcloset.exception.RandomGeneratorException;
import vidias.virtualcloset.helper.Constants;
import vidias.virtualcloset.model.BodyPosition;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.model.Clothing;

@Service
public class OverlapRandomClosetService extends RandomClosetService {
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
    @Override
    public Clothing getRandomClothing(Random random, ArrayList<Clothing> clothes,
            Set<BodyPosition> occupiedBodyPositions, Closet areadyChosenClothes) throws RandomGeneratorException {
        IntStream randomInts = random.ints(0, clothes.size());
        int index = randomInts.limit(MAX_TRIES).filter(i -> fit(clothes.get(i), occupiedBodyPositions)).findAny()
                .orElseThrow(() -> new RandomGeneratorException(Constants.SOMETHING_WRONG_RANDOM_MESSAGE));
        return clothes.get(index);
    }
}
