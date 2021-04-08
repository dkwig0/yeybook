package com.dkwig0.yeybook.jpa.repositories;

import com.dkwig0.yeybook.jpa.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByUsernameLike(String username, Pageable page);
}
