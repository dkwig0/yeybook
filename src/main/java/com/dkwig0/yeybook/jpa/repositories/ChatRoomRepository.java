package com.dkwig0.yeybook.jpa.repositories;

import com.dkwig0.yeybook.jpa.entities.ChatRoom;
import com.dkwig0.yeybook.jpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Set<ChatRoom> findByUsersIn(List<User> users);

    Set<ChatRoom> findByUsersContains(User user);

}
