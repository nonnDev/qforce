package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonPropertyOrder({ "id", "name", "birth_year", "gender", "height", "weight", "movies" })
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonImpl implements Person {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("birth_year")
    private String birthYear;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("mass")
    private Integer weight;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "films")
    private List<String> filmsUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "url")
    private String personalUrl;

    private List<Movie> movies;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBirthYear() {
        return birthYear;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Integer getWeight() {
        return weight;
    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }

    public List<String> getFilmsUrl() {
        return filmsUrl;
    }

    public String getPersonalUrl() {
        return personalUrl;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
