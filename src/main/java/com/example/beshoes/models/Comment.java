package com.example.beshoes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private String name;

    private long userId;

    private long productId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId" ,  insertable = false ,updatable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productId",  insertable = false ,updatable = false)
    private Product product;
}
