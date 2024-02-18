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
@Table(name = "Holiday")
public class Holiday {

    @Id
    @Column(name = "HolidayCode")
    private String holidayCode;

    @Column(name = "Description")
    private String description;
}