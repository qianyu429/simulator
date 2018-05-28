package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Bank;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
public interface BankService {

    List<Bank> findAllBanksByPage(int pageNum);

    List<Bank> findAllBanks();

    Bank getBank(Long id);

    Bank findBankByBankNo(String bankNo);

    Bank findBankByAdminUserId(Long userId);

    int saveBank(Bank bank);

    int updateBank(Bank bank);

    int deleteBank(Long id);
}