package com.string.edits.persistence.repository;

import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import java.util.Optional;

/**
 * Stores results found based on
 * a key which represents the hashcode value of
 * languageName, search term, algorithm and maximum distance
 */
public interface ResultsCacheRepository {

    /**
     * Saves a result in the cache entry
     * @param searchDTO
     * @param termQuery
     */
    void save(SearchDTO searchDTO, TermQuery termQuery);

    /**
     * Retrieves a result if hashcode matches
     * the one from the search term
     * @param searchDTO
     * @return
     */
    Optional<TermQuery> findResult(SearchDTO searchDTO);

    /**
     * Removes a cache entry if it exists
     * @param searchDTO
     */
    void removeEntry(SearchDTO searchDTO);

    /**
     * Flushes the bucket with all cache stored
     */
    void deleteAll();
}
