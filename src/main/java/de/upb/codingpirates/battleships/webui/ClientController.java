package de.upb.codingpirates.battleships.webui;

import de.upb.codingpirates.battleships.logic.AbstractClient;
import de.upb.codingpirates.battleships.server.ClientManager;
import de.upb.codingpirates.battleships.server.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    @Nonnull
    private final ClientManager clientManager;
    @Nonnull
    private final GameManager gameManager;

    @Autowired
    public ClientController(@Nonnull ClientManager clientManager, @Nonnull GameManager gameManager) {
        this.clientManager = clientManager;
        this.gameManager = gameManager;
    }

    @GetMapping("/clients")
    public List<AbstractClient> clients() {
        return new ArrayList<>(clientManager.getPlayerMappings().values());
    }
}
