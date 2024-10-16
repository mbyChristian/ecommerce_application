package org.site.ecommerce.ecommerce_app.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.site.ecommerce.ecommerce_app.entity.enumeration.Role;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DiscriminatorValue("CLIENT_ENREGISTRE")
public class ClientEnregistre extends Client{
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}