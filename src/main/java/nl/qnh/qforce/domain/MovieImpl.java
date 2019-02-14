package nl.qnh.qforce.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class MovieImpl implements Movie {

    @JsonProperty("title")
    private String title;
    @JsonProperty("episode_id")
    private Integer episode;
    @JsonProperty("director")
    private String director;
    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getEpisode() {
        return episode;
    }

    @Override
    public String getDirector() {
        return director;
    }

    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
