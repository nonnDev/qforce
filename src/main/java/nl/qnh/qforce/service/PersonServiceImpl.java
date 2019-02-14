package nl.qnh.qforce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.qnh.qforce.domain.Movie;
import nl.qnh.qforce.domain.MovieImpl;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.domain.PersonImpl;
import nl.qnh.qforce.dto.PersonsResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);
    private static final String REGEX = "/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static final String SWAPI_SEARCH = "https://swapi.co/api/people";
    private static final String SWAPI_FIND = "https://swapi.co/api/people/";

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public PersonServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Person> search(String query) {
        return getPeople(query);
    }

    @Override
    public Optional<Person> get(long id) {
        return getPerson(id);
    }

    /**
     * Responsible to return a list of people by name
     * @param query name search string
     * @return list of people
     */
    private List<Person> getPeople(final String query) {

        ResponseEntity<String> response = restTemplate.exchange(SWAPI_SEARCH, HttpMethod.GET, getStringHttpEntity(), String.class);
        List<Person> list = new ArrayList<>();

        PersonsResultDto people;
        try {
            people = objectMapper.readValue(response.getBody(), PersonsResultDto.class);

            if (people == null) {
                return new ArrayList<>();
            }

            for (PersonImpl result : people.getResults()) {

                //only return result names that contains the query string
                if (query != null && !result.getName().contains(query)) {
                    continue;
                }

                //need to split the URL to identify the ID field of the person
                String[] pairs = result.getPersonalUrl().split(REGEX);
                result.setId(Long.parseLong(pairs[pairs.length-1]));

                result.setMovies(loadMovies(result.getFilmsUrl()));

                list.add(result);

            }

            return list;

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            return new ArrayList<>();
        }
    }

    /**
     * Responsible to return a single Person object from the SWAPI url
     * @param id person id
     * @return person object
     */
    private Optional<Person> getPerson(final long id) {

        ResponseEntity<String> response = restTemplate.exchange(SWAPI_FIND+id+"/", HttpMethod.GET, getStringHttpEntity(), String.class);
        PersonImpl person;

        try {
            person = objectMapper.readValue(response.getBody(), PersonImpl.class);

            if (person == null) {
                return Optional.empty();
            }

            //need to split the URL to identify the ID field of the person
            String[] pairs = person.getPersonalUrl().split(REGEX);
            person.setId(Long.parseLong(pairs[pairs.length-1]));

            person.setMovies(loadMovies(person.getFilmsUrl()));

            return Optional.of(person);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e.getCause());
            return Optional.empty();
        }
    }

    /**
     * Responsible to load list of movies per url
     * @param filmUrls a list of url related to movies
     * @return a list of movies
     */
    private List<Movie> loadMovies(List<String> filmUrls) {
        List<Movie> movies = new ArrayList<>();

        for (String filmUrl : filmUrls) {
            ResponseEntity<MovieImpl> movieResponse = restTemplate.exchange(
                    filmUrl,
                    HttpMethod.GET,
                    getStringHttpEntity(),
                    MovieImpl.class);

            movies.add(movieResponse.getBody());
        }
        return movies;
    }

    /**
     * Build the correct HttpEntity to make calls to external HTTPS API
     * @return authorised HttpEntity
     */
    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", USER_AGENT);
        return new HttpEntity<>("parameters", headers);
    }
}

