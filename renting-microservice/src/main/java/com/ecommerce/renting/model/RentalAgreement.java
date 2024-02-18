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
@Table(name = "RentalAgreement")
public class RentalAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalAgreementId")
    private BigDecimal rentalAgreementId;

    @Column(name = "DueDate")
    private Date dueDate;

    @Column(name = "ChargeDays")
    private BigDecimal chargeDays;

    @Column(name = "PreDiscountCharge")
    private BigDecimal preDiscountCharge;

    @Column(name = "DiscountAmount")
    private BigDecimal discountAmount;

    @Column(name = "FinalCharge")
    private BigDecimal finalCharge;

    @Column(name = "CheckoutID")
    private BigDecimal checkoutID;

    @Column(name = "ToolCode")
    private String toolCode;

    @ManyToOne
    @JoinColumn(name = "CheckoutID", referencedColumnName = "CheckoutID", insertable = false, updatable = false)
    private Checkout checkout;

    @ManyToOne
    @JoinColumn(name = "ToolCode", referencedColumnName = "ToolCode", insertable = false, updatable = false)
    private Tool tool;

    public void printAsTextToConsole() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
        DecimalFormat percentFormat = new DecimalFormat("##%");

        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        // Other attributes...

        System.out.println("Date: " + dateFormat.format(dueDate));
        System.out.println("Currency: " + currencyFormat.format(finalCharge));
        System.out.println("Percent: " + percentFormat.format(discountPercent));
    }
}