package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.DepositTransaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

	private final AccountService accountService;
	private final AccountRepository accountRepository;
	private final DepositRepository depositRepository;

	public DepositService(AccountService accountService, AccountRepository accountRepository, DepositRepository depositRepository) {
		this.accountService = accountService;
		this.accountRepository = accountRepository;
		this.depositRepository = depositRepository;
	}

	@Transactional
	public void deposit(Long accountId, Double amount) {
		Account account = accountService.getAccountById(accountId);
		double currentBalance = account.getBalance() == null ? 0.0 : account.getBalance();
		account.setBalance(currentBalance + amount);
		accountRepository.save(account);

		DepositTransaction depositTransaction = new DepositTransaction();
		depositTransaction.setAmount(amount);
		depositTransaction.setAccount(account);
		depositRepository.save(depositTransaction);

		//throw new RuntimeException("Test Rollback");
	}
}