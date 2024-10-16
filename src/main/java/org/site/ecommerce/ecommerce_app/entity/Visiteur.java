package org.site.ecommerce.ecommerce_app.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter
@Getter
@DiscriminatorValue("VISITEUR")
@Entity
public class Visiteur extends Client{
}
