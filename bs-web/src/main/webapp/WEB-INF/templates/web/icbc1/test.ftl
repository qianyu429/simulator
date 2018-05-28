<#assign title="提交">


<body>
<@override name="page_main">

<form id="form" action="http://localhost:8080/servlet/EBConsignPayServlet" method="POST">
    <input type="hidden" name="selserialNo" value="BDP300356425" \=""/>
    <input type="hidden" name="merCertID" value="ncrp.y.4000" \=""/>
    <input type="hidden" name="selpayType" value="TTT" \=""/>
    <input type="hidden" name="certDate" value="20170630150948" \=""/>
    <input type="hidden" name="selcorpId" value="40009000160441a" \=""/>
    <input type="hidden" name="interfaceVersion" value="1.0.0.0" \=""/>
    <input type="hidden" name="selaccountNo" value="4000023029200124946" \=""/>
    <input type="hidden" name="regDate" value="20170612" \=""/>
    <input type="hidden" name="merVAR" value="2017061500289129" \=""/>
    <input type="hidden" name="tipMsg" value="欢迎在线签署《中国工商银行网上银行个人委托缴费协议》" \=""/>
    <input type="hidden" name="payNo" value="2017061500289129" \=""/>
    <input type="hidden" name="merSignMsg" value="" \=""/>
    <!--   <input type="hidden" name="merURL" value="http://10.199.105.122:8080/bankEngine/authenticate/icbcAuthGetResponsion.service" \="" /> -->
    <input type="hidden" name="merURL" value="http://localhost:8080/icbc1/test" \=""/>
    <input type="hidden" name="Language" value="ZH_CN" \=""/>
    <input type="hidden" name="accountNo" value="6222024000799000340" \=""/>
    <input type="hidden" name="merCert" value="" \=""/>
    <input type="hidden" name="interfaceName" value="ICBC_PERBANK_EBConsignPay" \=""/>
    <input type="hidden" name="certData"
           value="MIIFvgYJKoZIhvcNAQcCoIIFrzCCBasCAQExCzAJBgUrDgMCGgUAMIIBkwYJKoZIhvcNAQcBoIIBhASCAYBpbnRlcmZhY2VOYW1lPUlDQkNfUEVSQkFOS19FQkNvbnNpZ25QYXkmaW50ZXJmYWNlVmVyc2lvbj0xLjAuMC4wJnNlbHNlcmlhbE5vPUJEUDMwMDM1NjQyNSZwYXlObz0yMDE3MDYxNTAwMjg5MTI5JnNlbHBheVR5cGU9VFRUJnNlbGNvcnBJZD00MDAwOTAwMDE2MDQ0MWEmc2VsYWNjb3VudE5vPTQwMDAwMjMwMjkyMDAxMjQ5NDYmcmVnRGF0ZT0yMDE2MTIzMCZtZXJVUkw9aHR0cDovLzEwLjE5OS4xMDUuMTIyOjgwODAvYmFua0VuZ2luZS9hdXRoZW50aWNhdGUvaWNiY0F1dGhHZXRSZXNwb25zaW9uLnNlcnZpY2UmbWVyQ2VydElEPW5jcnAueS40MDAwJmNlcnREYXRlPTIwMTcwNjMwMTUwOTQ4JmFjY291bnRObz02MjIyMDI0MDAwNzk5MDAwMzQwJmFsbG93RmluYWxEYXRlPTCgggMPMIIDCzCCAfOgAwIBAgIKG5LKECVWAAJvczANBgkqhkiG9w0BAQUFADA7MR8wHQYDVQQDExZJQ0JDIFRlc3QgQ29ycG9yYXRlIENBMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24wHhcNMTUxMjMxMDMwNjQxWhcNMjAxMjMxMDMwNjQxWjA/MRQwEgYDVQQDEwtuY3JwLnkuNDAwMDENMAsGA1UECxMENDAwMDEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCggAaIsLIQ0zFHBgkj+L2bqbLY/vdaxwHHVJBPIyq3IulSG10JA5pwLmTgEr59iRi4wV2myRDhYfioL/PeDm7GYMVzmuBtNX9BB35xPVfZuxHrO/RVsSrdoP4M0hWhm5E16dTy12wzJjjQUBcFM0eiNaJsG5ovmrvtnoqNEN9XWQIDAQABo4GQMIGNMB8GA1UdIwQYMBaAFER9t5AsN6TZ7WzipIdXZwq18E0UMEsGA1UdHwREMEIwQKA+oDykOjA4MQ4wDAYDVQQDEwVjcmwzMjEMMAoGA1UECxMDY3JsMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24wHQYDVR0OBBYEFCNmgyn2V5f9lFzawl8JxiiojQ7KMA0GCSqGSIb3DQEBBQUAA4IBAQAXwwrEH/vNMbpzX8+8EtjbgH2Jm8bEjxr9YU6thEnDR+gvySr4CkeA8PEgpeiCem4iUXPjRUuANbnbjvNPfU1xxKzZwxO9uiC0xX7YpyLI4wc/DzVIQcm7hsFoBjlHw/8pixk4DIlvBQyedZYo9VRURiIO/PQBEWmP9kI+gt7hOQT0id7+mj4VuXMfQAcZ88z5QZ2cKlSuBTi1RtKzPVsHWbimbxRqtCiSJU0Tyx5jcvMtl3WKwyjpOLZJuuxdgHr4QYKihY1W62DcaBnDmRjgmd/xQwNY9xf7ZZLz5AZr/7txBWGkCQrZANA41RHPZrNPNqdozIHPincCeBKldLR3MYHuMIHrAgEBMEkwOzEfMB0GA1UEAxMWSUNCQyBUZXN0IENvcnBvcmF0ZSBDQTEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuAgobksoQJVYAAm9zMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYCP/qCbNkVolLBjE2Es4QP1ufS+aeYdxX+6so3wrfQwQiaNnToIgp7jSkBv7RwZZcRjuMF3pcjN2H1N/I668lxG5hMZ6E1Hu/41w12MQwaKTHQpD2X+n+iaUG4QGpuQVC2/1RQE9YgKgW9xN6ZDrFZIc9MNYJhIrQ1LG41H2bWPeQ=="
           \=""/>
    <input type="hidden" name="allowFinalDate" value="0" \=""/>
    <input type="submit" name="submit" value="提交" style="width:800px;"/>
</form>

</@override>


<@override name="page_script">
<script src="${ctx}static/app/js/web/login/index.js"></script>
</@override>
</body>
<@extends name="../../layout.ftl"/>
