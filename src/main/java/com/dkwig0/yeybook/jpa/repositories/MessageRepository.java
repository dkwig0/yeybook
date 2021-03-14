package com.dkwig0.yeybook.jpa.repositories;

import com.dkwig0.yeybook.jpa.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findByChatRoomIdOrderByDateDesc(Long id, Pageable page);

}
