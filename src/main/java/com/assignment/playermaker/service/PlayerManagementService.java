package com.assignment.playermaker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerManagementService implements IPlayerManagementService{

    public List<String> getTopPlayers(List<List<String>> input, int numPlayers) {
        return input.stream()
                .map(list -> new HashSet<>(list))
                .flatMap(sett -> sett.stream())
                .filter(v -> !v.isBlank())
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(v -> v.getKey()).limit(numPlayers).collect(Collectors.toList());
    }
}
