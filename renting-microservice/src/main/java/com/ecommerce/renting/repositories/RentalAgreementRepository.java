package com.ecommerce.cart.repository;

import com.ecommerce.cart.model.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, BigDecimal> {
}
