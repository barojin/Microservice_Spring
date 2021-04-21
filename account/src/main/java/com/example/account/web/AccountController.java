package com.example.account.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AccountRepository repository;

    public AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/accounts")
    List<Account> all(){
        return repository.findAll();
    }

    @PostMapping("/accounts")
    Account newAccount(@RequestBody Account account){
        return repository.save(account);
    }

    @GetMapping("/accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable Long id){
        return repository.findById(id)
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
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable Long id){
        repository.deleteById(id);
    }
}
