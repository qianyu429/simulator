package ${package}.controller.${module?lower_case};

import ${package}.model.${domain};
<#if type == '0'>
import ${package}.constants.AppConstants;
import ${package}.model.ValidationResponse;
</#if>
import ${package}.service.${domain}Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author kangyonggan
 * @since ${.now?string("yyyy/MM/dd")}
 */
@Controller
@RequestMapping(value = "<#if module!='Web'>${module?lower_case}/</#if>${lower_domain}")
public class <#if module!='Web'>${module}</#if>${domain}Controller {

    private static final String PATH_ROOT = "${module?lower_case}/${lower_domain}";
    private static final String PATH_INDEX = PATH_ROOT + "/list";
    private static final String PATH_FORM = PATH_ROOT + "/form";
    <#if type == '0'>
    private static final String PATH_DETAIL_MODAL = PATH_ROOT + "/detail-modal";
    <#else>
    private static final String PATH_DETAIL = PATH_ROOT + "/detail";
    </#if>

    @Autowired
    private ${domain}Service ${lower_domain}Service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        Model model) {
        List<${domain}> ${lower_domain}s = ${lower_domain}Service.findAll${domain}sByPage(pageNum);
        PageInfo<${domain}> page = new PageInfo(${lower_domain}s);

        model.addAttribute("page",page);
        return PATH_INDEX;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(@ModelAttribute("${lower_domain}") ${domain} ${lower_domain}){
        return PATH_FORM;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    <#if type == '1'>
        <#include "controller-save-common-function.ftl" />
    <#elseif type == '0'>
        <#include "controller-save-ajax-function.ftl" />
    </#if>


    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("${lower_domain}", ${lower_domain}Service.get${domain}(id));
        return PATH_FORM;
    }

    @RequestMapping(value = "{id:[\\d]+}/update", method = RequestMethod.POST)
    <#if type == '1'>
        <#include "controller-update-common-function.ftl" />
    <#elseif type == '0'>
        <#include "controller-update-ajax-function.ftl" />
    </#if>


    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id, Model model) {
        ${domain} ${lower_domain} = ${lower_domain}Service.get${domain}(id);

        model.addAttribute("${lower_domain}", ${lower_domain});
        <#if type == '0'>
        return PATH_DETAIL_MODAL;
        <#else>
        return PATH_DETAIL;
        </#if>
    }

    @RequestMapping(value = "{id:[\\d]+}/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@PathVariable Long id){
        ${lower_domain}Service.delete${domain}(id);
        return "true";
    }

}
