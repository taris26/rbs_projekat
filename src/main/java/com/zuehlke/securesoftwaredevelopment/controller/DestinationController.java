package com.zuehlke.securesoftwaredevelopment.controller;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.*;
import com.zuehlke.securesoftwaredevelopment.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class DestinationController {

    private static final Logger LOG = LoggerFactory.getLogger(DestinationController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(DestinationController.class);

    private DestinationRepository destinationRepository;
    private RatingRepository ratingRepository;
    private PersonRepository userRepository;

    public DestinationController(DestinationRepository destinationRepository, RatingRepository ratingRepository, PersonRepository userRepository) {
        this.destinationRepository = destinationRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showSearch(Model model) {
        model.addAttribute("destinations", destinationRepository.getAll());
        return "destinations";
    }

    @GetMapping("/destinations/create-form")
    public String CreateForm(Model model) {
        return "new-destination";
    }

    @PostMapping("new-destination")
    public String CreateDestination(Destination destination) throws SQLException {
        Long id = destinationRepository.create(destination);
        return "redirect:/destinations?id=" + id;
    }

    @GetMapping(value = "/api/destinations/search", produces = "application/json")
    @ResponseBody
    public List<Destination> search(@RequestParam("query") String query) throws SQLException {
        return destinationRepository.search(query);
    }

    @GetMapping("/destinations")
    public String showDestinations(@RequestParam(name = "id", required = false) String id, Model model, Authentication authentication) {
        if (id == null) {
            model.addAttribute("destinations", destinationRepository.getAll());
            return "destinations";
        }
        User user = (User) authentication.getPrincipal();

        List<Rating> ratings = ratingRepository.getAll(id);
        Optional<Rating> userRating = ratings.stream().filter(rating -> rating.getUserId() == user.getId()).findFirst();
        userRating.ifPresent(rating -> model.addAttribute("userRating", rating.getRating()));
        if (!ratings.isEmpty()) {
            Integer sumRating = ratings.stream().map(rating -> rating.getRating()).reduce(0, (total, rating) -> total + rating);
            Double avgRating = (double) sumRating / ratings.size();
            model.addAttribute("averageRating", avgRating);
        }

        model.addAttribute("destination", destinationRepository.get(Integer.valueOf(id)));

        return "destination";
    }
}
