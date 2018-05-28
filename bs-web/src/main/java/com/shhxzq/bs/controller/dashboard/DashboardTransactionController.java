package com.shhxzq.bs.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.service.UserService;
import freemarker.ext.beans.BeansWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 交易
 *
 * @author kangyonggan
 * @since 2016/05/26
 */
@Controller
@RequestMapping(value = "dashboard/transaction")//@RequestMapping用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class DashboardTransactionController {

    private static final String PATH_ROOT = "dashboard/transaction";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_TRANSACTION_TR = PATH_ROOT + "/transaction-tr";

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    /**
     * 交易管理
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)//指定请求的method类型
    @RequiresPermissions("dashboard-transaction-manage")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        @RequestParam(value = "transType", required = false, defaultValue = "") String transType,
                        @RequestParam(value = "bankNo", required = false, defaultValue = "") String bankNo,
                        @RequestParam(value = "beSer", required = false, defaultValue = "") String beSer,
                        Model model) {
        Subject subject = SecurityUtils.getSubject();
        Long userId = 0L;
        if (!subject.hasRole("ROLE_SYS_ADMIN")) {
            userId = userService.getShiroUser().getId();
        }
        List<Transaction> transactions = transactionService.searchTransactions(pageNow, userId, transType, bankNo, beSer);
        List<Bank> banks = bankService.findAllBanks();
        PageInfo<Transaction> page = new PageInfo(transactions);

        model.addAttribute("page", page);
        model.addAttribute("banks", banks);
        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        return PATH_INDEX;
    }

    /**
     * 添加
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-transaction-manage")
    public String create(Model model) {
        List<Bank> banks = bankService.findAllBanks();

        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("banks", banks);
        return PATH_CREATE_MODAL;
    }

    /**
     * 保存
     *
     * @param transaction
     * @param result
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-transaction-manage")
    public ValidationResponse save(@ModelAttribute("transaction") @Valid Transaction transaction,
                                   BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            transactionService.saveTransaction(transaction);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    /**
     * 编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-transaction-manage")
    public String edit(@PathVariable Long id, Model model) {
        List<Bank> banks = bankService.findAllBanks();

        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        model.addAttribute("transaction", transactionService.getTransaction(id));
        model.addAttribute("banks", banks);
        return PATH_CREATE_MODAL;
    }

    /**
     * 更新
     *
     * @param transaction
     * @param result
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-transaction-manage")
    public ValidationResponse update(@ModelAttribute("transaction") @Valid Transaction transaction,
                                     BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            transactionService.updateTransaction(transaction);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    /**
     * 详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-transaction-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        Transaction transaction = transactionService.getTransaction(id);

        model.addAttribute("transaction", transaction);
        return PATH_DETAIL_MODAL;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-transaction-manage")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "true";
    }

    /**
     * 禁用/启用
     *
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/{status:\\bable\\b|\\bdisable\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("dashboard-transaction-manage")
    public String lock(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        Transaction transaction = transactionService.getTransaction(id);
        transaction.setStatus(status);
        transactionService.updateTransaction(transaction);

        model.addAttribute("transaction", transaction);
        return PATH_TRANSACTION_TR;
    }

    /**
     * 流水号唯一性校验
     *
     * @param beSer
     * @param vaild
     * @return
     */
    @RequestMapping(value = "/verify-beSer", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-transaction-manage")
    public boolean verifyName(@RequestParam String beSer, @RequestParam Boolean vaild) {
        if (!vaild) {
            return true;
        }
        Transaction transaction = transactionService.findTransactionBySerNo4dashboard(beSer);
        return transaction == null;
    }

}
