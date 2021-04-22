package com.example.account.web;

import com.example.account.exception.AccountNotFoundException;
import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AccountController {
    private final AccountRepository repository;

    private final AccountModelAssembler assembler;

    public AccountController(AccountRepository repository, AccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/accounts")
    CollectionModel<EntityModel<Account>> all(){
        List<EntityModel<Account>> accounts = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    @PostMapping("/accounts")
    ResponseEntity<?> newAccount(@RequestBody Account account){
        EntityModel<Account> entityModel = assembler.toModel(repository.save(account));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        // ResponseEntity create an HTTP 201 Created status message
    }

    @GetMapping("/accounts/{id}")
    EntityModel<Account> one(@PathVariable Long id){
        Account account = repository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));

        return assembler.toModel(account);
    }

    @PutMapping("/accounts/{id}")
    ResponseEntity<?> replaceAccount(@RequestBody Account newAccount, @PathVariable Long id){
        Account replacedAccount = repository.findById(id)
                .map(account -> {
                    account.setAccountNumber(newAccount.getAccountNumber());
                    account.setName(newAccount.getName());
                    account.setBalance(newAccount.getBalance());
                    return repository.save(newAccount);
                })
                .orElseGet(() ->{
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });

        EntityModel<Account> entityModel = assembler.toModel(replacedAccount);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/accounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
