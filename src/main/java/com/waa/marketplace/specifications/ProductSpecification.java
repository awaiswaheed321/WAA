package com.waa.marketplace.specifications;

import com.waa.marketplace.entites.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> filter(
            String name,
            Double priceMin,
            Double priceMax,
            Long categoryId,
            Long sellerId,
            String description,
            Boolean active,
            Integer stockAvailable) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            // Filter by name
            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            // Filter by price range
            if (priceMin != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
                        priceMin));
            }
            if (priceMax != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"),
                        priceMax));
            }

            // Filter by category ID
            if (categoryId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"),
                        categoryId));
            }

            // Filter by seller ID
            if (sellerId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("seller").get("id"),
                        sellerId));
            }

            // Filter by description
            if (description != null && !description.isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("description")),
                                "%" + description.toLowerCase() + "%"
                        )
                );
            }

            // Filter by active status
            if (active != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("active"), active));
            }

            // Filter by stock availability
            if (stockAvailable != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("stock"),
                        stockAvailable));
            }

            return predicate;
        };
    }
}
