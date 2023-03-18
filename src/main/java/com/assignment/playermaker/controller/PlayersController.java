package com.assignment.playermaker.controller;

import com.assignment.playermaker.dto.RequestDataDto;
import com.assignment.playermaker.dto.ResponseDataDto;
import com.assignment.playermaker.service.PlayerManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.assignment.playermaker.errors.BadRequestException;


@RestController
@RequestMapping(value="/players")
public class PlayersController {

    private final PlayerManagementService playerManagementService;

    public PlayersController(PlayerManagementService playerManagementService) {
        this.playerManagementService = playerManagementService;
    }

    @RequestMapping(value="/top", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTopPlayers(@RequestBody RequestDataDto input){

        inputValidation(input);

        List<String> topPlayers = playerManagementService.getTopPlayers(input.getParticipatedPlayers(), input.getRequiredTopPlayers());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(ResponseDataDto.builder()
                        .mostParticipatedPlayers(topPlayers)
                        .build());
    }

    private void inputValidation(RequestDataDto input){

        if(input.getRequiredTopPlayers() == null || input.getRequiredTopPlayers() <= 0){
            throw new BadRequestException("Input value of requiredTopPlayers should be positive");
        }

        if(input.getParticipatedPlayers() == null || input.getParticipatedPlayers().size() == 0){
            throw new BadRequestException("Input value of participatedPlayers can not be empty or null");
        }
    }
}
