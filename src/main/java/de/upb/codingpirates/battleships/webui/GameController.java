package de.upb.codingpirates.battleships.webui;

import de.upb.codingpirates.battleships.logic.Configuration;
import de.upb.codingpirates.battleships.logic.Game;
import de.upb.codingpirates.battleships.server.GameManager;
import de.upb.codingpirates.battleships.server.game.GameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class GameController {

    @Nonnull
    private final GameManager gameManager;

    @Autowired
    public GameController(@Nonnull GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping("/games")
    public Collection<Game> clients() {
        return gameManager.getGameHandlers().stream().map(GameHandler::getGame).collect(Collectors.toList());
    }

    @GetMapping("/createGame")
    public Game createGame(@RequestParam(value = "name", required = true) String name) {
        return this.gameManager.createGame(Configuration.DEFAULT, name).getGame();
    }
}
