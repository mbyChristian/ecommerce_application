package org.site.ecommerce.ecommerce_app.service;

import org.site.ecommerce.ecommerce_app.entity.Produit;
import org.site.ecommerce.ecommerce_app.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImplmt implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    //methode pour creer un produit
    @Override
    public Produit create(Produit produit) {
        return produitRepository.save(produit);
    }

    //methode pour lister tous les produits
    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    //methode pour obtenir un produit avec son id
    @Override
    public Produit findById(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        return produit.orElse(null);
    }

    //methode pour effacer un produit
    @Override
    public void delete(Long id) {
        Optional<Produit> produit = produitRepository.findById(id);
        produit.ifPresent(value -> produitRepository.delete(value));
    }

    //methode pour mettre um produit a jour
    @Override
    public Produit update(Long id ,Produit produit) {
        Optional<Produit> produitOptional =produitRepository.findById(id);
        if (produitOptional.isPresent()) {
            return produitRepository.save(produit);
        }else return null;
    }
}
