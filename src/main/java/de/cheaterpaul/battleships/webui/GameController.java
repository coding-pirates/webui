package de.cheaterpaul.battleships.webui;

import de.cheaterpaul.battleships.logic.Game;
import de.cheaterpaul.battleships.server.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.Set;

@RestController
public class GameController {

    @Nonnull
    private final GameManager gameManager;

    @Autowired
    public GameController(@Nonnull GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping("/games")
    public Set<Game> clients() {
        return gameManager.getGames();
    }
}
