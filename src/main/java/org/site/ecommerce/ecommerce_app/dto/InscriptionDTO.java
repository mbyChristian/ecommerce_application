package org.site.ecommerce.ecommerce_app.dto;

public record InscriptionDTO(
        String prenom,
        String nom,
        String tel,
        String email,
        String password
) {
}
