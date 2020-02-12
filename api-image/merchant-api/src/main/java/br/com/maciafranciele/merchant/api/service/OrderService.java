package br.com.maciafranciele.merchant.api.service;

import br.com.maciafranciele.merchant.api.model.OrderModel;
import br.com.maciafranciele.merchant.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;


    public List<OrderModel> getAllOrders(){
        return this.repository.findAll();
    }
}
