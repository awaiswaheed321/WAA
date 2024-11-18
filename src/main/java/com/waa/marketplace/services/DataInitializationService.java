package com.waa.marketplace.services;

import com.waa.marketplace.entites.*;
import com.waa.marketplace.enums.OrderStatus;
import com.waa.marketplace.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DataInitializationService(
            BuyerRepository buyerRepository,
            SellerRepository sellerRepository,
            ProductRepository productRepository, AdminRepository adminRepository, OrderRepository orderRepository, ReviewRepository reviewRepository, CategoryRepository categoryRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initData() {
        // Initialize Admin
        Admin admin = new Admin();
        admin.setName("System Admin");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123"); // Encrypt in production
        adminRepository.save(admin);


        // Initialize Buyers
        Buyer buyer1 = new Buyer();
        buyer1.setName("John Doe");
        buyer1.setEmail("john.doe@example.com");
        buyer1.setPassword("password123"); // Encrypt in production
        buyerRepository.save(buyer1);

        Buyer buyer2 = new Buyer();
        buyer2.setName("Jane Smith");
        buyer2.setEmail("jane.smith@example.com");
        buyer2.setPassword("password123");
        buyerRepository.save(buyer2);

        // Initialize Sellers
        Seller seller1 = new Seller();
        seller1.setName("ACME Electronics");
        seller1.setEmail("acme@example.com");
        seller1.setPassword("password123");
        sellerRepository.save(seller1);

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
        order1.setBuyer(buyer1); // Assuming buyer1 is already initialized
        order1.setProduct(product1); // Assuming product1 is already initialized
        order1.setQuantity(2);
        order1.setTotalPrice(product1.getPrice() * 2); // Calculate total price
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
        review1.setBuyer(buyer1); // Assuming buyer1 is already initialized
        review1.setProduct(product1); // Assuming product1 is already initialized
        review1.setContent("Great product! Highly recommended.");
        review1.setRating(5);
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setBuyer(buyer2);
        review2.setProduct(product2);
        review2.setContent("Good quality, but a bit pricey.");
        review2.setRating(4);
        reviewRepository.save(review2);

        System.out.println("Initial data loaded successfully!");
    }
}
