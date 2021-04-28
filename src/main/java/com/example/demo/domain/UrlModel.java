package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "urls")
@Data
@NoArgsConstructor
public class UrlModel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "shortUrl", unique = true)
    private String shortUrl;
    @Column(name = "longUrl", unique = true)
    private String longUrl;
    @Column(name = "timeOfBirth", unique = true)
    private long timer;

}
