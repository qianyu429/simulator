package com.shhxzq.bs;

import com.shhxzq.bs.common.Base64Coder;
import com.shhxzq.bs.common.Coder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houjiagang on 16/7/21.
 */
public class BOCTest {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void testPreSign() throws Exception {
        String uri = "http://localhost:8080/MCPPreSignAgreement.do";

        String signature = "<?xml version='1.0' encoding='UTF-8'?><request><head><requestTime>20160809041516</requestTime></head><body><traceNo>2016080900013924</traceNo><cardNo>6217850100000000631</cardNo><tranCode>01</tranCode><recvTime>20160809161516</recvTime></body></request>";
        String messageId = "215108";

        Coder coder = new Base64Coder();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        // 交易码（固定6位数字）
        nvps.add(new BasicNameValuePair("messageId", coder.encode(messageId.getBytes("UTF-8"))));
        // 签名结果
        nvps.add(new BasicNameValuePair("signature", signature));

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
        EntityUtils.consume(entity);
        System.out.print(content);

    }




}
