package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.User;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
@Service
@Transactional
public class BankServiceImpl extends BaseService<Bank> implements BankService {

    @Autowired
    private UserService userService;

    @Override
    public int saveBank(Bank bank) {
        bank.setStatus(StatusEnum.ABLE.getStatus());
        bank.setCreatedTime(new Date());
        bank.setUpdatedTime(new Date());

        if (bank.getAdminUserId() != 0) {
            User user = userService.getUser(bank.getAdminUserId());
            bank.setAdminRealname(user.getRealname());
        }

        return super.insertSelective(bank);
    }

    @Override
    public int updateBank(Bank bank) {
        bank.setUpdatedTime(new Date());
        if (bank.getAdminUserId() != 0) {
            User user = userService.getUser(bank.getAdminUserId());
            bank.setAdminRealname(user.getRealname());
        }

        return super.updateByPrimaryKeySelective(bank);
    }

    @Override
    public int deleteBank(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public List<Bank> findAllBanksByPage(int pageNum) {
        Example example = new Example(Bank.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<Bank> findAllBanks() {
        Bank bank = new Bank();
        bank.setStatus(StatusEnum.ABLE.getStatus());

        return super.select(bank);
    }

    @Override
    public Bank getBank(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public Bank findBankByBankNo(String bankNo) {
        Bank bank = new Bank();
        bank.setBankNo(bankNo);
        return super.selectOne(bank);
    }

    @Override
    public Bank findBankByAdminUserId(Long userId) {
        Bank bank = new Bank();
        bank.setAdminUserId(userId);

        return super.selectOne(bank);
    }
}
