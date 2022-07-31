package com.subhan.onlinebank.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.subhan.onlinebank.entiry.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
	@Query(value = "select bal from Account where acc_num =:aid", nativeQuery = true)
	public double getBalByAcc(@Param("aid") String aid);
	@Modifying
	@Transactional
	@Query(value = "UPDATE Account set bal=:amt where acc_num = :aid", nativeQuery = true)
	public void updateBalByAcc(@Param("aid") String aid,@Param("amt") double amt);


	/*
	 * @Query(value = "select * from tx where tx_id = :txId ", nativeQuery = true)
	 * List<Transaction> findTxId(@Param("txId") String txId);
	 * 
	 *  @Modifying
	 @Transactional 
	 @Query(value = "UPDATE cust set amt=:amt where acc_num = :acc", nativeQuery = true)
	 void updateBalByAcc(@Param("amt") double amt,@Param("acc") String acc);
	
	 * 
	 */
}
