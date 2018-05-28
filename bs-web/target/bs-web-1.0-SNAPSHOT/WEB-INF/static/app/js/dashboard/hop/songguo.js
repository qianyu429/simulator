 $(function () {
    $('#thirdparty').addClass('active open');
    $('#songguo').addClass('active');

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
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"mobile\\\": \\\"${mobile}\\\",\\\"cardNo\\\": \\\"${cardNo}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"name\\\": \\\"${name}\\\",\\\"certType\\\": \\\"0\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000005-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000005\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"smsSerialNo\\\": \\\"${smsSerialNo}\\\",\\\"mobileCode\\\": \\\"${mobileCode}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000006-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000006\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
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
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcct\\\": \\\"${tradeAcct}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\",\\\"share\\\": \\\"${share}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#0CTSTRD002-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CTSTRD002\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"serialNo\\\": \\\"${serialNo}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"prodId\\\": \\\"${prodId}\\\",\\\"amount\\\": \\\"${amount}\\\",\\\"paymentType\\\": \\\"${paymentType}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\",\\\"chargeType\\\": \\\"${chargeType}\\\",\\\"apkind\\\": \\\"${apkind}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#1CTSTRQ001-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSTRQ001\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
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
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"ProdType\\\": \\\"${ProdType}\\\",\\\"prodId\\\": \\\"${prodId}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA003-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSSHA003\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });

    $("#0CIF000002-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"0CIF000002\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"partnerCustNo\\\": \\\"${partnerCustNo}\\\",\\\"custType\\\": \\\"${custType}\\\",\\\"name\\\": \\\"${name}\\\",\\\"certType\\\": \\\"${certType}\\\",\\\"certNo\\\": \\\"${certNo}\\\",\\\"mobile\\\": \\\"${mobile}\\\",\\\"riskLevel\\\": \\\"${riskLevel}\\\",\\\"remark\\\": \\\"${remark}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSTRQ004-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSTRQ004\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA006-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSSHA006\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"pageNum\\\": \\\"${pageNum}\\\",\\\"pageSize\\\": \\\"${pageSize}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });


    $("#1CTSSHA007-btn").click(function () {
        jsonString = "{\n"+
            "  \"interfaceId\": \"1CTSSHA007\",\n"+
            "  \"companyNo\": \"9003\",\n"+
            "  \"subCompanyNo\": \"90030000\",\n"+
            "  \"signType\": \"01\",\n"+
            "  \"signature\": \"可以不填\",\n"+
            "  \"content\": \"{\\\"prodId\\\": \\\"${prodId}\\\",\\\"tradeAcco\\\": \\\"${tradeAcco}\\\",\\\"bankAccount\\\": \\\"${bankAccount}\\\",\\\"bankNo\\\": \\\"${bankNo}\\\"}\"\n"+
            "}";
        var jsonObj = JSON.parse(jsonString);
        var pretty = JSON.stringify(jsonObj, undefined, 4);

        document.getElementById("request").innerHTML = pretty;
    });







});