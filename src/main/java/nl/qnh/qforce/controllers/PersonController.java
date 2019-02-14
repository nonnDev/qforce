package nl.qnh.qforce.controllers;


import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Person> returnListOfStarWarsCharactors(@RequestParam String q) {

        LOGGER.debug("received query string: " + q);

        return personService.search(q);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Person> returnUniqueResult(@PathVariable int id) {
        LOGGER.debug("received id: " + id);

        Optional<Person> personOption = personService.get(id);
        return personOption.map(person ->
                new ResponseEntity<>(person, HttpStatus.OK)).
                orElseGet(() ->
                ResponseEntity.notFound().build());
    }
}
