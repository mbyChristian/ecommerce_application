package org.site.ecommerce.ecommerce_app.repository;

import org.site.ecommerce.ecommerce_app.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier,Long> {
}
