package org.site.ecommerce.ecommerce_app.service;

import jakarta.transaction.Transactional;
import org.site.ecommerce.ecommerce_app.entity.Panier;
import org.site.ecommerce.ecommerce_app.entity.Produit;
import org.site.ecommerce.ecommerce_app.repository.PanierRepository;
import org.site.ecommerce.ecommerce_app.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanierService {
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Transactional
    public Panier ajouterProduitAuPanier(Long panierId, Long produitId, int quantite) {
        Panier panier = panierRepository.findById(panierId).orElse(new Panier());
        Produit produit = produitRepository.findById(produitId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        panier.ajouterItem(produit, quantite);
        return panierRepository.save(panier);
    }

    @Transactional
    public Panier retirerProduitDuPanier(Long panierId, Long produitId) {
        Panier panier = panierRepository.findById(panierId).orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        boolean removed = panier.retirerItem(produitId);
        if (!removed) {
            throw new RuntimeException("Produit non trouvé dans le panier");
        }
        return panierRepository.save(panier);
    }


    public Panier obtenirPanier(Long panierId) {
        Panier panier = panierRepository.findById(panierId).orElseThrow(() -> new RuntimeException("Panier non trouvé"));
        panier.recalculerTotal(); // Recalculer le total avant de retourner le panier
        return panier;
    }

    public double obtenirTotalPanier(Long panierId) {
        Panier panier = obtenirPanier(panierId);
        return panier.getTotal();
    }
}
