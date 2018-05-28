 $(function () {
    $('#thirdparty').addClass('active open');
    $('#lakala').addClass('active');

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
     
     
    $("#0CIF000007-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000007\",\n"+
            "  \"companyNo\": \"9005\",\n"+
            "  \"subCompanyNo\": \"90050000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"mobile\\\": \\\"${mobile}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\",\\\"certType\\\": \\\"0\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"name\\\": \\\"${name}\\\",\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CTSTRD001-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CTSTRD001\",\n"+
            "  \"companyNo\": \"9005\",\n"+
            "  \"subCompanyNo\": \"90050000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcct\\\": \\\"${tradeAcct}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\",\\\"share\\\": \\\"${share}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


     $("#1CTSTRQ001-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSTRQ001\",\n"+
             "  \"companyNo\": \"9005\",\n"+
             "  \"subCompanyNo\": \"90050000\",\n"+
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
             "  \"companyNo\": \"9005\",\n"+
             "  \"subCompanyNo\": \"90050000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"ProdType\\\": \\\"${ProdType}\\\",\\\"prodId\\\": \\\"${prodId}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

    $("#1CTSSHA005-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSSHA005\",\n"+
            "  \"companyNo\": \"9005\",\n"+
            "  \"subCompanyNo\": \"90050000\",\n"+
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
             "  \"companyNo\": \"9005\",\n"+
             "  \"subCompanyNo\": \"90050000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });

     $("#1CTSSHA007-btn").click(function () {
         jsonString = "{\n"+
             "  \"interfaceId\": \"1CTSSHA007\",\n"+
             "  \"companyNo\": \"9005\",\n"+
             "  \"subCompanyNo\": \"90050000\",\n"+
             "  \"signType\": \"01\",\n"+
             "  \"signature\": \"可以不填\",\n"+
             "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\"}\"\n"+
             "}";
         var jsonObj = JSON.parse(jsonString);
         var pretty = JSON.stringify(jsonObj, undefined, 4);

         document.getElementById("request").innerHTML = pretty;
     });


});