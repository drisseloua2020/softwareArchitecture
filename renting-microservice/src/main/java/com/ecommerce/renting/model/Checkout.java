package com.ecommerce.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CheckoutID")
    private BigDecimal checkoutID;

    @Column(name = "ToolCode")
    private String toolCode;

    @Column(name = "RentalDayCount")
    private String rentalDayCount;

    @Column(name = "DiscountPercent")
    private BigDecimal discountPercent;

    @Column(name = "CheckoutDate")
    private Date checkoutDate;
}