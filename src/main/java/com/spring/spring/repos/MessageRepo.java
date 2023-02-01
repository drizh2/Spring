package com.spring.spring.repos;

import com.spring.spring.domain.Message;
import com.spring.spring.domain.User;
import com.spring.spring.domain.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepo extends CrudRepository<Message, Integer> {

    @Query("SELECT new com.spring.spring.domain.dto.MessageDto(" +
            "m, " +
                    "COUNT(ml), " +
                    "SUM (CASE WHEN ml = :user THEN 1 ELSE 0 END) > 0 " +
                    ") " +
                    "FROM Message m LEFT JOIN m.likes ml " +
                    "GROUP BY m")
    Page<MessageDto> findAll(Pageable pageable, @Param("user") User author);

    @Query("SELECT new com.spring.spring.domain.dto.MessageDto(" +
            "m, " +
            "COUNT(ml), " +
            "SUM (CASE WHEN ml = :user THEN 1 ELSE 0 END) > 0 " +
            ") " +
            "FROM Message m LEFT JOIN m.likes ml " +
            "WHERE m.tag = :tag " +
            "GROUP BY m")
    Page<MessageDto> findByTag(String tag, Pageable pageable, @Param("user") User author);

    @Query("SELECT new com.spring.spring.domain.dto.MessageDto(" +
            "m, " +
            "COUNT(ml), " +
            "SUM (CASE WHEN ml = :user THEN 1 ELSE 0 END) > 0 " +
            ") " +
            "FROM Message m LEFT JOIN m.likes ml " +
            "WHERE m.author = :author " +
            "GROUP BY m")
    Page<MessageDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);
}
