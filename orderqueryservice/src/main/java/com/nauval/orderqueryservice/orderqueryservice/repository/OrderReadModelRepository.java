package com.nauval.orderqueryservice.orderqueryservice.repository;

import com.nauval.orderqueryservice.orderqueryservice.model.OrderReadModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderReadModelRepository extends MongoRepository<OrderReadModel, Long> {
}