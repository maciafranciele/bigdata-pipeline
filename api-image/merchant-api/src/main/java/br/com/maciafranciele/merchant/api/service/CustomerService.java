package br.com.maciafranciele.merchant.api.service;

import br.com.maciafranciele.merchant.api.model.CustomerModel;
import br.com.maciafranciele.merchant.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;


}
