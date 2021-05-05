package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BlackjackController {

    private final GameService gameService;

    public BlackjackController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start-game")
    public String startGame() {
        gameService.createNewGame();
        gameService.currentGame().initialDeal();
        return "redirect:/game";
    }

    @GetMapping("/game")
    public String viewGame(Model model) {
        GameView gameView = GameView.of(gameService.currentGame());;
        model.addAttribute("gameView", gameView);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hit() {
        gameService.currentGame().playerHits();
        if (gameService.currentGame().isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    @GetMapping("/done")
    public String done(Model model) {
        GameView gameView = GameView.of(gameService.currentGame());
        model.addAttribute("gameView", gameView);
        model.addAttribute("outcome", gameService.currentGame().determineOutcome().message());
        return "done";
    }
}
