package com.hilrara.kingdom.repository;

import com.hilrara.kingdom.hibernate.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kun7788 on 2016. 12. 18..
 */
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
