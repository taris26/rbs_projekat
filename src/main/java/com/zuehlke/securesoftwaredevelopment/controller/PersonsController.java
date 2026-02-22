package com.zuehlke.securesoftwaredevelopment.controller;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.config.SecurityUtil;
import com.zuehlke.securesoftwaredevelopment.domain.Person;
import com.zuehlke.securesoftwaredevelopment.domain.User;
import com.zuehlke.securesoftwaredevelopment.repository.PersonRepository;
import com.zuehlke.securesoftwaredevelopment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.SQLException;
import java.util.List;

@Controller

public class PersonsController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonsController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(PersonRepository.class);

    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public PersonsController(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/persons/{id}")
    @PreAuthorize("hasAuthority('VIEW_PERSON')")
    public String person(@PathVariable int id, Model model) {
        LOG.info("Viewing person with id={}", id);
        auditLogger.audit("Viewed person details for personId=" + id);
        model.addAttribute("person", personRepository.get("" + id));
        model.addAttribute("username", userRepository.findUsername(id));
        return "person";
    }

    @GetMapping("/myprofile")
    @PreAuthorize("hasAuthority('VIEW_MY_PROFILE')")
    public String self(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("person", personRepository.get("" + user.getId()));
        model.addAttribute("username", userRepository.findUsername(user.getId()));
        return "person";
    }

    @DeleteMapping("/persons/{id}")
    @PreAuthorize("hasAuthority('VIEW_PERSON')")
    public ResponseEntity<Void> person(@PathVariable int id) {
        LOG.warn("Deleting person and user with id={}", id);
        auditLogger.audit("Deleting person and user with id=" + id);
        personRepository.delete(id);
        userRepository.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-person")
    @PreAuthorize("hasAuthority('UPDATE_PERSON')")
    public String updatePerson(Person person, String username) {
        User currentUser = SecurityUtil.getCurrentUser();
        int personId = Integer.parseInt(person.getId());

        // MANAGER and CUSTOMER can only update their own data
        if (!SecurityUtil.hasPermission("VIEW_PERSON") && currentUser.getId() != personId) {
            LOG.warn("Unauthorized profile update attempt: userId={} tried to update personId={}", currentUser.getId(), personId);
            throw new AccessDeniedException("You can only update your own profile");
        }

        LOG.info("Updating person with id={} by userId={}", personId, currentUser.getId());
        auditLogger.audit("Updated person id=" + personId + " (username=" + username + ")");
        personRepository.update(person);
        userRepository.updateUsername(personId, username);

        // Non-admin users redirect to myprofile instead of /persons/{id}
        if (!SecurityUtil.hasPermission("VIEW_PERSON")) {
            return "redirect:/myprofile";
        }
        return "redirect:/persons/" + person.getId();
    }

    @GetMapping("/persons")
    @PreAuthorize("hasAuthority('VIEW_PERSONS_LIST')")
    public String persons(Model model) {
        model.addAttribute("persons", personRepository.getAll());
        return "persons";
    }

    @GetMapping(value = "/persons/search", produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('VIEW_PERSONS_LIST')")
    public List<Person> searchPersons(@RequestParam String searchTerm) throws SQLException {
        LOG.info("Person search with searchTerm='{}'", searchTerm);
        auditLogger.audit("Searched persons with searchTerm=" + searchTerm);
        return personRepository.search(searchTerm);
    }
}
