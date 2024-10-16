package org.site.ecommerce.ecommerce_app.repository;

import org.site.ecommerce.ecommerce_app.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
