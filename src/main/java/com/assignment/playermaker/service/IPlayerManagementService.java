package com.assignment.playermaker.service;

import java.util.List;

public interface IPlayerManagementService {

    public List<String> getTopPlayers(List<List<String>> inputData, int numPlayers);
}
