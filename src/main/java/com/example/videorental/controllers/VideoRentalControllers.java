package com.example.videorental.controllers;

import com.example.videorental.dto.CustomerDTO;
import com.example.videorental.dto.RentalDTO;
import com.example.videorental.dto.VideoMediaDTO;
import com.example.videorental.models.CustomerModel;
import com.example.videorental.models.VideoMediaModel;
import com.example.videorental.services.VideoRentalServices;
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

    final VideoRentalServices videoRentalServices;

    public VideoRentalControllers(VideoRentalServices videoRentalServices) {
        this.videoRentalServices = videoRentalServices;
    }

    //---------Methods for customer access--------------

    @PostMapping("/customers")
    public ResponseEntity<Object> saveCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        if(videoRentalServices.existsByCpf(customerDTO.getCpf())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer CPF already in use!");
        if(videoRentalServices.existsByEmail(customerDTO.getEmail())) return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail already in use!");
        if(videoRentalServices.existsByPhone(customerDTO.getPhone())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone already in use!");

        var customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerDTO, customerModel);
        customerModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(videoRentalServices.saveCustomer(customerModel));

    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> findAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.findAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> findCustomerById(@PathVariable (value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerById(id);

        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
    }

//    @GetMapping("/customers/{cpf}")   desta forma entra em conflito com requests GET usando Id
//    public ResponseEntity<Object> findCustomerByCpf(@PathVariable (value = "cpf") String cpf){
//        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerByCpf(cpf);
//
//        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer CPF not found!");
//        return ResponseEntity.status(HttpStatus.OK).body(customerModelOptional.get());
//    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable (value = "id") UUID id){
        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerById(id);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        videoRentalServices.deleteCustomer(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable (value = "id") UUID id, @RequestBody @Valid CustomerDTO customerDTO){
        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerById(id);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        var customerModel = customerModelOptional.get();
        BeanUtils.copyProperties(customerDTO, customerModel);
        customerModel.setId(customerModelOptional.get().getId());
        customerModel.setRegistrationDate(customerModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.saveCustomer(customerModel));
    }

    //---------Methods for video medias--------------

    @PostMapping("/medias")
    public ResponseEntity<Object> saveVideoMedia(@RequestBody @Valid VideoMediaDTO videoMediaDTO) {

        var mediaModel = new VideoMediaModel();
        BeanUtils.copyProperties(videoMediaDTO, mediaModel);
        mediaModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(videoRentalServices.saveMedia(mediaModel));

    }

    @GetMapping("/medias")
    public ResponseEntity<List<VideoMediaModel>> findAllMedias(){
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.findAllMedias());
    }

    @GetMapping("/medias/{id}")
    public ResponseEntity<Object> findMediaById(@PathVariable (value = "id") UUID id){
        Optional<VideoMediaModel> mediaModelOptional = videoRentalServices.findMediaById(id);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.findMediaById(id).get());
    }

    @DeleteMapping("/medias/{id}")
    public ResponseEntity<Object> deleteMediaById(@PathVariable (value = "id") UUID id){
        Optional<VideoMediaModel> mediaModelOptional = videoRentalServices.findMediaById(id);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        videoRentalServices.deleteMedia(mediaModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
    }

    @PutMapping("/medias/{id}")
    public ResponseEntity<Object> updateMedia(@PathVariable (value = "id") UUID id, @RequestBody @Valid VideoMediaDTO videoMediaDTO){
        Optional<VideoMediaModel> videoMediaModelOptional = videoRentalServices.findMediaById(id);
        if(!videoMediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");
        var videoMediaModel = videoMediaModelOptional.get();
        BeanUtils.copyProperties(videoMediaDTO, videoMediaModel);
        videoMediaModel.setId(videoMediaModelOptional.get().getId());
        videoMediaModel.setRegistrationDate(videoMediaModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.saveMedia(videoMediaModel));
    }

    //metodo para fazer o aluguel do filme por tal cliente
    @PutMapping("/rental/{id}")
    public ResponseEntity<Object> addMediaToCustomer(@PathVariable (value = "id") UUID customerId, @RequestBody RentalDTO rentalDTO){
        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerById(customerId);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");

        UUID idMedia = rentalDTO.getIdMedia();

        Optional<VideoMediaModel> mediaModelOptional = videoRentalServices.findMediaById(idMedia);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");

        if(videoRentalServices.findMediaById(idMedia).get().getCustomer() != null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media is in possession of another customer, return the media before renting!");

        mediaModelOptional.get().setCustomer(videoRentalServices.findCustomerById(customerId).get());
        customerModelOptional.get().setMedias(videoRentalServices.findMediaById(idMedia).get());
        videoRentalServices.saveCustomer(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.saveMedia(mediaModelOptional.get()));

    }

    //metodo para devolver o filme
    @PutMapping("/return/{id}")
    public ResponseEntity<Object> removeMediaFromCustomer(@PathVariable (value = "id") UUID customerId, @RequestBody RentalDTO rentalDTO){
        Optional<CustomerModel> customerModelOptional = videoRentalServices.findCustomerById(customerId);
        if(!customerModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer ID not found!");
        UUID idMedia = rentalDTO.getIdMedia();

        Optional<VideoMediaModel> mediaModelOptional = videoRentalServices.findMediaById(idMedia);
        if(!mediaModelOptional.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media ID not found!");

        if(videoRentalServices.findMediaById(idMedia).get().getCustomer() == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Media is not in possession of any customer!");

        customerModelOptional.get().deleteMedia(videoRentalServices.findMediaById(idMedia).get());
        mediaModelOptional.get().setCustomer(null);
        videoRentalServices.saveCustomer(customerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.saveMedia(mediaModelOptional.get()));
    }

    //metodo para buscar todas midias em posse de um cliente especifico
    @GetMapping("/rentalview/{id}")
    public ResponseEntity<Object> findMediasByCustomerId(@PathVariable (value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoRentalServices.findMediasByCustomerId(id));
    }
}
