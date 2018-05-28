    public String update(@ModelAttribute("${lower_domain}") @Valid ${domain} ${lower_domain},
                         BindingResult result){
        if(!result.hasErrors()){
            ${lower_domain}Service.update${domain}(${lower_domain});
            return "redirect:/" + PATH_ROOT;
        }else{
            return PATH_FORM;
        }
    }