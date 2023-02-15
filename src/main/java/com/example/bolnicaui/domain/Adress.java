package com.example.bolnicaui.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "adress")
public class Adress {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private int number;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Adress() {
    }

    public Adress(String street, int number, City city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city=" + city.getName() +
                '}';
    }
}