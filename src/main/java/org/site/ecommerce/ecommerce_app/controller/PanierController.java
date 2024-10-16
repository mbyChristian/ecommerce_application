package org.site.ecommerce.ecommerce_app.controller;

import org.site.ecommerce.ecommerce_app.entity.Panier;
import org.site.ecommerce.ecommerce_app.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ecommerce/panier")
public class PanierController {
    @Autowired
    private PanierService panierService;

    @PostMapping("/{panierId}/ajouter")
    public ResponseEntity<?> ajouterProduitAuPanier(@PathVariable Long panierId, @RequestParam Long produitId, @RequestParam int quantite) {
        try {
            Panier panier = panierService.ajouterProduitAuPanier(panierId, produitId, quantite);
            return ResponseEntity.ok(panier);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{panierId}/retirer")
    public ResponseEntity<?> retirerProduitDuPanier(@PathVariable Long panierId, @RequestParam Long produitId) {
        try {
            Panier panier = panierService.retirerProduitDuPanier(panierId, produitId);
            return ResponseEntity.ok(panier);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @GetMapping("/{panierId}")
    public ResponseEntity<?> obtenirPanier(@PathVariable Long panierId) {
        try {
            Panier panier = panierService.obtenirPanier(panierId);
            return ResponseEntity.ok(panier);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Panier non trouvé");
        }
    }

    @GetMapping("/{panierId}/total")
    public ResponseEntity<Double> obtenirTotalPanier(@PathVariable Long panierId) {
        double total = panierService.obtenirTotalPanier(panierId);
        return ResponseEntity.ok(total);
    }
}
