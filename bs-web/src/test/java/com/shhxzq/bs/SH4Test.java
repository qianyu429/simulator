package com.shhxzq.bs;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.BufferedInputStream;

/**
 * Created by zhangzhenzhen on 17/7/13.
 */
public class SH4Test {
    @Test
    public void test() throws Exception {
        String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Banksh>\n" +
                "  <Message id=\"2017051000001299797\">\n" +
                "    <CSReq id=\"TQReq\">\n" +
                "      <instId>315310018000363</instId>\n" +
                "      <date>20170510 17:40:36</date>\n" +
                "      <KoalB64Cert>MIIDwjCCAqqgAwIBAgIQEAAAAAAAAAAAAAAgIjhCdzANBgkqhkiG9w0BAQUFADBZMQswCQYDVQQG\n" +
                "EwJDTjEwMC4GA1UEChMnQ2hpbmEgRmluYW5jaWFsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MRgw\n" +
                "FgYDVQQDEw9DRkNBIFRFU1QgT0NBMTEwHhcNMTcwNTA1MDY0OTI3WhcNMTgwNTA0MTYwMDAwWjCB\n" +
                "jTELMAkGA1UEBhMCQ04xFTATBgNVBAoTDENGQ0EgVEVTVCBDQTENMAsGA1UECxMES09BTDESMBAG\n" +
                "A1UECxMJQ3VzdG9tZXJzMUQwQgYDVQQDDDswNDFAWjIwMTcwNTA1MTAxMTUzNkBtMzE1MzEwMDE4\n" +
                "MDAwMzYzJOWNjuS/oeivgeWIuEAwMDAwMDAwMDCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA\n" +
                "rQCsT7Wp9MOg6hbu8WDCQ4MwYGnhT3LrGkML8mrbhDnWA3nWdigE+keKfaQXD1UKkwsmP+d4FPm9\n" +
                "V5wsgqe/INNw0WxCgku4UIbcfMKW0js0r77NEBWI5VC69/nWw+Jh4c55wC1fIUd3rkFompUVPPnK\n" +
                "wj5RW2Qzpbo+5+kcOfMCAwEAAaOB1DCB0TAfBgNVHSMEGDAWgBT8C7xEmg4xoYOpgYcnHgVCxr9W\n" +
                "+DAJBgNVHRMEAjAAMDoGA1UdHwQzMDEwL6AtoCuGKWh0dHA6Ly8yMTAuNzQuNDIuMy9PQ0ExMS9S\n" +
                "U0EvY3JsMjEyODAuY3JsMAsGA1UdDwQEAwIFoDAdBgNVHQ4EFgQUu5OEXBGXR6W/eWvOO7l1RPXo\n" +
                "KdQwOwYDVR0lBDQwMgYIKwYBBQUHAwIGCCsGAQUFBwMDBggrBgEFBQcDBAYIKwYBBQUHAwEGCCsG\n" +
                "AQUFBwMIMA0GCSqGSIb3DQEBBQUAA4IBAQBSFDGzVfvZJc640mn+VqWxei8DecktW4Fx9ecUxfeK\n" +
                "H/ccDuQ4++em+rQjOctJRr2ZUiF7T91YmlBA/XNbR8Fr6CqeGAdauD2p1qNwGCeYIViB1C97nuaz\n" +
                "M2Ethd3cdGW4Kzi7b9sz/6Z193JWeDhWnBRCC3yirQb+/gxMDBKw/a1OQjmAahqwPfN2HtVHKww3\n" +
                "TdpOt9hoKmwi2VYScIEpS8NzBbQyHzCCSy+s60AHuXlg8+5r0yGruNmmq+nbzwLThOmFKS4zZm/J\n" +
                "EAfASBx6bAdKpUYdx62q3IcH57sszeO+Ly9NcZvs1Z+oF0zK2m5RA8E95wCeMZo8GgWpVUVq</KoalB64Cert>\n" +
                "      <serialNo>2017051000001299797</serialNo>\n" +
                "      <type>1</type>\n" +
                "      <beginDate>20170510 17:40:36</beginDate>\n" +
                "      <endDate>20170510 17:40:36</endDate>\n" +
                "      <checkSerialNoList>2017051000001299</checkSerialNoList>\n" +
                "    </CSReq>\n" +
                "    <Signature>TZqq/J+MNUF5WH6U3hwJ+AppqYF+qkp4b4Mjh1X7IcChwO/U3TttSlY1S8eW2xmLHmBJfVW3Q0Hl\n" +
                "10UGIhZalTt4H3o9YPF7lM+aqRMBblhHidfwFPLuJuHPeAUJ5BhxrWFfMG6dwIjXEXnAhKAtCTph\n" +
                "sBDECi/Zbzat62rAL2Q=</Signature>\n" +
                "  </Message>\n" +
                "</Banksh>";
        String url = "http://localhost:8080/boscartoon/directpay.do";
        HttpClient client = new HttpClient();
        PostMethod myPost = new PostMethod(url);
        try {

            //设置请求超时时间
            String responseString = null;

            myPost.setRequestHeader("Content-Type","application/xml");
            myPost.setRequestHeader("charset","utf-8");

            myPost.setRequestBody(xmlData);
            int statusCode = client.executeMethod(myPost);
            if(statusCode == HttpStatus.SC_OK){
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while((count = bis.read(bytes))!= -1){
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                responseString = new String(strByte,0,strByte.length,"utf-8");
                bos.close();
                bis.close();
                System.out.println("response string: " + responseString);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            myPost.releaseConnection();
        }

    }


}
