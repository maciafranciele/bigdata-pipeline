package br.com.maciafranciele.merchant.api.repository;

import br.com.maciafranciele.merchant.api.model.CustomerModel;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerModel, String> {

    List<CustomerModel> findAll();

    List<CustomerModel> findByCityAndState(String city, String state);



}
