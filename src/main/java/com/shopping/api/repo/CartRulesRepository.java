package com.shopping.api.repo;

import com.shopping.api.entity.CartRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRulesRepository extends JpaRepository<CartRuleEntity, Long> {
}