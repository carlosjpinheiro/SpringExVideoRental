package com.example.videorental.controllers;

import com.example.videorental.dto.CustomerDTO;
import com.example.videorental.dto.RentalDTO;
import com.example.videorental.dto.MediaDTO;
import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.MediaModel;
import com.example.videorental.services.CustomerServices;
import com.example.videorental.services.MediaServices;
import com.example.videorental.services.RentalServices;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class VideoRentalControllers {

    final RentalServices rentalServices;
    final CustomerServices customerServices;
    final MediaServices mediaServices;

    public VideoRentalControllers(RentalServices rentalServices, CustomerServices customerServices, MediaServices mediaServices) {
        this.rentalServices = rentalServices;
        this.customerServices = customerServices;
        this.mediaServices = mediaServices;
    }

    //---------Methods for customer access--------------

    @PostMapping("/customers")
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        if(customerServices.existsByCpf(customerDTO.getCpf())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer CPF already in use!");
        if(customerServices.existsByEmail(customerDTO.getEmail())) return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail already in use!");
        if(customerServices.existsByPhone(customerDTO.getPhone())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone already in use!");

        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerDTO, customerModel);
        customerModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerServices.saveCustomer(customerModel));

    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> findAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerServices.findAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> findCustomerById(@PathVariable (value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional = customerServices.findCustomerById(id);

        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
    }

    //  desta forma entra em conflito com requests GET usando Id
//    @GetMapping("/customers/{cpf}")
//    public ResponseEntity<Object> findCustomerByCpf(@PathVariable (value = "cpf") String cpf){
//        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerByCpf(cpf);
//
//        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer CPF not found!");
//        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
//    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable (value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional = customerServices.findCustomerById(id);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        customerServices.deleteCustomer(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable (value = "id") UUID id, @RequestBody @Valid CustomerDTO customerDTO){
        Optional<CustomerModel> customerModelOptional = customerServices.findCustomerById(id);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        var customerModel = customerModelOptional.get();
        BeanUtils.copyProperties(customerDTO, customerModel);
        customerModel.setId(customerModelOptional.get().getId());
        customerModel.setRegistrationDate(customerModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(customerServices.saveCustomer(customerModel));
    }

    //---------Methods for video medias--------------

    @PostMapping("/medias")
    public ResponseEntity<Object> saveVideoMedia(@RequestBody @Valid MediaDTO mediaDTO) {

        var mediaModel = new MediaModel();
        BeanUtils.copyProperties(mediaDTO, mediaModel);
        mediaModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaServices.saveMedia(mediaModel));

    }

    @GetMapping("/medias")
    public ResponseEntity<List<MediaModel>> findAllMedias(){
        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.findAllMedias());
    }

    @GetMapping("/medias/{id}")
    public ResponseEntity<Object> findMediaById(@PathVariable (value = "id") UUID id){
        Optional<MediaModel> mediaModelOptional = mediaServices.findMediaById(id);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.findMediaById(id).get());
    }

    @DeleteMapping("/medias/{id}")
    public ResponseEntity<Object> deleteMediaById(@PathVariable (value = "id") UUID id){
        Optional<MediaModel> mediaModelOptional = mediaServices.findMediaById(id);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        mediaServices.deleteMedia(mediaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/medias/{id}")
    public ResponseEntity<Object> updateMedia(@PathVariable (value = "id") UUID id, @RequestBody @Valid MediaDTO mediaDTO){
        Optional<MediaModel> videoMediaModelOptional = mediaServices.findMediaById(id);
        if(!videoMediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        var videoMediaModel = videoMediaModelOptional.get();
        BeanUtils.copyProperties(mediaDTO, videoMediaModel);
        videoMediaModel.setId(videoMediaModelOptional.get().getId());
        videoMediaModel.setRegistrationDate(videoMediaModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.saveMedia(videoMediaModel));
    }

    //metodo para fazer o aluguel do filme por tal cliente
    @PutMapping("/rental/{id}")
    public ResponseEntity<Object> addMediaToCustomer(@PathVariable (value = "id") UUID customerId, @RequestBody RentalDTO rentalDTO){
        Optional<CustomerModel> customerModelOptional = customerServices.findCustomerById(customerId);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");

        UUID mediaId = rentalDTO.getIdMedia();

        Optional<MediaModel> mediaModelOptional = mediaServices.findMediaById(mediaId);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");

        if(mediaServices.findMediaById(mediaId).get().getCustomer() != null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media is in possession of another customer, return the media before renting!");

        mediaModelOptional.get().setCustomer(customerServices.findCustomerById(customerId).get());

        rentalServices.sendEmailRental(customerId, mediaId);

        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.saveMedia(mediaModelOptional.get()));

    }

    //metodo para devolver o filme
    @PutMapping("/return/{id}")
    public ResponseEntity<Object> removeMediaFromCustomer(@PathVariable (value = "id") UUID mediaId){
        Optional<MediaModel> mediaModelOptional = mediaServices.findMediaById(mediaId);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");

        if(mediaServices.findMediaById(mediaId).get().getCustomer() == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media is not in possession of any customer!");

        rentalServices.sendEmailReturn(mediaServices.findMediaById(mediaId).get().getCustomer().getId(), mediaId);

        mediaModelOptional.get().setCustomer(null);
        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.saveMedia(mediaModelOptional.get()));
    }

    //metodo para buscar todas midias em posse de um cliente especifico
    @GetMapping("/rentalview/{id}")
    public ResponseEntity<Object> findMediasByCustomerId(@PathVariable (value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(mediaServices.findMediasByCustomerId(id));
    }

}
