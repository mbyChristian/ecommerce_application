package org.site.ecommerce.ecommerce_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private double prix;
    @Column(nullable = false)
    private double stock;

    public Produit(String nom, String description, String imageUrl, double prix, int stock) {
        this.nom = nom;
        this.description = description;
        this.imageUrl = imageUrl;
        this.prix = prix;
        this.stock = stock;

    }

    public Produit() {

    }
}
