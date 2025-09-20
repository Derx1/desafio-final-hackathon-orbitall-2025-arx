package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerOutput> create(@Valid @RequestBody CustomerInput input) {
        CustomerOutput output = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOutput> retrieve(@PathVariable UUID id) {
        CustomerOutput output = service.retrieve(id);
        return ResponseEntity.ok(output);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutput> update(@PathVariable UUID id, @Valid @RequestBody CustomerInput input) {
        CustomerOutput output = service.update(id, input);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerOutput> delete(@PathVariable UUID id) {
        CustomerOutput output = service.delete(id);
        return ResponseEntity.ok(output);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutput>> findAll() {
        List<CustomerOutput> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}