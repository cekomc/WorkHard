package org.softuni.work.areas.businesPartners.repositories;

import org.softuni.work.areas.businesPartners.entities.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, String> {
  BusinessPartner findFirstByEmail(String email);
}
