package ch.studer.germanclimatedataanalyser.model.dto.helper;

import ch.studer.germanclimatedataanalyser.service.db.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BundeslandTest {

    private final static String EXISTING_BUNDESLAND = "existing_bundesland";

    private final static String NON_EXISTING_BUNDESLAND = "non_existing_bundesland";

    @Mock
    StationService stationService;

    @InjectMocks
    Bundesland bundesland = new Bundesland(EXISTING_BUNDESLAND);

    @InjectMocks
    Bundesland non_existing_bundesland = new Bundesland(NON_EXISTING_BUNDESLAND);

    @Test
    void exists() {
        //* Define Mock szenario
        when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
        Assertions.assertTrue(bundesland.exists());
    }

    @Test
    void not_exists() {
        //* Define Mock szenario
        when(stationService.bundeslandExists(NON_EXISTING_BUNDESLAND)).thenReturn(false);
        Assertions.assertFalse(non_existing_bundesland.exists());
    }

    @Test
    void proof() {
        //* Define Mock szenario
        when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
        Assertions.assertEquals("",bundesland.proof());

        //* Define Mock szenario
        when(stationService.bundeslandExists(NON_EXISTING_BUNDESLAND)).thenReturn(false);
        Assertions.assertEquals(NON_EXISTING_BUNDESLAND + " Bundesland doesn't exist!",non_existing_bundesland.proof());

    }
    @Test
    void getName(){

        // Proof if leading and trailing blanks are trimmed
        Assertions.assertTrue(bundesland.getName().contentEquals(EXISTING_BUNDESLAND));
        Assertions.assertTrue(non_existing_bundesland.getName().contentEquals(NON_EXISTING_BUNDESLAND));
    }
}
