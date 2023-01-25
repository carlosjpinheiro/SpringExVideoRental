package com.example.videorental.services;

import com.example.videorental.models.CustomerModel;
import com.example.videorental.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServices {

    final CustomerRepository customerRepository;

    public CustomerServices(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerModel saveCustomer(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }

    public boolean existsByCpf(String cpf) {
        return customerRepository.existsByCpf(cpf);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    public List<CustomerModel> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerModel> findCustomerByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }

    @Transactional
    public void deleteCustomer(CustomerModel customerModel) {
        customerRepository.delete(customerModel);
    }


}
