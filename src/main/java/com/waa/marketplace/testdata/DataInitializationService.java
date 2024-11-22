package com.waa.marketplace.testdata;

import com.waa.marketplace.entites.*;
import com.waa.marketplace.enums.OrderStatus;
import com.waa.marketplace.enums.Role;
import com.waa.marketplace.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DataInitializationService {

    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Autowired
    public DataInitializationService(
            BuyerRepository buyerRepository,
            SellerRepository sellerRepository,
            ProductRepository productRepository,
            AdminRepository adminRepository,
            OrderRepository orderRepository,
            ReviewRepository reviewRepository,
            CategoryRepository categoryRepository,
            PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    @PostConstruct
    public void initData() {
        initializeAdmin();
        initializeBuyers();
        initializeSellers();
        Category electronics = initializeCategories();
        initializeProducts(electronics);
        initializeOrders();
        initializeReviews();
        System.out.println("Initial data loaded successfully!");
    }

    private void initializeAdmin() {
        User adminUser = createUser("System", "Admin", "admin@example.com", "admin123", Role.ADMIN);
        Admin admin = new Admin();
        admin.setUser(adminUser);
        adminRepository.save(admin);
    }

    private void initializeBuyers() {
        // Buyer 1
        Buyer buyer1 = createBuyer("John", "Doe", "john.doe@example.com", "john123");
        buyer1.getAddresses().add(createAddress(buyer1, "123 Main St", "Shipping City", "Shipping State", "12345",
                "USA"));
        buyer1.getAddresses().add(createAddress(buyer1, "456 Elm St", "Billing City", "Billing State", "12345", "USA"
        ));
        buyerRepository.save(buyer1);

        // Buyer 2
        Buyer buyer2 = createBuyer("Jane", "Smith", "jane.smith@example.com", "jane123");
        buyer2.getAddresses().add(createAddress(buyer2, "789 Oak St", "Shipping City 2", "Shipping State 2", "09876",
                "USA"));
        buyer2.getAddresses().add(createAddress(buyer2, "101 Pine Ave", "Billing City 2", "Billing State 2", "54321",
                "USA"));
        buyerRepository.save(buyer2);
    }

    private void initializeSellers() {
        // Seller 1
        Seller seller1 = createSeller("Alice", "Johnson", "alice.johnson@example.com", "alice123", true);
        sellerRepository.save(seller1);

        // Seller 2
        Seller seller2 = createSeller("Bob", "Brown", "bob.brown@example.com", "bob123", false);
        sellerRepository.save(seller2);
    }

    private Category initializeCategories() {
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Devices and gadgets");
        categoryRepository.save(electronics);
        return electronics;
    }

    private void initializeProducts(Category category) {
        Seller seller1 = sellerRepository.findAll().get(0);
        productRepository.save(createProduct("Smartphone", "Latest model with advanced features", 699.99, 50, seller1
                , category, "https://waa-project-bucket.s3.us-east-2.amazonaws.com/phone.webp", "phone.webp"));
        productRepository.save(createProduct("Laptop", "High-performance laptop for professionals", 1299.99, 30,
                seller1, category, "https://waa-project-bucket.s3.us-east-2.amazonaws.com/laptop.webp","laptop.webp"));
    }

    private void initializeOrders() {
        List<Buyer> buyers = buyerRepository.findAll();
        List<Product> products = productRepository.findAll();
        Buyer buyer1 = buyers.get(0);
        Buyer buyer2 = buyers.get(1);
        Product product1 = products.get(0);
        Product product2 = products.get(1);

        orderRepository.save(createOrder(buyer1, product1, 2, OrderStatus.PENDING));
        orderRepository.save(createOrder(buyer2, product1, 2, OrderStatus.PENDING));
        orderRepository.save(createOrder(buyer1, product1, 2, OrderStatus.PENDING));
        orderRepository.save(createOrder(buyer2, product1, 2, OrderStatus.PENDING));
        orderRepository.save(createOrder(buyer1, product2, 1, OrderStatus.SHIPPED));
        orderRepository.save(createOrder(buyer1, product1, 1, OrderStatus.SHIPPED));
        orderRepository.save(createOrder(buyer2, product2, 1, OrderStatus.SHIPPED));
        orderRepository.save(createOrder(buyer2, product1, 1, OrderStatus.SHIPPED));
    }

    private void initializeReviews() {
        List<Buyer> buyers = buyerRepository.findAll();
        List<Product> products = productRepository.findAll();
        Buyer buyer1 = buyers.get(0);
        Buyer buyer2 = buyers.get(1);
        Product product1 = products.get(0);
        Product product2 = products.get(1);

        reviewRepository.save(createReview(buyer1, product1, 6L,"Great product! Highly recommended.", 5));
        reviewRepository.save(createReview(buyer2, product2, 7L, "Good quality, but a bit pricey.", 4));
    }

    private User createUser(String firstName, String lastName, String email, String password, Role role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.name());
        return user;
    }

    private Buyer createBuyer(String firstName, String lastName, String email, String password) {
        User buyerUser = createUser(firstName, lastName, email, password, Role.BUYER);
        Buyer buyer = new Buyer();
        buyer.setUser(buyerUser);
        Cart cart = new Cart();
        cart.setBuyer(buyer);
        buyer.setCart(cart);
        return buyer;
    }

    private Seller createSeller(String firstName, String lastName, String email, String password, boolean approved) {
        User sellerUser = createUser(firstName, lastName, email, password, Role.SELLER);
        Seller seller = new Seller();
        seller.setUser(sellerUser);
        seller.setApproved(approved);
        return seller;
    }

    private Address createAddress(Buyer buyer, String street, String city, String state, String zip, String country) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zip);
        address.setCountry(country);
        address.setBuyer(buyer);
        return address;
    }

    private Product createProduct(String name, String description, double price, int stock, Seller seller,
                                  Category category, String imageUrl, String imageName) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setSeller(seller);
        product.setCategory(category);
        Image image = Image.builder().imageUrl(imageUrl).contentType("image/webp")
                .name(imageName).product(product).build();
        product.setImages(List.of(image));
        return product;
    }

    private Order createOrder(Buyer buyer, Product product, int quantity, OrderStatus status) {
        Order order = new Order();
        order.setBuyer(buyer);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setStatus(status);

        List<Address> addresses = addressRepository.findByBuyerId(buyer.getId());
        Address billingAddress = addresses.get(0);
        Address shippingAddress = addresses.get(1);

        if (billingAddress != null) {
            order.setBillingAddress(billingAddress);
        } else {
            throw new IllegalStateException("No billing address found for buyer: " + buyer.getUser().getEmail());
        }
        if (shippingAddress != null) {
            order.setShippingAddress(shippingAddress);
        } else {
            throw new IllegalStateException("No shipping address found for buyer: " + buyer.getUser().getEmail());
        }

        return order;
    }

    private Review createReview(Buyer buyer, Product product, Long orderId, String comment, int rating) {
        Review review = new Review();
        review.setBuyer(buyer);
        review.setProduct(product);
        review.setComment(comment);
        review.setRating(rating);
        review.setOrderId(orderId);
        return review;
    }
}
