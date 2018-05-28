package com.shhxzq.bs.controller.dashboard;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.mapping.songguo.HopRequest;
import com.shhxzq.bs.model.Order;
import com.shhxzq.bs.model.ValidationResponse;
import com.shhxzq.bs.service.OrderService;
import com.shhxzq.kernel.security.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by houjiagang on 2016/12/19.
 */
@Controller
@RequestMapping(value = "dashboard/order")
@Log4j2
public class DashboardHopController {

    @Value("${hop.server}")
    private String hopServerIP;

    @Value("${hop.port}")
    private String hopServerPort;

    // private key and public key needed to be configured in the Hop database. HOP_ENCRYPT_KEY and HOP_FILE_CONFIG
    private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCsw0Ok68hlWDicqwfLSR+NiOdmVjYx2VekXZiZT7nLw4qVYsbOhBRUC2fE2XNPFNBcaeKuZmFrpGSDqSVs0goPi7xiQSbGQep2EOeq87jk3ZsKXAdtPvX1XmHTepY5+xmZBMQbVah1Vtzjjlf/iFjcyPupACCALz0Ylez7u9pv8m3AY/i0YU08xPUx9a19IUzj24xEr2K2kYe1J7edKI57BSaOYPgOCxj3msVk+Gj6cUCF+dB0AX8ECROh0QWEGLsRobQt4oUhAwusPN0kgQQg9Uy6M/UjQEQotn86JRKPwvXJcyq53EoFvgqm/PtzWvWWbxHmVdj19vEM/n9GpQmnAgMBAAECggEAUwxKqCzv2Efgbu+If6BXGqKFGhy3UJ86Ejkr8gbxOZJ2O/mPuBal7wDMkUQ2uf03bDU6UrvEeQo9h0z4QKd3TqHNnS3UhdmJ69eUhglDCEG/FevHZiyt75W/UPnM3XJni7dOzhUPNdjbtkfm5V+V2AyFbWgyN2x94iOwGBLlnooQN+RsQoVliXXygbYF6XYwsWmbMlB9mRhoEzonzmsmBo2nnOrAARjNtw4tjmCGkI9K67QXE6pxK8ogtxteGduk4xDnGvVD2hMc2HUB6qtTYZZlefzOOXadmSeL8FhzKUD5H5es5mYKLIuYGfr9wV6yLmf4OkAMM45hXHrNMQrLgQKBgQDniHuxffyqpDr5WnEIKDpEBG7I43DmM0rle28GbawL/juGg36LCsZgDhlwlgCM4wF758JrOI0Oyvtj3VJav6DIsTTsZl0G9TliY4zKdZgaEHdb6Axej0vQa1QMS201C+HTT+HaQz4s4eF6gLg/i6LcXUF50zniMG5Fy7BpUiexcwKBgQC/BOdVlnX94DbOiMokXFhTT0jGyxyNmM9FK4gbVpjRIgHOgIdwr9trbJgL5d3RCAwVRKTYfiPrbTpPao5bSSKJdwAikEtivkIhEGBKEoYeWwqPTm4/79YZ5rAG02Ed2928JfYayF+SZI/yRARQSDgielz+5g6FG+hzQqLoOTPp/QKBgA+0HRebwPBd9TYGYVY5TEJivpTXgEfMwM6xwYUBGUMy+hyUfJe3ol7PdgBB3EWx+97IiFI3YrHXKJfMYhKPnrsd8cX652JabYrzz4/HzAowhbfxFC2xsGWxceDnmL+ZT7bCW0Ivf18R7vYdFuIQeXpSxOcbYXiq6j/Hoe5yyQhrAoGAASO/WZRfOdeHnC3WvubKJB0Z+w2lKvcZbXk4A6m9manRRvEfXb2+2mI4egGyFBgvMkVJkn0WK8ZoDac+GC9UhGtwVcR0nq8x586YNHjt0eqLIpW+NKVyqo7kx/Wk46+3H/M+B6TgZRgyf6iGOhBkPVhri53FwmeLOHzSSf5lX+UCgYAt+fOZA/h/lG6EB/nnvbANCvVrXGUJkp8oGJKS9uIUUdk3AU3tB9JQfnwE0y676nI8m9DtOUoeee8N543JKKckMtNtzbYe+/yDvR8mIow3DwBSb7kQEYqWJ0+DWPLVZ66MYOAMXmaj7bB1MmW8FwZl+/M4qV6wSt6jK/qD3JfqXg==";

    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArMNDpOvIZVg4nKsHy0kfjYjnZlY2MdlXpF2YmU+5y8OKlWLGzoQUVAtnxNlzTxTQXGnirmZha6Rkg6klbNIKD4u8YkEmxkHqdhDnqvO45N2bClwHbT719V5h03qWOfsZmQTEG1WodVbc445X/4hY3Mj7qQAggC89GJXs+7vab/JtwGP4tGFNPMT1MfWtfSFM49uMRK9itpGHtSe3nSiOewUmjmD4DgsY95rFZPho+nFAhfnQdAF/BAkTodEFhBi7EaG0LeKFIQMLrDzdJIEEIPVMujP1I0BEKLZ/OiUSj8L1yXMqudxKBb4Kpvz7c1r1lm8R5lXY9fbxDP5/RqUJpwIDAQAB";

