package org.site.ecommerce.ecommerce_app.service;

import org.site.ecommerce.ecommerce_app.entity.Produit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProduitService {
    //methode pour creer un produit
    public Produit create(Produit produit);

    //methode pour obtenir tous les produits
    public List<Produit> findAll();

    //methode pour obtenir un produit avec son id
    public Produit findById(Long id);

    //methode pour effacer un produit
    public void delete(Long id);

    //methode pour mettre un produit a jour
    public Produit update(Long  id ,Produit produit);
}
