package nl.qnh.qforce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nl.qnh.qforce.domain.PersonImpl;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonsResultDto {

    public PersonsResultDto() {}

    private List<PersonImpl> results;

    public List<PersonImpl> getResults() {
        return results;
    }
}
