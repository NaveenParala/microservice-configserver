package com.wba.walgreens.accounts.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wba.walgreens.accounts.model.Accounts;
@Repository
public interface AccountsRepo extends CrudRepository<Accounts, Long> {

	Accounts findByCustomerId(int customerId);
}
