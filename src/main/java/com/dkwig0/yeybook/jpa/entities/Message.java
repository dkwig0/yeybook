package com.dkwig0.yeybook.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usr_id")
    @JsonIgnoreProperties(value = {"chatRooms", "messages"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    @JsonIgnoreProperties(value = {"users", "messages"})
    private ChatRoom chatRoom;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "text")
    private String text;


    public Message() {
    }

    public Message(User user, ChatRoom chatRoom, LocalDateTime date, String text) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.date = date;
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        if (this.text != "") {
            return text;
        } else {
            return null;
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", chatRoomId=" + chatRoom.getId() +
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}
