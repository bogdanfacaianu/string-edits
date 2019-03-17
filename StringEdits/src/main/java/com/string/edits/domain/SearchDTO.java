package com.string.edits.domain;

import com.github.liblevenshtein.transducer.Algorithm;
import java.util.Objects;

public class SearchDTO {

    private String language;
    private String searchTerm;
    private Algorithm algorithm;
    private int maxDistance;

    public SearchDTO(String language, String searchTerm, Algorithm algorithm, int maxDistance) {
        this.language = language;
        this.searchTerm = searchTerm;
        this.algorithm = algorithm;
        this.maxDistance = maxDistance;
    }

    public SearchDTO(String language, String searchTerm, int maxDistance) {
        this.language = language;
        this.searchTerm = searchTerm;
        this.algorithm = Algorithm.STANDARD;
        this.maxDistance = maxDistance;
    }

    public SearchDTO(String language, String searchTerm) {
        this.language = language;
        this.searchTerm = searchTerm;
        this.algorithm = Algorithm.STANDARD;
        this.maxDistance = 5;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SearchDTO)) {
            return false;
        }
        SearchDTO searchDTO = (SearchDTO) o;
        return maxDistance == searchDTO.maxDistance &&
            Objects.equals(language, searchDTO.language) &&
            Objects.equals(searchTerm, searchDTO.searchTerm) &&
            algorithm == searchDTO.algorithm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, searchTerm, algorithm, maxDistance);
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
            "language='" + language + '\'' +
            ", searchTerm='" + searchTerm + '\'' +
            ", algorithm=" + algorithm +
            ", maxDistance=" + maxDistance +
            '}';
    }
}
