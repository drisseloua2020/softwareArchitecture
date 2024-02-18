package com.ecommerce.cart.repository;

import com.ecommerce.cart.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, BigDecimal> {
}
