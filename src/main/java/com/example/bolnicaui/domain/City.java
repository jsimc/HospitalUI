package com.example.bolnicaui.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "postal_code")
    private String postalCode;

    public City() {
    }

    public City(String name, String country, String postalCode) {
        this.name = name;
        this.country = country;
        this.postalCode = postalCode;
    }
}
