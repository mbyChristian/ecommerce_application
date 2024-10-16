package org.site.ecommerce.ecommerce_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "panier",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<PanierItem> panierItems = new ArrayList<>();
    @Column(updatable=false)
    private LocalDate dateCreation;

    private double total;


    public void ajouterItem(Produit produit, int quantite) {
        PanierItem item = new PanierItem();
        item.setProduit(produit);
        item.setQuantite(quantite);
        item.setPanier(this);
        if (panierItems == null) {
            panierItems = new ArrayList<>();
        }
        this.panierItems.add(item);
    }

    public boolean retirerItem(Long produitId) {
        if (panierItems != null) {
            return panierItems.removeIf(item -> item.getProduit().getId().equals(produitId));
        }
        return false;
    }

    @PrePersist
    protected void onCreate(){
        this.dateCreation = LocalDate.now();
    }


    public void recalculerTotal() {
        total= panierItems.stream()
                .mapToDouble(item -> item.getProduit().getPrix() * item.getQuantite())
                .sum();
    }
}
