package com.waa.marketplace.services;

import com.waa.marketplace.entites.*;
import com.waa.marketplace.enums.OrderStatus;
import com.waa.marketplace.enums.Role;
import com.waa.marketplace.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {

    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializationService(
            BuyerRepository buyerRepository,
            SellerRepository sellerRepository,
            ProductRepository productRepository, AdminRepository adminRepository, OrderRepository orderRepository, ReviewRepository reviewRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initData() {
        // Initialize Admin
        User adminUser = new User();
        adminUser.setName("System Admin");
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setRole(Role.ADMIN.name());

        Admin admin = new Admin();
        admin.setUser(adminUser);
        adminRepository.save(admin);

        // Initialize Buyers
        User buyerUser1 = new User();
        buyerUser1.setName("John Doe");
        buyerUser1.setEmail("john.doe@example.com");
        buyerUser1.setPassword(passwordEncoder.encode("john123"));
        buyerUser1.setRole(Role.BUYER.name());

        Buyer buyer1 = new Buyer();
        buyer1.setUser(buyerUser1);
        buyer1.setShippingAddress("123 Main St");
        buyer1.setBillingAddress("456 Elm St");
        buyerRepository.save(buyer1);

        User buyerUser2 = new User();
        buyerUser2.setName("Jane Smith");
        buyerUser2.setEmail("jane.smith@example.com");
        buyerUser2.setPassword(passwordEncoder.encode("jane123"));
        buyerUser2.setRole(Role.BUYER.name());

        Buyer buyer2 = new Buyer();
        buyer2.setUser(buyerUser2);
        buyer2.setShippingAddress("789 Oak St");
        buyer2.setBillingAddress("101 Pine Ave");
        buyerRepository.save(buyer2);

        // Initialize Sellers
        User sellerUser1 = new User();
        sellerUser1.setName("Alice Johnson");
        sellerUser1.setEmail("alice.johnson@example.com");
        sellerUser1.setPassword(passwordEncoder.encode("alice123"));
        sellerUser1.setRole(Role.SELLER.name());

        Seller seller1 = new Seller();
        seller1.setUser(sellerUser1);
        seller1.setApproved(true);
        sellerRepository.save(seller1);

        User sellerUser2 = new User();
        sellerUser2.setName("Bob Brown");
        sellerUser2.setEmail("bob.brown@example.com");
        sellerUser2.setPassword(passwordEncoder.encode("bob123"));
        sellerUser2.setRole(Role.SELLER.name());

        Seller seller2 = new Seller();
        seller2.setUser(sellerUser2);
        seller2.setApproved(false);
        sellerRepository.save(seller2);


        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Devices and gadgets");
        categoryRepository.save(electronics);

        // Initialize Products
        Product product1 = new Product();
        product1.setName("Smartphone");
        product1.setDescription("Latest model with advanced features");
        product1.setPrice(699.99);
        product1.setStock(50);
        product1.setSeller(seller1);
        product1.setCategory(electronics);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Laptop");
        product2.setDescription("High-performance laptop for professionals");
        product2.setPrice(1299.99);
        product2.setStock(30);
        product2.setSeller(seller1);
        product2.setCategory(electronics);
        productRepository.save(product2);

        // Initialize Orders
        Order order1 = new Order();
        order1.setBuyer(buyer1);
        order1.setProduct(product1);
        order1.setQuantity(2);
        order1.setTotalPrice(product1.getPrice() * 2);
        order1.setStatus(OrderStatus.PENDING);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setBuyer(buyer2);
        order2.setProduct(product2);
        order2.setQuantity(1);
        order2.setTotalPrice(product2.getPrice());
        order2.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order2);

        // Initialize Reviews
        Review review1 = new Review();
        review1.setBuyer(buyer1);
        review1.setProduct(product1);
        review1.setComment("Great product! Highly recommended.");
        review1.setRating(5);
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setBuyer(buyer2);
        review2.setProduct(product2);
        review2.setComment("Good quality, but a bit pricey.");
        review2.setRating(4);
        reviewRepository.save(review2);

        System.out.println("Initial data loaded successfully!");
    }
}
