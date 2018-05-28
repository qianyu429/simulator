    @ResponseBody
    public ValidationResponse save(@ModelAttribute("${lower_domain}") @Valid ${domain} ${lower_domain},
                                   BindingResult result){
        ValidationResponse res = new ValidationResponse();

        if(result.hasErrors()){
            res.setStatus(AppConstants.FAIL);
        }else{
            ${lower_domain}Service.save${domain}(${lower_domain});
            res.setStatus(AppConstants.SUCCESS);
        }

        return res;
    }