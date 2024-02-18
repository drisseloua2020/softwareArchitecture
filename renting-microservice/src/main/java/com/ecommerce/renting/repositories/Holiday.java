package com.ecommerce.cart.repository;

import com.ecommerce.cart.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, BigDecimal> {
}
