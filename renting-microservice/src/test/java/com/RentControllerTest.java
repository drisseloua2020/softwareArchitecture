import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.Checkout;
import com.ecommerce.cart.model.RentalAgreement;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentController.class)
class RentControllerTest {

    @Mock
    private ReactiveRedisTemplate<String, Cart> redisTemplate;

    @Mock
    private ReactiveValueOperations<String, Cart> cartOps;

    @InjectMocks
    private RentController rentController;

    @Mock
    private RentalAgreement mockRentalAgreement;

    @Mock
    private Checkout mockCheckout;

    @Test
    void testIndex() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ \"name\": \"Renting API\", \"version\": 1.0.0} "));
    }

    @Test
    void testCheckoutValid() throws Exception {
        when(redisTemplate.opsForValue()).thenReturn(cartOps);
        when(cartOps.get(anyString())).thenReturn(Mono.just(new Cart())); // You may need to adjust this based on your actual logic

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"rentalDayCount\": 3, \"discountPercent\": 10 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rentalDays").value(3))
                .andExpect(jsonPath("$.discountPercent").value(10));
    }

    @Test
    void testCheckoutInvalid() throws Exception {
        when(redisTemplate.opsForValue()).thenReturn(cartOps);
        when(cartOps.get(anyString())).thenReturn(Mono.just(new Cart())); // You may need to adjust this based on your actual logic

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"rentalDayCount\": 0, \"discountPercent\": 110 }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGenerateRentalAgreement() throws Exception {
        when(rentController.generateRentalAgreement(any())).thenReturn(mockRentalAgreement);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rentController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/generateRentalAgreement")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"rentalDayCount\": 3, \"discountPercent\": 10 }"))
                .andExpect(status().isOk());
    }
}
