import com.ecommerce.cart.model.Checkout;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    @Test
    void testGetterAndSetter() {
        // Arrange
        BigDecimal checkoutId = BigDecimal.valueOf(1);
        String toolCode = "TOOL001";
        String rentalDayCount = "5";
        BigDecimal discountPercent = BigDecimal.TEN;
        Date checkoutDate = new Date();

        // Act
        Checkout checkout = new Checkout();
        checkout.setCheckoutID(checkoutId);
        checkout.setToolCode(toolCode);
        checkout.setRentalDayCount(rentalDayCount);
        checkout.setDiscountPercent(discountPercent);
        checkout.setCheckoutDate(checkoutDate);

        // Assert
        assertEquals(checkoutId, checkout.getCheckoutID());
        assertEquals(toolCode, checkout.getToolCode());
        assertEquals(rentalDayCount, checkout.getRentalDayCount());
        assertEquals(discountPercent, checkout.getDiscountPercent());
        assertEquals(checkoutDate, checkout.getCheckoutDate());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange
        Checkout checkout = new Checkout();

        // Act & Assert
        assertNotNull(checkout);
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        BigDecimal checkoutId = BigDecimal.valueOf(1);
        String toolCode = "TOOL001";
        String rentalDayCount = "5";
        BigDecimal discountPercent = BigDecimal.TEN;
        Date checkoutDate = new Date();

        // Act
        Checkout checkout = new Checkout(checkoutId, toolCode, rentalDayCount, discountPercent, checkoutDate);

        // Assert
        assertEquals(checkoutId, checkout.getCheckoutID());
        assertEquals(toolCode, checkout.getToolCode());
        assertEquals(rentalDayCount, checkout.getRentalDayCount());
        assertEquals(discountPercent, checkout.getDiscountPercent());
        assertEquals(checkoutDate, checkout.getCheckoutDate());
    }
}
