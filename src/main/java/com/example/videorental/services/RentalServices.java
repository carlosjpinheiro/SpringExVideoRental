package com.example.videorental.services;

import com.example.videorental.enums.StatusEmail;
import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.EmailModel;
import com.example.videorental.models.MediaModel;
import com.example.videorental.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

}
