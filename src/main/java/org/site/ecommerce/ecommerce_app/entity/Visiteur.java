package org.site.ecommerce.ecommerce_app.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@DiscriminatorValue("VISITEUR")
@Entity
public class Visiteur extends Client{
}
