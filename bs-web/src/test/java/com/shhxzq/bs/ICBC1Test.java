package com.shhxzq.bs;

import com.shhxzq.bs.mapping.icbc.CMS;
import com.shhxzq.bs.util.Base64Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by houjiagang on 16/7/21.
 */
public class ICBC1Test {

    private static final String ENCODING = "GBK";

    private XStream requestParser;
    private XStream responseParser;
    private static HttpClient httpClient;

    static {
        PoolingClientConnectionManager manager = new PoolingClientConnectionManager();
        manager.setDefaultMaxPerRoute(2);
        httpClient = new DefaultHttpClient(manager, new BasicHttpParams());
    }

    /**
     * 测试解约
     *
     * @throws Exception
     */
    @Test
    public void testParseXml() throws Exception {

        String res = Base64Util.base64Decode("MIIFvgYJKoZIhvcNAQcCoIIFrzCCBasCAQExCzAJBgUrDgMCGgUAMIIBkwYJKoZIhvcNAQcBoIIBhASCAYBpbnRlcmZhY2VOYW1lPUlDQkNfUEVSQkFOS19FQkNvbnNpZ25QYXkmaW50ZXJmYWNlVmVyc2lvbj0xLjAuMC4wJnNlbHNlcmlhbE5vPUJEUDMwMDM1NjQyNSZwYXlObz0yMDE3MDYxNTAwMjg5MTI5JnNlbHBheVR5cGU9VFRUJnNlbGNvcnBJZD00MDAwOTAwMDE2MDQ0MWEmc2VsYWNjb3VudE5vPTQwMDAwMjMwMjkyMDAxMjQ5NDYmcmVnRGF0ZT0yMDE2MTIzMCZtZXJVUkw9aHR0cDovLzEwLjE5OS4xMDUuMTIyOjgwODAvYmFua0VuZ2luZS9hdXRoZW50aWNhdGUvaWNiY0F1dGhHZXRSZXNwb25zaW9uLnNlcnZpY2UmbWVyQ2VydElEPW5jcnAueS40MDAwJmNlcnREYXRlPTIwMTcwNjMwMTUwOTQ4JmFjY291bnRObz02MjIyMDI0MDAwNzk5MDAwMzQwJmFsbG93RmluYWxEYXRlPTCgggMPMIIDCzCCAfOgAwIBAgIKG5LKECVWAAJvczANBgkqhkiG9w0BAQUFADA7MR8wHQYDVQQDExZJQ0JDIFRlc3QgQ29ycG9yYXRlIENBMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24wHhcNMTUxMjMxMDMwNjQxWhcNMjAxMjMxMDMwNjQxWjA/MRQwEgYDVQQDEwtuY3JwLnkuNDAwMDENMAsGA1UECxMENDAwMDEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCggAaIsLIQ0zFHBgkj+L2bqbLY/vdaxwHHVJBPIyq3IulSG10JA5pwLmTgEr59iRi4wV2myRDhYfioL/PeDm7GYMVzmuBtNX9BB35xPVfZuxHrO/RVsSrdoP4M0hWhm5E16dTy12wzJjjQUBcFM0eiNaJsG5ovmrvtnoqNEN9XWQIDAQABo4GQMIGNMB8GA1UdIwQYMBaAFER9t5AsN6TZ7WzipIdXZwq18E0UMEsGA1UdHwREMEIwQKA+oDykOjA4MQ4wDAYDVQQDEwVjcmwzMjEMMAoGA1UECxMDY3JsMRgwFgYDVQQKEw90ZXN0aWNiYy5jb20uY24wHQYDVR0OBBYEFCNmgyn2V5f9lFzawl8JxiiojQ7KMA0GCSqGSIb3DQEBBQUAA4IBAQAXwwrEH/vNMbpzX8+8EtjbgH2Jm8bEjxr9YU6thEnDR+gvySr4CkeA8PEgpeiCem4iUXPjRUuANbnbjvNPfU1xxKzZwxO9uiC0xX7YpyLI4wc/DzVIQcm7hsFoBjlHw/8pixk4DIlvBQyedZYo9VRURiIO/PQBEWmP9kI+gt7hOQT0id7+mj4VuXMfQAcZ88z5QZ2cKlSuBTi1RtKzPVsHWbimbxRqtCiSJU0Tyx5jcvMtl3WKwyjpOLZJuuxdgHr4QYKihY1W62DcaBnDmRjgmd/xQwNY9xf7ZZLz5AZr/7txBWGkCQrZANA41RHPZrNPNqdozIHPincCeBKldLR3MYHuMIHrAgEBMEkwOzEfMB0GA1UEAxMWSUNCQyBUZXN0IENvcnBvcmF0ZSBDQTEYMBYGA1UEChMPdGVzdGljYmMuY29tLmNuAgobksoQJVYAAm9zMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYCP/qCbNkVolLBjE2Es4QP1ufS+aeYdxX+6so3wrfQwQiaNnToIgp7jSkBv7RwZZcRjuMF3pcjN2H1N/I668lxG5hMZ6E1Hu/41w12MQwaKTHQpD2X+n+iaUG4QGpuQVC2/1RQE9YgKgW9xN6ZDrFZIc9MNYJhIrQ1LG41H2bWPeQ==", "UTF-8");
        String res1 = Base64Util.base64Encode("interfaceName=ICBC_PERBANK_EBConsignPay&interfaceVersion=1.0.0.0&selserialNo=BDP300356425&payNo=2017061500289129&selpayType=TTT&selcorpId=40009000160441a&selaccountNo=4000023029200124946&regDate=20161230&merURL=http://10.199.105.122:8080/bankEngine/authenticate/icbcAuthGetResponsion.service&merCertID=ncrp.y.4000&certDate=20170630150948&accountNo=6222024000799000340");
        String res2 = Base64Util.base64Decode("aW50ZXJmYWNlTmFtZT1JQ0JDX1BFUkJBTktfRUJDb25zaWduUGF5JmludGVyZmFjZVZlcnNpb249MS4wLjAuMCZzZWxzZXJpYWxObz1CRFAzMDAzNTY0MjUmcGF5Tm89MjAxNzA2MTUwMDI4OTEyOSZzZWxwYXlUeXBlPVRUVCZzZWxjb3JwSWQ9NDAwMDkwMDAxNjA0NDFhJnNlbGFjY291bnRObz00MDAwMDIzMDI5MjAwMTI0OTQ2JnJlZ0RhdGU9MjAxNjEyMzAmbWVyVVJMPWh0dHA6Ly8xMC4xOTkuMTA1LjEyMjo4MDgwL2JhbmtFbmdpbmUvYXV0aGVudGljYXRlL2ljYmNBdXRoR2V0UmVzcG9uc2lvbi5zZXJ2aWNlJm1lckNlcnRJRD1uY3JwLnkuNDAwMCZjZXJ0RGF0ZT0yMDE3MDYzMDE1MDk0OCZhY2NvdW50Tm89NjIyMjAyNDAwMDc5OTAwMDM0MA==");

    }
}
