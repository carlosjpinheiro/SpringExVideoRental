package com.example.videorental.services;

import com.example.videorental.enums.StatusEmail;
import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.EmailModel;
import com.example.videorental.models.VideoMediaModel;
import com.example.videorental.repository.CustomerRepository;
import com.example.videorental.repository.EmailRepository;
import com.example.videorental.repository.VideoMediaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoRentalServices {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;
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

    public EmailModel sendEmail(UUID customerId, UUID mediaId) {
        EmailModel emailModel = new EmailModel();
        CustomerModel customer = findCustomerById(customerId).get();
        VideoMediaModel media = findMediaById(mediaId).get();
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("locadora@hotmail.com");
            message.setTo(customer.getEmail());
            message.setSubject("New Rental");
            message.setText("Hello "+customer.getName()+" the movie "+media.getName()+" was rented in "+ LocalDate.now()+"\nThank you for the preference and the confidence.");

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {

            return emailRepository.save(emailModel);
        }
    }


}
