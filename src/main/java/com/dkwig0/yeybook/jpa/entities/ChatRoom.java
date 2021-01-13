package com.dkwig0.yeybook.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToMany(mappedBy = "chatRooms", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"chatRooms", "messages"})
    private List<User> users;

    @OneToMany(mappedBy = "chatRoom")
    @JsonIgnoreProperties(value = {"user", "chatRoom"})
    private List<Message> messages;

    private boolean anonymous;

    public ChatRoom() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getName(User currentUser) {
        return users.stream().filter(u -> !u.getUsername().equals(currentUser.getUsername()))
                .map(User::getUsername)
                .reduce((x,y) -> x + ", " + y).orElse("Myself");
    }
}
