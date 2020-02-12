package br.com.maciafranciele.merchant.api.controller;

import br.com.maciafranciele.merchant.api.model.OrderModel;
import br.com.maciafranciele.merchant.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping(method = RequestMethod.GET, produces =  {MediaType.APPLICATION_JSON_VALUE })
    public List<OrderModel> getAllOrderWithoutFilter(){
        return service.getAllOrders();
    }
}
