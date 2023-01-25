package com.example.videorental.services;

import com.example.videorental.enums.StatusEmail;
import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.EmailModel;
import com.example.videorental.models.MediaModel;
import com.example.videorental.models.RentalModel;
import com.example.videorental.repository.EmailRepository;
import com.example.videorental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalServices {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    CustomerServices customerServices;
    @Autowired
    MediaServices mediaServices;

    final RentalRepository rentalRepository;

    public RentalServices(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public EmailModel sendEmailRental(UUID customerId, UUID mediaId) {
        EmailModel emailModel = new EmailModel();
        CustomerModel customer = customerServices.findCustomerById(customerId).get();
        MediaModel media = mediaServices.findMediaById(mediaId).get();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("locadora@hotmail.com");
            message.setTo(customer.getEmail());
            message.setSubject("New Rental");
            message.setText("Hello "+customer.getName()+" the movie "+media.getName()+" was rented in "+ LocalDateTime.now().format(formatter)+"\nThank you for the preference and the confidence.");

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {

            return emailRepository.save(emailModel);
        }
    }

    public EmailModel sendEmailReturn(UUID customerId, UUID mediaId) {
        EmailModel emailModel = new EmailModel();
        CustomerModel customer = customerServices.findCustomerById(customerId).get();
        MediaModel media = mediaServices.findMediaById(mediaId).get();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("locadora@hotmail.com");
            message.setTo(customer.getEmail());
            message.setSubject("Movie devolution");
            message.setText("Hello "+customer.getName()+" the movie "+media.getName()+" was returned to our store in "+ LocalDateTime.now().format(formatter)+"\nThank you for the preference and the confidence.");

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {

            return emailRepository.save(emailModel);
        }

    }

    public RentalModel saveRental(UUID customerId, UUID mediaId) {
        var rentalModel = new RentalModel();
        rentalModel.setCustomer(customerServices.findCustomerById(customerId).get());
        rentalModel.setMedia(mediaServices.findMediaById(mediaId).get());
        rentalModel.setRentalDate(LocalDateTime.now(ZoneId.of("UTC")));
        return rentalRepository.save(rentalModel);

    }

    public RentalModel returnRental(UUID mediaId) {
        var rentalModel = this.findRentalByMediaId(mediaId).get();
        rentalModel.setReturnDate(LocalDateTime.now(ZoneId.of("UTC")));
        return rentalRepository.save(rentalModel);
    }

    public Optional<RentalModel> findRentalByMediaId(UUID mediaId) {
        return rentalRepository.findRentalByMediaId(mediaId);
    }
}
