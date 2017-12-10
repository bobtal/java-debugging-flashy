package com.teamtreehouse.flashy.controllers;

import com.teamtreehouse.flashy.domain.FlashCard;
import com.teamtreehouse.flashy.services.FlashCardService;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FlashCardController {

  private FlashCardService flashCardService;

  @Autowired
  public void setFlashCardService(FlashCardService flashCardService) {
    this.flashCardService = flashCardService;
  }

  private Map<Long, Long> getCardCounts(HttpServletRequest req) {
    Map<Long, Long> cardCounts = (Map<Long, Long>) req.getSession().getAttribute("cardCounts");
    if (cardCounts == null) {
      cardCounts = new HashMap<>();
      req.getSession().setAttribute("cardCounts", cardCounts);
    }
    return cardCounts;
  }

  @RequestMapping("/flashcard/next")
  public String showNextFlashCard(HttpServletRequest req) {
    Map<Long, Long> cardCounts = getCardCounts(req);
    FlashCard card = flashCardService.getNextFlashCardBasedOnViews(cardCounts);
    return "redirect:/flashcard/" + card.getId();
  }

  @RequestMapping("/flashcard/{id}")
  public String showFlashCard(HttpServletRequest req, @PathVariable Long id, Model model, Principal principal) {
    Map<Long, Long> cardCounts = getCardCounts(req);
    cardCounts.compute(id, (key, val) -> val == null ? 1 : val + 1);
    model.addAttribute("flashCard", flashCardService.getFlashCardById(id));
    model.addAttribute("viewCount", cardCounts.get(id));
    model.addAttribute("loggedInUsername", principal.getName());
    return "flashcard_show";
  }

  // This controller method triggers when the student didn't guess the card right (clicked no)
  // and we simply make it like it was not seen in this sequence by reducing the number of times
  // it was seen. This method also moves to the next card as if next was clicked afterwards.
  // The yes option simply initiates a http request to "/flashcard/next" as if next was clicked
  @RequestMapping(value = "/flashcard/no/{id}", method = RequestMethod.POST)
  public String cardNotGuessed(HttpServletRequest req, @PathVariable Long id, Model model, Principal principal) {
    Map<Long, Long> cardCounts = getCardCounts(req);
    // if value (for number of times seen) is null, it should be set to zero
    // otherwise we check to see if it's greater then zero, and only then reduce it by one
    // If it's not greater than zero, we simply keep it at zero to prevent negative values
    cardCounts.compute(id, (key, val) -> val == null ? 0 : val > 0 ? val - 1 : 0);
    model.addAttribute("flashCard", flashCardService.getFlashCardById(id));
    model.addAttribute("viewCount", cardCounts.get(id));
    model.addAttribute("loggedInUsername", principal.getName());

    // move to the next card as usual
    FlashCard card = flashCardService.getNextFlashCardBasedOnViews(cardCounts);
    return "redirect:/flashcard/" + card.getId();
  }

}
