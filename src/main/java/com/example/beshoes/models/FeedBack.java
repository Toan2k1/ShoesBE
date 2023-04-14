package com.example.beshoes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Feedback")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    private String name;
    private long userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId" ,  insertable = false ,updatable = false)
    private User user;
}
