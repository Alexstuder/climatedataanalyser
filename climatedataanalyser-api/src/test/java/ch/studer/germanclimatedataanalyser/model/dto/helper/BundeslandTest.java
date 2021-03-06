package ch.studer.germanclimatedataanalyser.model.dto.helper;

import ch.studer.germanclimatedataanalyser.service.db.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BundeslandTest {

    private final static String EXISTING_BUNDESLAND = "existing_bundesland";

    private final static String NON_EXISTING_BUNDESLAND = "non_existing_bundesland";

    @Mock
    StationService stationService;

    @InjectMocks
    Bundesland bundesland = new Bundesland();


    @InjectMocks
    Bundesland non_existing_bundesland = new Bundesland();

    @Test
    void exists() {
        //* Define Mock szenario
        when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
        bundesland.setName(EXISTING_BUNDESLAND);
        Assertions.assertTrue(bundesland.exists());
    }

    @Test
    void not_exists() {
        when(stationService.bundeslandExists(NON_EXISTING_BUNDESLAND)).thenReturn(false);
        non_existing_bundesland.setName(NON_EXISTING_BUNDESLAND);
        Assertions.assertFalse(non_existing_bundesland.exists());
    }

    @Test
    void proof() {
        when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
        bundesland.setName(EXISTING_BUNDESLAND);
        Assertions.assertEquals("", bundesland.proof());

        non_existing_bundesland.setName(NON_EXISTING_BUNDESLAND);
        when(stationService.bundeslandExists(NON_EXISTING_BUNDESLAND)).thenReturn(false);
        Assertions.assertEquals(NON_EXISTING_BUNDESLAND + " Bundesland doesn't exist!", non_existing_bundesland.proof());

    }

    @Test
    void getName() {
        // Proof if leading and trailing blanks are trimmed
        bundesland.setName(EXISTING_BUNDESLAND);
        non_existing_bundesland.setName(NON_EXISTING_BUNDESLAND);

        Assertions.assertTrue(bundesland.getName().contentEquals(EXISTING_BUNDESLAND));
        Assertions.assertTrue(non_existing_bundesland.getName().contentEquals(NON_EXISTING_BUNDESLAND));
    }
}
