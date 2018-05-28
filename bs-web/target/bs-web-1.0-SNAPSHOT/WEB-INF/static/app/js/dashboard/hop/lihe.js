 $(function () {
    $('#thirdparty').addClass('active open');
    $('#lihe').addClass('active');

    var requestStr = document.getElementById("request").value;
    var responseStr = document.getElementById("response").value;

    if (responseStr && typeof(responseStr)!="undefined" && responseStr!=0)
    {
        var reqObj = JSON.parse(requestStr);
        var reqPretty = JSON.stringify(reqObj, undefined, 4);
        document.getElementById("request").innerHTML = reqPretty;

        var respObj = JSON.parse(responseStr);
        var respPretty = JSON.stringify(respObj, undefined, 4);
        document.getElementById("response").innerHTML = respPretty;
    }
     
     
    $("#0CIF000004-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000004\",\n"+
            "  \"companyNo\": \"9007\",\n"+
            "  \"subCompanyNo\": \"90070000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"bizType\\\": \\\"bindBankCard\\\",\\\"mobile\\\": \\\"${mobile}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\",\\\"certType\\\": \\\"0\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"name\\\": \\\"${name}\\\",\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000005-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000005\",\n"+
            "  \"companyNo\": \"9007\",\n"+
            "  \"subCompanyNo\": \"90070000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"smsSerialNo\\\": \\\"${smsSerialNo}\\\",\\\"bankSmsSerialNo\\\": \\\"${bankSmsSerialNo}\\\",\\\"mobileCode\\\": \\\"${mobileCode}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

     $("#0CIF000006-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"0CIF000006\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#0CTSTRD001-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"0CTSTRD001\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"prodId\\\": \\\"${prodId}\\\"," +
             "\\\"amount\\\": \\\"${amount}\\\",\\\"share\\\": \\\"${share}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\"," +
             "\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"A\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#0CTSTRD003-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"0CTSTRD003\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\"," +
             "\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\"," +
             "\\\"apkind\\\": \\\"${apkind}\\\",\\\"workday\\\": \\\"${workday}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

    $("#0CTSTRD006-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CTSTRD006\",\n"+
            "  \"companyNo\": \"9007\",\n"+
            "  \"subCompanyNo\": \"90070000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"share\\\": \\\"${share}\\\"," +
            "\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\"," +
            "\\\"apkind\\\": \\\"${apkind}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


     $("#1CTSTRQ001-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSTRQ001\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSPRD002-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSPRD002\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodType\\\": \\\"${prodType}\\\",\\\"prodId\\\": \\\"${prodId}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSSHA003-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSSHA003\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSTRQ004-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSTRQ004\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSPRD005-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSPRD005\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodType\\\": \\\"${prodType}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"navDate\\\": \\\"${navDate}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSSHA006-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSSHA006\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSPRO001-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSPRO001\",\n"+
             "  \"companyNo\": \"9007\",\n"+
             "  \"subCompanyNo\": \"90070000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"profitDay\\\": \\\"${profitDay}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"prodType\\\": \\\"${prodType}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

});