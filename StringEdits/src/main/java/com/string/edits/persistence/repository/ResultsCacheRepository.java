package com.string.edits.persistence.repository;

import com.string.edits.domain.SearchDTO;
import com.string.edits.domain.TermQuery;
import java.util.List;

public interface ResultsCacheRepository {

    void save(SearchDTO searchDTO, TermQuery termQuery);

    TermQuery findResult(SearchDTO searchDTO);

    void removeEntry(SearchDTO searchDTO);

    void deleteAll();
}
