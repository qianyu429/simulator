package com.shhxzq.bs.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.constants.TransTypeEnum;
import com.shhxzq.bs.model.Bank;
import com.shhxzq.bs.model.Dz;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.BankService;
import com.shhxzq.bs.service.DzService;
import com.shhxzq.bs.service.TransactionService;
import com.shhxzq.bs.service.UserService;
import com.shhxzq.bs.util.PushDzUtil;
import freemarker.ext.beans.BeansWrapper;
import lombok.extern.log4j.Log4j2;
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
 * 对账文件管理
 *
 * @author kangyonggan
 * @since 2016/05/29
 */
@Controller
@RequestMapping(value = "dashboard/dz")
@Log4j2
public class DashboardDzController {

    private static final String PATH_ROOT = "dashboard/dz";
    private static final String PATH_INDEX = PATH_ROOT + "/index";
    private static final String PATH_CREATE_MODAL = PATH_ROOT + "/create-modal";
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    private static final String PATH_DZ_TR = PATH_ROOT + "/dz-tr";

    @Autowired
    private DzService dzService;

    @Autowired
    private BankService bankService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    /**
     * 对账文件管理
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("dashboard-dz-manage")
    public String index(@RequestParam(name = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(name = "bankNo", required = false, defaultValue = "") String bankNo,
                        @RequestParam(name = "transType", required = false, defaultValue = "") String transType,
                        @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                        @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate,
                        Model model) {
        Subject subject = SecurityUtils.getSubject();
        Long userId = 0L;
        if (!subject.hasRole("ROLE_SYS_ADMIN")) {
            userId = userService.getShiroUser().getId();
        }
        List<Dz> dzs = dzService.searchDzs(pageNum, userId, bankNo, transType, startDate.replaceAll("-", ""), endDate.replaceAll("-", ""));
        PageInfo<Dz> page = new PageInfo(dzs);
        List<Bank> banks = bankService.findAllBanks();
        int indexW14 = matchBanksNoListId(banks, "W14");
        if (indexW14 != -1)
            banks.remove(indexW14);//去除民生网关对账
        model.addAttribute("page", page);
        model.addAttribute("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        model.addAttribute("banks", banks);
        return PATH_INDEX;
    }

    /**
     * @param banks  银行list列表
     * @param bankNo
     * @return 匹配bankNo的对应List中的index
     */
    int matchBanksNoListId(List<Bank> banks, String bankNo) {
        int i = 0;
        for (Bank bank : banks) {
            if (bank.getBankNo().equals(bankNo))
                return i;
            i++;
        }
        return -1;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-dz-manage")
    public String create(Model model) {
        List<Bank> banks = bankService.findAllBanks();
        int indexW14 = matchBanksNoListId(banks, "W14");
        if (indexW14 != -1)
            banks.remove(indexW14);//去除民生网关对账
        //添加生成全部对账文件的select option标签
        Bank allBank = new Bank();
        allBank.setBankName("全部");
        allBank.setId(new Long(banks.size() + 1));
        allBank.setBankNo("all");
        banks.add(allBank);

        model.addAttribute("banks", banks);

        return PATH_CREATE_MODAL;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("dashboard-dz-manage")
    public ValidationResponse save(@ModelAttribute("dz") @Valid Dz dz,
                                   BindingResult result) throws Exception {
        ValidationResponse res = new ValidationResponse();
        res.setStatus(AppConstants.FAIL);

        if (!result.hasErrors()) {
            //modified by zuodongxiang 17/3/7 9:55
            //添加生成全部对账文件的功能
            String bankNo = dz.getBankNo();
            if ("all".equals(bankNo)) {
                List<Bank> banks = bankService.findAllBanks();
                int indexW14 = matchBanksNoListId(banks, "W14");
                if (indexW14 != -1)
                    banks.remove(indexW14);//去除民生网关对账
                for (Bank bank : banks) {
                    //中国银联和民生T+0的情况下包括pay和redeem两种情况
                    if (AppConstants.BANK_NO_CP.equals(bank.getBankNo()) || AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
                        String fileName_pay = TransTypeEnum.PAY.getType() + transactionService.genDzFile(bank, TransTypeEnum.PAY.getType(), dz.getWorkDay().replaceAll("-", ""));
                        String fileName_redeem = TransTypeEnum.REDEEM.getType() + transactionService.genDzFile(bank, TransTypeEnum.REDEEM.getType(), dz.getWorkDay().replaceAll("-", ""));
                        PushDzUtil.pushDz(bank, fileName_pay, dzService, dz);
                        PushDzUtil.pushDz(bank, fileName_redeem, dzService, dz);
                    } else {
                        String fileName = transactionService.genDzFile(bank, dz.getTransType(), dz.getWorkDay().replaceAll("-", ""));
                        PushDzUtil.pushDz(bank, fileName, dzService, dz);
                    }
                    //res.setStatus(AppConstants.SUCCESS);
//                    // 银联需要推送对账文件
//                    if (bank.getBankNo().equals(AppConstants.BANK_NO_CP)) {
//                        String filePath = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay().replaceAll("-", "") + "/" + fileName;
//                        dzService.pushCpDz(dz.getTransType(), bank, filePath);
//                    } else if (AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
//                        // 民生T+0
//                        String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
//                        dzService.pushCMBCT0Dz(path + fileName);
//                        log.info("民生T+0对账文件已推送");
//                    } else if (AppConstants.BANK_NO_CITICB.equals(bank.getBankNo())) {
//                        // 中信信用卡
//                        String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
//                        dzService.pushCiticbDz(path);
//                    }

                }
            } else {
                Bank bank = bankService.findBankByBankNo(dz.getBankNo());
                String fileName = transactionService.genDzFile(bank, dz.getTransType(), dz.getWorkDay().replaceAll("-", ""));


                // 银联需要推送对账文件
//                if (bank.getBankNo().equals(AppConstants.BANK_NO_CP)) {
//                    String filePath = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay().replaceAll("-", "") + "/" + fileName;
//                    dzService.pushCpDz(dz.getTransType(), bank, filePath);
//                } else if (AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
//                    // 民生T+0
//                    String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
//                    dzService.pushCMBCT0Dz(path + fileName);
//                    log.info("民生T+0对账文件已推送");
//                } else if (AppConstants.BANK_NO_CITICB.equals(bank.getBankNo())) {
//                    // 中信信用卡
//                    String path = AppConstants.PATH_DOWNLOAD_DZ + bank.getCode() + "/" + dz.getWorkDay() + "/";
//                    dzService.pushCiticbDz(path);
//                }
            }
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @RequiresPermissions("dashboard-dz-manage")
    public String detail(@PathVariable("id") Long id, Model model) {
        Dz dz = dzService.getDz(id);

        model.addAttribute("dz", dz);
        return PATH_DETAIL_MODAL;
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("dashboard-dz-manage")
    public String delete(@PathVariable Long id) {
        dzService.deleteDz(id);
        return "true";
    }

    @RequestMapping(value = "{id}/push", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @RequiresPermissions("dashboard-dz-manage")
    public String push(@PathVariable Long id) {
        Dz dz = dzService.getDz(id);
        Bank bank = bankService.findBankByBankNo(dz.getBankNo());

        if (AppConstants.BANK_NO_CP.equals(bank.getBankNo())) {
            // 银联
            dzService.pushCpDz(dz.getTransType(), bank, dz.getPath());
        } else if (AppConstants.BANK_NO_CMBCT0.equals(bank.getBankNo())) {
            // 民生T+0
            dzService.pushCMBCT0Dz(dz.getPath());
        } else if (AppConstants.BANK_NO_CITICB.equals(bank.getBankNo())) {
            // 中信信用卡
            dzService.pushCiticbDz(dz.getPath());
        }

        return AppConstants.SUCCESS;
    }

    /**
     * 禁用/启用
     *
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "{id}/{status:\\bable\\b|\\bdisable\\b}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions("admin-user-manage")
    public String lock(@PathVariable("id") Long id, @PathVariable("status") String status, Model model) {
        Dz dz = dzService.getDz(id);
        dz.setStatus(status);
        dzService.updateDz(dz);

        model.addAttribute("dz", dz);
        return PATH_DZ_TR;
    }

}
