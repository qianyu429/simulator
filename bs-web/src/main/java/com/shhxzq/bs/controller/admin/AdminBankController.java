package com.shhxzq.bs.controller.admin;

import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.User;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2016/05/29
 */
@Controller
@RequestMapping(value = "admin/bank")
public class AdminBankController {

    private static final String PATH_ROOT = "admin/bank";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_BANK_TR = PATH_ROOT + "/bank-tr";

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("admin-bank-manage")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        Model model) {
        List<Bank> banks = bankService.findAllBanksByPage(pageNow);
        PageInfo<Bank> page = new PageInfo(banks);

        model.addAttribute("page", page);
        return PATH_INDEX;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("admin-bank-manage")
    public String create(Model model) {
        List<User> users = userService.findAllUsers();

        model.addAttribute("bank", new Bank());
        model.addAttribute("users", users);
        return PATH_CREATE_MODAL;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-bank-manage")
    public ValidationResponse save(@ModelAttribute("bank") @Valid Bank bank,
                                   BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            bankService.saveBank(bank);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("admin-bank-manage")
    public String edit(@PathVariable Long id, Model model) {
        List<User> users = userService.findAllUsers();

        model.addAttribute("bank", bankService.getBank(id));
        model.addAttribute("users", users);
        return PATH_CREATE_MODAL;
    }

    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-bank-manage")
    public ValidationResponse update(@ModelAttribute("bank") @Valid Bank bank,
                                     BindingResult result) {
        ValidationResponse res = new ValidationResponse();

        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            bankService.updateBank(bank);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("admin-bank-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        Bank bank = bankService.getBank(id);

        model.addAttribute("bank", bank);
        return PATH_DETAIL_MODAL;
    }

    @RequestMapping(value = "no/{bankNo}", method = RequestMethod.GET)
    public String detailByBankNo(@PathVariable("bankNo") String bankNo, Model model) {
        Bank bank = bankService.findBankByBankNo(bankNo);

        model.addAttribute("bank", bank);
        return PATH_DETAIL_MODAL;
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
    @RequiresPermissions("admin-user-manage")
    public String lock(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        Bank bank = bankService.getBank(id);
        bank.setStatus(status);
        bankService.updateBank(bank);

        model.addAttribute("bank", bank);
        return PATH_BANK_TR;
    }

    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-bank-manage")
    public String delete(@PathVariable Long id) {
        bankService.deleteBank(id);
        return "true";
    }

    /**
     * 银行编号唯一性校验
     *
     * @param bankNo
     * @param vaild
     * @return
     */
    @RequestMapping(value = "/verify-bankNo", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("admin-user-manage")
    public boolean verifyBankNo(@RequestParam String bankNo, @RequestParam Boolean vaild) {
        if (!vaild) {
            return true;
        }
        Bank bank = bankService.findBankByBankNo(bankNo);
        return bank == null;
    }
}
