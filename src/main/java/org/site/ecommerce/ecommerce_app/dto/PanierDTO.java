package org.site.ecommerce.ecommerce_app.dto;

import org.site.ecommerce.ecommerce_app.entity.PanierItem;

import java.util.List;

public record PanierDTO(
        Long id,
        List<PanierItem> panierItems
) {
}
