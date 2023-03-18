package com.assignment.playermaker;


import com.assignment.playermaker.service.PlayerManagementService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlayerManagementServiceTest {


    @Test
    void PlayerManagementService_getTwoTopPlayersTest(){

        List<List<String>> input = Arrays.asList(
                Arrays.asList("Sharon", "Shalom", "Sharon", "Ronaldo"),
                Arrays.asList("Sharon", "Shalom", "Myke", "Ronaldo"),
                Arrays.asList("Yechiel", "Sivan","Messi", "Ronaldo"),
                Arrays.asList("Yechie", "Assaf" ,"Shalom", "Ronaldo")
        );

        PlayerManagementService playerManagementService = new PlayerManagementService();
        List<String> topPlayers = playerManagementService.getTopPlayers(
                input, 2);

        assertThat(topPlayers).containsExactly("Ronaldo", "Shalom");
    }

    @Test
    void PlayerManagementService_TopPlayersWithEmptyOrBlankValuesTest(){

        List<List<String>> input = Arrays.asList(
                Arrays.asList("Sharon", "", "Sharon", "Ronaldo"),
                Arrays.asList("Nike", "", "Myke", "Messi"),
                Arrays.asList(" ", "Shalom", "Sharon", "Ronaldo"),
                Arrays.asList("Sharon", "Shalom", " ", "Ronaldo"),
                Arrays.asList("Yechiel", "Sivan"," ", "Ronaldo"),
                Arrays.asList(),
                Arrays.asList("Shalom", "Assaf" ," ", "Ronaldo")
        );

        PlayerManagementService playerManagementService = new PlayerManagementService();
        List<String> topPlayers = playerManagementService.getTopPlayers(
                input, 2);

        assertThat(topPlayers).containsExactly("Ronaldo", "Shalom");
    }

    @Test
    void PlayerManagementService_notValidInputTest() {

        List<List<String>> input = Arrays.asList(
                Arrays.asList("Sharon", "Shalom", "Sharon", "Ronaldo"),
                Arrays.asList("Sharon", "Shalom", "Myke", "Ronaldo"),
                Arrays.asList("Yechiel", "Sivan","Messi", "Ronaldo"),
                Arrays.asList("Yechie", "Assaf" ,"Shalom", "Ronaldo")
        );

        assertThrows(RuntimeException.class, () -> new PlayerManagementService().getTopPlayers(input, -1));
    }
}
