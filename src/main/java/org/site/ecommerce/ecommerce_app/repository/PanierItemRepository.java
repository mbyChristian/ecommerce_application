package org.site.ecommerce.ecommerce_app.repository;

import org.site.ecommerce.ecommerce_app.entity.PanierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierItemRepository extends JpaRepository<PanierItem, Long> {
}
