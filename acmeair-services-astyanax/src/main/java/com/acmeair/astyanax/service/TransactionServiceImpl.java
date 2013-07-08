package com.acmeair.astyanax.service;

import org.springframework.stereotype.Service;

import com.acmeair.service.TransactionService;

@Service("wxsTransactionServiceImpl")
public class TransactionServiceImpl implements TransactionService {

	@Override
	public void prepareForTransaction() throws Exception {

		System.out.println("Prepare");
		
	}

}
