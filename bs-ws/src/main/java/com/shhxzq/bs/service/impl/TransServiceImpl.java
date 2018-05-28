package com.shhxzq.bs.service.impl;

import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.StatusEnum;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Dz;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.DzService;
import com.shhxzq.bs.service.TransService;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.StringUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/26
 */
@Service
@Transactional
@Log4j2
public class TransServiceImpl extends BaseService<Transaction> implements TransService {

    @Autowired
    private DzService dzService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;
//
//    @Value("${app.dzpath}")
//    private String dzPath;

    @Override
    public int saveTransaction(Transaction transaction) {
        if (StringUtil.isEmpty(transaction.getStatus())) {
            transaction.setStatus(StatusEnum.ABLE.getStatus());
        }
        transaction.setCreatedTime(new Date());
        transaction.setCreatedDate(DateUtil.format8Date(new Date()));
        transaction.setUpdatedTime(new Date());
        return super.insertSelective(transaction);
    }

    @Override
    public int updateTransaction(Transaction transaction) {
        transaction.setUpdatedTime(new Date());
        return super.updateByPrimaryKeySelective(transaction);
    }

    @Override
    public int deleteTransaction(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public String genDzFile(Bank bank, String transType, String workDay) throws Exception {
        log.info("生成对账的参数, bank={}, transType={}, workDay={}", bank, transType, workDay);

        // 查询交易
        List<Transaction> transactions = findDZTransactions(bank.getBankNo(), transType, workDay);
        log.info("查询到 {} 条交易记录", transactions.size());

        // 查询所有中信银行签约的协议号
        List<Transaction> trans = null;
        if(bank.getBankNo() == AppConstants.BANK_NO_CITICB){
            trans = findCiticbDzAllTransactions(bank.getBankNo(), transType);
            log.info("查询到 {} 条交易记录", transactions.size());
        }

        // 对账文件路径
        String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + workDay + "/";
        log.info("对账文件路径: {}", path);

        // 证通
        if (AppConstants.BANK_NO_ECT.equals(bank.getBankNo())) {
            path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/";
        }

        // 中信信用卡
        if(bank.getBankNo().equals(AppConstants.BANK_NO_CITICB)){
            path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/";
            log.info("对账文件路径: {}", path);
        }

        // 对账文件路径
        String fileName = getFileName(bank, workDay);
        log.info("对账文件名称: {}", fileName);

        if(bank.getBankNo().equals(AppConstants.BANK_NO_CITICB)){
            fileName = "ZCMATPOP4L";
            log.info("对账文件名称: {}", fileName);
        }

        String head = bank.getPayHead();
        String template = bank.getPayTemplate();
        if (transType.equals(TransTypeEnum.REDEEM.getType())) {
            head = bank.getRedeemHead();
            template = bank.getRedeemTemplate();
        }

        // 报文第一行
        if (AppConstants.BANK_NO_CP.equals(bank.getBankNo()) || AppConstants.BANK_NO_GDNY.equals(bank.getBankNo())) {
            head = buildDzHead(bank, transType, head, transactions);
        }

//        String rootPath = StringUtil.isEmpty(dzPath) ? AppConstants.APP_ROOT : dzPath;
//        log.info("对账文件根目录为: {}", rootPath);

        // 生成对账文件
        boolean flag = false;
//        if(bank.getBankNo().equals(AppConstants.BANK_NO_CITICB)){
//            flag = FileUtil.genZXDZFile(rootPath + path, fileName, trans);
//        }else{
//            flag = FileUtil.genDZFile(rootPath + path, fileName, head, template, transactions);
//        }

//        if (bank.getBankNo().equals(AppConstants.BANK_NO_CP) && transType.equals(TransTypeEnum.REDEEM.getType())) {
//            flag = FileUtil.genDZFile(path, fileName, head, template, transactions);
//            log.info("将{}转为{}...", fileName, fileName + ".zip");
//            ZipUtil.zip(rootPath + path + fileName, rootPath + path + (fileName = fileName + ".zip"));
//            log.info("转换成功!zip文件路径: {}", path + fileName);
//        }

        if (flag) {
            log.info("对账文件生成成功, 路径为: {}", path + fileName);
            // 对账信息落库
            Dz dz = new Dz();
            dz.setBankNo(bank.getBankNo());
            dz.setPath(path + fileName);
            dz.setTransType(transType);
            dzService.saveDz(dz);
            log.info("对账文件落库成功, {}", dz);
        } else {
            log.info("对账文件生成失败, 路径为: {}", path + fileName);
            return null;
        }

        return fileName;
    }

    /**
     * 构建报文第一行
     *
     * @return
     */
    private String buildDzHead(Bank bank, String transType, String head, List<Transaction> transactions) throws Exception {
        if (bank.getBankNo().equals(AppConstants.BANK_NO_CP) && transType.equals(TransTypeEnum.PAY.getType())) {
            // 中国银联代扣: 总笔数|总金额|成功笔数|成功金额|非成功笔数|非成功金额
            head = String.format(head,
                    transactions.size(), getAmount(transactions, "beSer", false, ""),
                    getCount(transactions, "stat", true, "Y"), getAmount(transactions, "stat", true, "Y"),
                    getCount(transactions, "stat", false, "Y"), getAmount(transactions, "stat", false, "Y"));
        } else if (bank.getBankNo().equals(AppConstants.BANK_NO_CP) && transType.equals(TransTypeEnum.REDEEM.getType())) {
            // 中国银联代付: 总笔数|总金额|退单总笔数|退单总金额|重汇总笔数|重汇总金额|重汇退单总笔数|重汇退单总金额|签名值
            head = String.format(head,
                    transactions.size(), getAmount(transactions, "beSer", false, ""),
                    getCount(transactions, "transStat", true, "6", "9"), getAmount(transactions, "transStat", true, "6", "9"),
                    getCount(transactions, "transStat", true, "7", "8", "9"), getAmount(transactions, "transStat", true, "7", "8", "9"),
                    getCount(transactions, "transStat", true, "9"), getAmount(transactions, "transStat", true, "9"), "");
        } else if (bank.getBankNo().equals(AppConstants.BANK_NO_GDNY)){
            head = String.format(head,
                    getAmount(transactions, "stat", true, "Y"), getCount(transactions, "stat", true, "Y"));
        }
        return head;
    }

    /**
     * 获取对账文件名称
     *
     * @param bank
     * @param workDay
     * @return
     */
    private String getFileName(Bank bank, String workDay) {
        String fileName = "";
        if (AppConstants.BANK_NO_CP.equals(bank.getBankNo())) {
            // 文件生成时间
            String currentTime = DateUtil.format14Date(new Date());

            // 对账文件名[商户号_交易日期(yyyymmdd)_文件生成时间(yyyymmddhhmmss).txt]
            fileName = bank.getMerId() + "_" + workDay + "_" + currentTime + ".txt";
        } else if (AppConstants.BANK_NO_ECT.equals(bank.getBankNo())) {
            // RZDZ_YYYYMMDD.txt
            fileName = "RZDZ_" + workDay + ".txt";
        } else if (AppConstants.BANK_NO_GDNY.equals(bank.getBankNo())) {
            fileName = "00008" + "_" + workDay + ".txt";
        } else if (AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
            // 民生T+0 [DF_HXZQ_KS_DZWJ_T0_20170106.txt]
            fileName = "DF_HXZQ_KS_DZWJ_T0_" + workDay + ".txt";
        }

        return fileName;
    }

    /**
     * 查询交易, 用于对账
     *
     * @param bankNo
     * @param transType
     * @param workDay
     * @return
     */
    private List<Transaction> findDZTransactions(String bankNo, String transType, String workDay) {
        Transaction transaction = new Transaction();
        transaction.setBankNo(bankNo);
        if (StringUtil.isNotEmpty(transType)) {
            transaction.setTransType(transType);
        }
        transaction.setWorkDay(workDay);

        return super.select(transaction);
    }

    @Override
    public List<Transaction> searchTransactions(int pageNow, Long userId, String transType, String bankNo, String beSer) {
        Example example = new Example(Transaction.class);

        Example.Criteria criteria = example.createCriteria();
        if (userId > 0) {
            Bank bank = bankService.findBankByAdminUserId(userId);
            if (bank == null) {
                return new ArrayList();
            }
            criteria.andEqualTo("bankNo", bank.getBankNo());
        }
        if (StringUtil.isNotEmpty(transType)) {
            criteria.andEqualTo("transType", transType);
        }
        if (StringUtil.isNotEmpty(bankNo)) {
            criteria.andEqualTo("bankNo", bankNo);
        }
        if (StringUtil.isNotEmpty(beSer)) {
            criteria.andLike("beSer", "%" + beSer + "%");
        }
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNow, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public List<Transaction> findAllTransactionsByPage(int pageNum) {
        Example example = new Example(Transaction.class);
        example.setOrderByClause("id desc");
        return super.selectByExample4Page(pageNum, example);
    }

    @Override
    public List<Transaction> findAllTransactions(String start, String end, String transType, String bankNo) {
        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("transType", transType);
        criteria.andEqualTo("bankNo", bankNo);
        criteria.andGreaterThanOrEqualTo("beSer", start);
        criteria.andLessThanOrEqualTo("beSer", end);
        example.setOrderByClause("id desc");
        return super.selectByExample(example);
    }

    @Override
    public List<Transaction> findTransatcions(String bankNo, String transType, String workDay) {
        Transaction transaction = new Transaction();
        transaction.setBankNo(bankNo);
        if (StringUtil.isNotEmpty(transType)) {
            transaction.setTransType(transType);
        }
        transaction.setWorkDay(workDay);

        return super.select(transaction);
    }

    @Override
    public List<Transaction> findTransatctionByExample(Example example) {
        return super.selectByExample(example);
    }


    @Override
    public Transaction getTransaction(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public Transaction findTransactionBySerNo(String serNo) {
        Transaction transaction = new Transaction();
        transaction.setBeSer(serNo);

        return super.selectOne(transaction);
    }

    @Override
    public Transaction findTransactionByProtocolNo(String bankNo, String protocolNo) {
        Transaction transaction = new Transaction();
        transaction.setProtocolNo(protocolNo);
        transaction.setBankNo(bankNo);
        transaction.setStatus("able");
        return super.selectOne(transaction);
    }

    @Override
    public List<Transaction> findCiticbDzAllTransactions(String bankNo, String transType) {
        Transaction transaction = new Transaction();
        transaction.setTransType(transType);
        transaction.setBankNo(bankNo);
        transaction.setStatus("able");
        return super.select(transaction);
    }

    private String getCount(List<Transaction> transactions, String field, boolean eq, String... vals) throws Exception {
        Class clazz = Transaction.class;
        int count = 0;
        for (Transaction trans : transactions) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(trans));
            if (eq && StringUtil.in(val, vals)) {
                count++;
            } else if (!eq && !StringUtil.in(val, vals)) {
                count++;
            }
        }
        return count + "";
    }

    private String getAmount(List<Transaction> transactions, String field, boolean eq, String... vals) throws Exception {
        Class clazz = Transaction.class;
        BigDecimal total = new BigDecimal(0);
        for (Transaction trans : transactions) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(trans));
            if (eq && StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(trans.getAmount());
                total = total.add(amount);
            } else if (!eq && !StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(trans.getAmount());
                total = total.add(amount);
            }
        }
        return String.valueOf(total);
    }
}
