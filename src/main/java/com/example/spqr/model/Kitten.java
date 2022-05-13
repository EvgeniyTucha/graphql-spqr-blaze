package com.example.spqr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Kitten extends Cat {

    private static final long serialVersionUID = -8774051439534087727L;

    @Column(name = "kitten_name")
    private String name;
    @Column(name = "kitten_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Cat cat;
}
