package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.DepositService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
	private final DepositService depositService;

	public AccountController(AccountService accountService, DepositService depositService) {
		this.accountService = accountService;
		this.depositService = depositService;
	}

	@PostMapping
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@GetMapping("/{id}")
	public Account getAccount(@PathVariable Long id) {
		return accountService.getAccountById(id);
	}

	@PostMapping("/{id}/deposit")
	public Map<String, String> deposit(@PathVariable Long id, @RequestBody DepositRequest request) {
		depositService.deposit(id, request.amount());
		return Map.of("message", "Deposit successful");
	}

	public record DepositRequest(Double amount) {
	}
}