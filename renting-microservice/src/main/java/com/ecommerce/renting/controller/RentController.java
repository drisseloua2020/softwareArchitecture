import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.Checkout;
import com.ecommerce.cart.model.RentalAgreement;

import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
public class RentController {

    private static final Logger LOG = LoggerFactory.getLogger(RentController.class);

    private final ReactiveRedisTemplate<String, Cart> redisTemplate;
    private final ReactiveValueOperations<String, Cart> cartOps;

    public RentController(ReactiveRedisTemplate<String, Cart> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.cartOps = this.redisTemplate.opsForValue();
    }

    @RequestMapping("/")
    public String index() {
        return "{ \"name\": \"Renting API\", \"version\": 1.0.0} ";
    }

    @PostMapping("/checkout")
    public Mono<ResponseEntity<?>> checkout(@RequestBody Checkout checkout) {
        try {
            validateCheckout(checkout);
            RentalAgreement rentalAgreement = generateRentalAgreement(checkout);
            LOG.info("Generated Rental Agreement: {}", rentalAgreement);
            return Mono.just(ResponseEntity.ok(rentalAgreement));
        } catch (IllegalArgumentException e) {
            return Mono.just(ResponseEntity.badRequest().body(e.getMessage()));
        }
    }

    private void validateCheckout(Checkout checkout) {
        if (checkout.getRentalDayCount() < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }

        if (checkout.getDiscountPercent() < 0 || checkout.getDiscountPercent() > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range of 0 to 100.");
        }
    }
    @PostMapping("/generateRentalAgreement")
    private RentalAgreement generateRentalAgreement(Checkout checkout) {

        RentalAgreement rentalAgreement = new RentalAgreement();
        // Set attributes based on Checkout and perform calculations...
        rentalAgreement.printAsTextToConsole(); // Print the values to console
        return rentalAgreement;
    }
}
