package com.subhan.onlinebank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.subhan.onlinebank.entiry.Transaction;
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String>{
	public List<Transaction> findAllByOrderByCreatedDesc();
	public List<Transaction> findByAccNumOrderByCreatedDesc(String accNum);
	@Query(value = "select * from transaction where acc_num =:accNum order by created desc limit 5", nativeQuery = true)
	public List<Transaction> findLast5ByAccNumOrderByCreatedDesc(String accNum);
}
