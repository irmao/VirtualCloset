package vidias.virtualcloset.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vidias.virtualcloset.exception.InvalidClosetException;
import vidias.virtualcloset.exception.NotFoundException;
import vidias.virtualcloset.helper.Constants;
import vidias.virtualcloset.helper.TimeFunctions;
import vidias.virtualcloset.model.BodyPosition;
import vidias.virtualcloset.model.Closet;
import vidias.virtualcloset.model.ClosetClothing;
import vidias.virtualcloset.model.Clothing;
import vidias.virtualcloset.model.Sector;
import vidias.virtualcloset.repository.ClosetRepository;

@Service
@Transactional
public class ClosetService {

    @Autowired
    private ClosetRepository closetRepository;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private UserService userService;

    public Collection<Closet> getAll() {
        return closetRepository.findByUserId(userService.getCurrentUser().getId());
    }
    
    public Closet save(Closet closet) {
        validate(closet);

        closet.setUser(userService.getCurrentUser());
        
        if (closet.getId() == null) {
            closet.setStartDate(TimeFunctions.localDateToDate(LocalDate.now()));
        }
        
        closet.getClosetClothing().forEach(c -> c.setCloset(closet));

        return closetRepository.save(closet);
    }

    public void delete(Long closetId) {
        Closet closet = closetRepository.findOne(closetId);
        
        if (closet == null) {
            throw new NotFoundException(Constants.OBJECT_NOT_FOUND_MESSAGE);
        }
        
        closetRepository.delete(closet);
    }

    public void validate(Closet closet) {
        // check if all non-optional sectors are filled
        if (!allNonOptionalSectorsAllFilled(closet)) {
            throw new InvalidClosetException(Constants.NON_OPTIONAL_SECTORS_MISSING_MESSAGE);
        }

        // check if there is no clothing on top a a clothing that is marked
        // as 'topMost'. But only checks if the closet doesn't allow any overlapping
        if (!closet.getBodyPositionOverlap() && isThereForbiddenOverlap(closet)) {
            throw new InvalidClosetException(Constants.FORBIDDEN_CLOTHES_OVERLAP_MESSAGE);
        }
    }

    /**
     * Informs whether all non optional sectors are filled in a closet or not
     * 
     * @param closet
     * @return
     */
    boolean allNonOptionalSectorsAllFilled(Closet closet) {
        Stream<Long> nonOptionalSectorsIds = sectorService.getAllNonOptional().stream().map(Sector::getId);

        Set<Long> idsOfAllSectorsUsedInCloset = closet.getClosetClothing().stream().map(ClosetClothing::getClothing)
                .map(Clothing::getSector).map(Sector::getId).collect(toSet());

        return !nonOptionalSectorsIds.filter(nonOptId -> !idsOfAllSectorsUsedInCloset.contains(nonOptId)).findAny()
                .isPresent();
    }

    /**
     * Informs whether there is a clothing on top of another when this other is set
     * as topMost
     * 
     * @param closet
     * @return
     */
    boolean isThereForbiddenOverlap(Closet closet) {
        List<BodyPosition> allBodyPositions = Arrays.asList(BodyPosition.values());

        for (BodyPosition bodyPosition : allBodyPositions) {
            List<ClosetClothing> ccsInBodyPosition = closet.getClosetClothing().stream()
                    .filter(cc -> cc.getClothing().getSector().getBodyPositions().contains(bodyPosition))
                    .sorted(new ClosetClothingComparator()).collect(toList());

            // list is sorted in descending order. If there is a topMost element that is not
            // the first one, the list is invalid.
            boolean isListInvalid = ccsInBodyPosition.stream().skip(1)
                    .anyMatch(cc -> cc.getClothing().getSector().getTopMost());

            if (isListInvalid) {
                return true;
            }
        }

        return false;
    }

    /**
     * Sorts ClosetClothing instances by Z Index in descending order
     * 
     * @author vdias
     *
     */

    private class ClosetClothingComparator implements Comparator<ClosetClothing> {
        @Override
        public int compare(ClosetClothing o1, ClosetClothing o2) {
            return o2.getzIndex().compareTo(o1.getzIndex());
        }

    }

    public void deleteAllClosetsUsingClothing(Clothing clothing) {
        Collection<Closet> closets = closetRepository.findByClothingId(clothing.getId());
        closetRepository.delete(closets);
    }

}
