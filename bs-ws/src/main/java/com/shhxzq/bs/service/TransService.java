package com.shhxzq.bs.service;

import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/26
 */
public interface TransService {

    List<Transaction> searchTransactions(int pageNow, Long userId, String transType, String bankNo, String beSer);

    List<Transaction> findAllTransactionsByPage(int pageNum);

    List<Transaction> findAllTransactions(String start, String end, String transType, String bankNo);

    List<Transaction> findCiticbDzAllTransactions(String bankNo, String transType);

    List<Transaction> findTransatcions(String bankNo, String transType, String workDay);

    List<Transaction> findTransatctionByExample(Example example);

    Transaction getTransaction(Long id);

    Transaction findTransactionBySerNo(String serNo);

    Transaction findTransactionByProtocolNo(String bankNo, String protocolNo);

    int saveTransaction(Transaction transaction);

    int updateTransaction(Transaction transaction);

    int deleteTransaction(Long id);

    String genDzFile(Bank bank, String transType, String workDay) throws Exception;
}