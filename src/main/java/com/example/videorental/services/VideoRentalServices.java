package com.example.videorental.services;

import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.VideoMediaModel;
import com.example.videorental.repository.CustomerRepository;
import com.example.videorental.repository.VideoMediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoRentalServices {

    final CustomerRepository customerRepository;
    final VideoMediaRepository videoMediaRepository;

    public VideoRentalServices(CustomerRepository customerRepository, VideoMediaRepository videoMediaRepository) {
        this.customerRepository = customerRepository;
        this.videoMediaRepository = videoMediaRepository;
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

    public VideoMediaModel saveMedia(VideoMediaModel mediaModel) {
        return videoMediaRepository.save(mediaModel);
    }

    public List<VideoMediaModel> findAllMedias() {
        return videoMediaRepository.findAll();
    }

    public Optional<VideoMediaModel> findMediaById(UUID id) {
        return videoMediaRepository.findById(id);
    }

    public List<VideoMediaModel> findMediasByCustomerId(UUID id){
        return videoMediaRepository.findMediasByCustomerId(id);
    }

    public void deleteMedia(VideoMediaModel videoMediaModel) {
        videoMediaRepository.delete(videoMediaModel);
    }
}
