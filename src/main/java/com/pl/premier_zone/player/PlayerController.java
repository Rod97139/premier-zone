package com.pl.premier_zone.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayers(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String pos,
            @RequestParam(required = false) String nation
    ) {
        if (team != null && pos != null) {
            return playerService.getPlayersByTeamAndPosition(team, pos);
        }
        if (team != null) {
            return playerService.getPlayersByTeam(team);
        }
        if (name != null) {
            return playerService.getPlayersByName(name);
        }
        if (pos != null) {
            return playerService.getPlayersByPos(pos);
        }
        if (nation != null) {
            return playerService.getPlayersByNation(nation);
        }
        return playerService.getPlayers();
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Player updatedPlayer = playerService.updatePlayer(player);
        if (updatedPlayer != null) {
            return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@RequestParam String playerName) {
        playerService.deletePlayer(playerName);
        return new ResponseEntity<>("Player deleted successfully", HttpStatus.OK);
    }

}
