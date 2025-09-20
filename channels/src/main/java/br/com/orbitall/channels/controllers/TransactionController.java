package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionOutput> create(@Valid @RequestBody TransactionInput input) {
        TransactionOutput output = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    public ResponseEntity<List<TransactionOutput>> findTransactions(@RequestParam(required = false) UUID customerId) {
        List<TransactionOutput> list;

        if (customerId != null) {
            list = service.findByCustomerId(customerId);
        } else {
            list = service.findAll();
        }

        return ResponseEntity.ok(list);
    }
}