package com.assignment.playermaker;

import com.assignment.playermaker.controller.PlayersController;
import com.assignment.playermaker.dto.ErrorDto;
import com.assignment.playermaker.dto.RequestDataDto;
import com.assignment.playermaker.dto.ResponseDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PlayermakerApplicationTests {

		@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
    @Autowired
    private PlayersController controller;


	@Test
	public void contextLoads() {
        assertThat(controller).isNotNull();
	}


	@Test
	public void PlayermakerApplicationTests_getTwoTopPlayers(){

		List<List<String>> input = Arrays.asList(
				Arrays.asList("Sharon", "Shalom", "Sharon", "Ronaldo"),
				Arrays.asList("Sharon", "Shalom", "Myke", "Ronaldo"),
				Arrays.asList("Yechiel", "Sivan","Messi", "Ronaldo"),
				Arrays.asList("Yechie", "Assaf" ,"Shalom", "Ronaldo")
		);

		RequestDataDto requestDataDto = RequestDataDto.builder().build();

		requestDataDto.setParticipatedPlayers(input);
		requestDataDto.setRequiredTopPlayers(2);

		ResponseDataDto response = this.restTemplate.postForObject("http://localhost:" + port + "/players/top", requestDataDto , ResponseDataDto.class);

		assertThat(response.getMostParticipatedPlayers()).containsExactly("Ronaldo", "Shalom");

	}

	@Test
	public void PlayermakerApplicationTests_TopPlayersWithEmptyOrBlankValuesTest(){
        List<List<String>> input = Arrays.asList(
                Arrays.asList("Sharon", "", "Sharon", "Ronaldo"),
                Arrays.asList("Nike", "", "Myke", "Messi"),
                Arrays.asList(" ", "Shalom", "Sharon", "Ronaldo"),
                Arrays.asList("Sharon", "Shalom", " ", "Ronaldo"),
                Arrays.asList("Yechiel", "Sivan"," ", "Ronaldo"),
                Arrays.asList(),
                Arrays.asList("Shalom", "Assaf" ," ", "Ronaldo")
        );

		RequestDataDto requestDataDto = RequestDataDto.builder().build();

		requestDataDto.setParticipatedPlayers(input);
		requestDataDto.setRequiredTopPlayers(2);

		ResponseDataDto response = this.restTemplate.postForObject("http://localhost:" + port + "/players/top", requestDataDto , ResponseDataDto.class);

		assertThat(response.getMostParticipatedPlayers()).containsExactly("Ronaldo", "Shalom");

	}

    @Test
    void PlayerManagementService_notValidInputTest() {

        List<List<String>> input = Arrays.asList(
                Arrays.asList("Sharon", "Shalom", "Sharon", "Ronaldo"),
                Arrays.asList("Sharon", "Shalom", "Myke", "Ronaldo"),
                Arrays.asList("Yechiel", "Sivan","Messi", "Ronaldo"),
                Arrays.asList("Yechie", "Assaf" ,"Shalom", "Ronaldo")
        );

		RequestDataDto requestDataDto = RequestDataDto.builder().build();

		requestDataDto.setParticipatedPlayers(input);
		requestDataDto.setRequiredTopPlayers(-1);

		ErrorDto response = this.restTemplate.postForObject("http://localhost:" + port + "/players/top", requestDataDto , ErrorDto.class);

		assertThat(response.getError()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
       }

}
