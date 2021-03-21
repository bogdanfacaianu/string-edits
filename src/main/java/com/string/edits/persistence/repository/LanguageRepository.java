package com.string.edits.persistence.repository;

import com.string.edits.domain.Language;
import java.util.List;
import java.util.Optional;

/**
 * Bucket management for Languages stored
 * The bucket key is represented by the language name
 * The body is stored as a json object converted to String
 */
public interface LanguageRepository {

    /**
     * This Saves a Language object to the bucket
     * @param language
     */
    void save(Language language);

    /**
     * Attempts to get a language by that name
     * and adds another word to its dictionary
     * @param languageName
     * @param word
     */
    void addWordToLanguage(String languageName, String word);

    /**
     * Deletes a language by name reference
     * @param languageName
     */
    void delete(String languageName);

    /**
     * Returns an Optional object of the language found
     * in the repository or an Optional.empty()
     * @param name
     * @return
     */
    Optional<Language> findLanguage(String name);

    /**
     * Removes a word in a language if found by
     * the language name reference
     * @param languageName
     * @param word
     */
    void removeWordFromLanguage(String languageName, String word);

    /**
     * Returns a list of all names of languages stored in the bucket
     * @return
     */
    List<String> findAllLanguages();

    /**
     * Flushes the bucket clearing all languages stored so far
     */
    void deleteAll();
}