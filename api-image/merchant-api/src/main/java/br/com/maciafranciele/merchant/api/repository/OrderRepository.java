package br.com.maciafranciele.merchant.api.repository;

import br.com.maciafranciele.merchant.api.model.OrderModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderModel, String> {

    List<OrderModel> findAll();
}