    private String privateKey_lihe = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9/4RILxY+8WkHUx0IcKdS66a+8aox2duShPCo2HPvkfip/gJzhXsmxDA54ybgedEd0yDhqE1Da5YNwX/dqW65iaGRscXro9WoBlfhc17unGinhHuS5P/n6pc48SItxa27e1/zYCgIHNMZAMagWuZ0HhPCcoywRNK4ppg5LMeOGOvIbOy1ahF7dQvauvhq9WtB3JsJI8DwKKKkdDwFBDGfhR0XoNgqpTeGl2U/SQ5iYwy3VUC/1Sj6z5EFAjqUTzwXPyZs7sggcseyWs3jGL1tnyIhwoBs6zBBzoYgsYVFRSiwLnQnCYdh4839t8plozBen5GRI1MwDWQZh1LSJ9u7AgMBAAECggEAE4FPyzlKR1vxNFdL+7nU86ZNj/yvRD7hU8FiE3NlM4sAxDLXfZSOeARcDxVKwHDBDxbFoWTtyaYZ2X+r6M2kGFgQxixB7Vb8cCt2iiaV8FkORWRMa2AnBWAH9kiLAbfxhfW8e/e7JFoRMdfqjDvIvNL5pFSuLMpj96Dir7vxIJ0xBSreOLpTwUyu5tophu4SN3G7QaR9NCWH078QOnsHGZa4chXAuwM3cEK5CFIFCZ1O4l0OdoA2qjHosZ+67e8zUqQGO66xNuALg8CVtagt/HaOH4OyzPLcwWfbOaF7AmPJSANpbcIdMYb03CBOLIxoKzZgTy76bu9POtsF+StfwQKBgQDl43fH68KFEgltzRggjaozXFOFsbZFRXeHJ+lSxNpj36JskoIcfkEb5BNpup+llhR9kivdEgYhPmRMOTEg/CmcPlhgcP6KDR81yzVApZdHkzk8t856NN7YW/3ByJtiCBilO7GNrq1CioO0nYIx6VvnacTv9/5CLFHVOujAbV6T3wKBgQDTlCTOM5CMRhfmXacXI1ItCGQl9IHr90vlQToYEcVKrp4fSnCCasuCsac+XszR+2ZIiXas52P9S3cHyCXarCgDAu71e0tgQsPNa7V6IUbghUw6ajA1EmQ//f3yQHf6TVpWkFBnL8dOI58r069El0WVYA7anSEMOT2bRIHtP+cTpQKBgBcYWxhCnzAvN7NIP3xFvyuZwupTiDQ9Xly9rbsa+7W86dxue5PRO0lKCIk+rns8MKLM5Is5HFSKqBjgRgdCTgvcV5SYxXmpB9GnsJnmCOSv7bgj3yJ33UtvMncdaD7ZAWDYNmY9fjqJH+ItUL8i6pC9F4PkLi8HtNDvMiLi2fWRAoGAf8Q+Vacd6xeI5zw24uoZfchLcgAtVOmJk2/IPTC+C1NXeV3ig18qdkoBzNBCr6s5ISSh3df9scAYqUJcWakMoITs+KDz67VluG8KsC3YN01Hsx7c7a+lil8/M0NrMGP14cBUTXu0x6MLQ9QZSzpucr4HOk0Y3+DRKyo7q6GjUWECgYEAwsk4/2JYQlPDaJO9zCbXb3vcBlSMaBpgSP6PuJBBavXqW6FpkbVD3NW+mUwNuoQ0hZz+TRiobABIwDrdCGpppao8NFu+wGHhX00dv0VnTmM8TCnlAJie/klXzfXgY1DCinPwjMKutQpejm0eB+yIXJ/iTpKJL/0n4ftknlQzRLo=";

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNow,
                        @RequestParam(value = "orderType", required = false, defaultValue = "") String orderType,
                        Model model) {
        List<Order> orders = orderService.findOrders(pageNow, orderType);
        PageInfo<Order> page = new PageInfo<>(orders);
        model.addAttribute("page", page);

        return "dashboard/hop/index";

    }


    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findOrder(id);
        model.addAttribute("order", order);
        return "dashboard/hop/detail-modal";

    }

    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findOrder(id));
        return "dashboard/hop/update-modal";
    }


    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse update(@ModelAttribute("transaction") @Valid Order order, BindingResult result) {
        ValidationResponse res = new ValidationResponse();
        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            orderService.updateOrder(order);
            res.setStatus(AppConstants.SUCCESS);
        }
        return res;
    }

    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "true";
    }

    @RequestMapping(value = "createOpenAcct", method = RequestMethod.GET)
    public String createOpenAcct(Model model) {
        model.addAttribute("order", new Order());
        return "dashboard/hop/createOpenAcct-modal";
    }

    @RequestMapping(value = "createTransaction", method = RequestMethod.GET)
    public String createTransaction(Model model) {
        model.addAttribute("order", new Order());
        return "dashboard/hop/createTransaction-modal";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ValidationResponse save(HttpServletRequest request, @ModelAttribute("transaction") @Valid Order order, BindingResult result) {
        String orderType = request.getParameter("orderType");
        order.setOrderType(orderType);
        ValidationResponse res = new ValidationResponse();
        if (result.hasErrors()) {
            res.setStatus(AppConstants.FAIL);
        } else {
            orderService.saveOrder(order);
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }

    @RequestMapping(value = "file", method = RequestMethod.GET)
    public String file(Model model) {
        return "dashboard/hop/fileCreate-modal";
    }


    @RequestMapping(value = "genFile", method = RequestMethod.POST)
    public String genFile(HttpServletRequest request) {
        String fileName = orderService.genFile(request.getParameter("orderType"));

        return "redirect:/dashboard/order";
    }

    @RequestMapping(value = "post2hop", method = RequestMethod.POST)
    public String post2hop(HttpServletRequest request, Model model) {
        sendRequest(request, privateKey, model);
        return "dashboard/hop/songguo";
    }

    @RequestMapping(value = "post2lakalaHop", method = RequestMethod.POST)
    public String post2lakalaHop(HttpServletRequest request, Model model) {
        sendRequest(request, privateKey, model);
        return "dashboard/hop/lakala";
    }

    @RequestMapping(value = "post2liheHop", method = RequestMethod.POST)
    public String post2liheHop(HttpServletRequest request, Model model) {
        sendRequest(request, privateKey_lihe, model);
        return "dashboard/hop/lihe";
    }

    private void sendRequest(HttpServletRequest request, String privateKey, Model model) {
        String r = request.getParameter("request");
        HopRequest req = JSON.parseObject(r, HopRequest.class);

        String sign = SecurityUtils.sign(privateKey, req.getContent());

        log.info("Sign String is {}", sign);
        req.setSignature(sign);

        String reqString = JSON.toJSONString(req);

        String resp = send(reqString);

        model.addAttribute("request", reqString);
        model.addAttribute("response", resp);
    }

    @RequestMapping(value = "songguo", method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("request", "");
        model.addAttribute("response", "");

        return "dashboard/hop/songguo";
    }

    @RequestMapping(value = "lakala", method = RequestMethod.GET)
    public String lakala(Model model) {
        model.addAttribute("request", "");
        model.addAttribute("response", "");

        return "dashboard/hop/lakala";
    }

    @RequestMapping(value = "lihe", method = RequestMethod.GET)
    public String lihe(Model model) {
        model.addAttribute("request", "");
        model.addAttribute("response", "");

        return "dashboard/hop/lihe";
    }


    private String send(String data) {
        HttpClient httpClient = new HttpClient();
        PostMethod mypost = new PostMethod("http://" + hopServerIP + ":" + hopServerPort + "/hop/otc-front");
        mypost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        String resMsg = "";
        try {
            RequestEntity entity = new StringRequestEntity(data, "text/xml", "UTF-8");
            mypost.setRequestEntity(entity);
            httpClient.executeMethod(mypost);

            resMsg = mypost.getResponseBodyAsString();
            System.out.println(resMsg);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mypost.releaseConnection();
        }

        return resMsg;
    }

}
