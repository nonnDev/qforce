package nl.qnh.qforce.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.qnh.qforce.domain.Person;
import nl.qnh.qforce.service.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
    private static final String SWAPI_SEARCH = "https://swapi.co/api/people";
    private static final String SWAPI_FIND = "https://swapi.co/api/people/";

    private RestTemplate mockTemplate = mock(RestTemplate.class);
    private ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
    private ResponseEntity<Object> response = mock(ResponseEntity.class);
    private PersonServiceImpl sut = new PersonServiceImpl(mockTemplate, mockObjectMapper);

    private HttpEntity entity;

    @Before
    public void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", USER_AGENT);
        entity = new HttpEntity<>("parameters", headers);
    }

    // no person found
    @Test
    public void noValidPersonFound() {
        when(mockTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> eq(HttpMethod.GET), Mockito.<HttpEntity<?>> any(), Mockito.<Class<Object>> any())).thenReturn(response);
        List<Person> people = sut.search("11111111");

        assertEquals(0, people.size());
        verify(mockTemplate).exchange(SWAPI_SEARCH, HttpMethod.GET, entity, String.class);
    }

    // fina a person
    @Test
    public void validPersonFound() {
        when(mockTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod> eq(HttpMethod.GET), Mockito.<HttpEntity<?>> any(), Mockito.<Class<Object>> any())).thenReturn(response);
        Optional<Person> people = sut.get(1);
        people.ifPresent(person -> assertEquals(1, person.getId()));
        verify(mockTemplate).exchange(SWAPI_FIND+1+"/", HttpMethod.GET, entity, String.class);
    }

}
