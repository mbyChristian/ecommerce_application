package org.site.ecommerce.ecommerce_app.repository;

import org.site.ecommerce.ecommerce_app.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findByEmail(String email);
}
