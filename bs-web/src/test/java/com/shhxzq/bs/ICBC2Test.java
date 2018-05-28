package com.shhxzq.bs;

import com.shhxzq.bs.common.HttpClientSend;
import com.shhxzq.bs.mapping.icbc2.CMS;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houjiagang on 16/7/21.
 */
public class ICBC2Test {

    private static final String ENCODING = "GBK";

    private XStream requestParser;

    private static HttpClient httpClient;

    static {
        PoolingClientConnectionManager manager = new PoolingClientConnectionManager();
        manager.setDefaultMaxPerRoute(2);
        httpClient = new DefaultHttpClient(manager, new BasicHttpParams());
    }


    /**
     * 测试xml解析
     */
    @Test
    public void testParserXml() {

        String sendMsg = "<?xml version=\"1.0\" encoding = \"GB2312\"?>\n" +
                "       <CMS>\n" +
                "              <eb>\n" +
                "                     <pub>\n" +
                "                            <TransCode>交易代码</TransCode>\n" +
                "                            <CIS>集团CIS号</CIS>\n" +
                "                            <BankCode>归属银行编号</BankCode>\n" +
                "<ID>证书ID</ID>\n" +
                "                            <TranDate>交易日期</TranDate>\n" +
                "                            <TranTime>交易时间</TranTime>\n" +
                "                            <fSeqno>指令包序列号</fSeqno>\n" +
                "                     </pub>\n" +
                "                     <in>\n" +
                "                            <OnlBatF>联机批量标志</OnlBatF>\n" +
                "                            <SettleMode>入账方式</SettleMode>\n" +
                "<RecAccNo>收方账号</RecAccNo>\n" +
                "                            <RecAccNameCN>收方账户名称</RecAccNameCN>\n" +
                "                            <RecAccNameEN>收方账户英文名称</RecAccNameEN>\n" +
                "                            <TotalNum>总笔数</TotalNum>\n" +
                "<TotalAmt>总金额</TotalAmt>\n" +
                "<SignTime>签名时间</SignTime>\n" +
                "                            <ReqReserved1>请求备用字段1</ReqReserved1>\n" +
                "                            <ReqReserved2>请求备用字段2</ReqReserved2>\n" +
                "                            <rd>\n" +
                "                                   <iSeqno>指令包顺序号</iSeqno>\n" +
                "                                   <PayAccNo>付款账号</PayAccNo>\n" +
                "                                   <PayAccNameCN>付款账户名称</PayAccNameCN>\n" +
                "                                   <PayAccNameEN>付款账户英文名称</PayAccNameEN>\n" +
                "                                   <PayBranch>付款账号开户行</PayBranch>\n" +
                "<Portno>缴费编号</Portno>\n" +
                "                                   <ContractNo>协议编号</ContractNo>\n" +
                "<CurrType>币种</CurrType>\n" +
                "                                   <PayAmt>金额</PayAmt>\n" +
                "                                   <UseCode>用途代码</UseCode>\n" +
                "                                   <UseCN>用途中文描述</UseCN>\n" +
                "<EnSummary>备注信息</EnSummary>\n" +
                "                                   <PostScript>附言</PostScript>\n" +
                "                                   <Summary>摘要</Summary>\n" +
                "                                   <Ref>业务编号（业务参考号）</Ref>\n" +
                "                                   <Oref>相关业务编号</Oref>\n" +
                "                                   <ERPSqn>ERP流水号</ERPSqn>\n" +
                "                                   <BusCode>业务代码</BusCode>\n" +
                "                                   <ERPcheckno>ERP支票号</ERPcheckno>\n" +
                "                                   <CrvouhType>原始凭证种类</CrvouhType>\n" +
                "                                   <CrvouhName>原始凭证名称</CrvouhName>\n" +
                "                                   <CrvouhNo>原始凭证号</CrvouhNo>\n" +
                "                                   <ReqReserved3>请求备用字段3</ReqReserved3>\n" +
                "                                   <ReqReserved4>请求备用字段4</ReqReserved4>\n" +
                "                            </rd>\n" +
                "                     </in>\n" +
                "              </eb>\n" +
                "       </CMS>";


        String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?><CMS><eb><pub><TransCode>PERDIS</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>rp.y.4000</ID><TranDate>20170620</TranDate><TranTime>null</TranTime><fSeqno>2017061500289342</fSeqno></pub><in><OnlBatF>1</OnlBatF><SettleMode>0</SettleMode><RecAccNo>4000023029200124946</RecAccNo><RecAccNameCN>谬\n" +
                "奸疙非尧架漏亭迹灿件粉帅圳申呵架（歌辐未捷歧）</RecAccNameCN><RecAccNameEN></RecAccNameEN><TotalNum>1</TotalNum><TotalAmt>****</TotalAmt><SignTime>20170630183336000</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>1</iSeqno><PayAccNo>6222024000799000340</PayAccNo><PayAccNameCN>张二</PayAccNameCN><PayAccNameEN></PayAccNameEN><PayBranch>null</PayBranch><Portno>2017061500289292</Portno><ContractNo>BDP300356425</ContractNo><CurrType>001</CurrType><PayAmt>****</PayAmt><UseCode></UseCode><UseCN></UseCN><EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode>022</BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName><CrvouhNo></CrvouhNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>";
        String msg2 = "<?xml version=\"1.0\" encoding=\"GBK\" ?><CMS><eb><pub><TransCode>PERDIS</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>rp.y.4000</ID><TranDate>20170620</TranDate><TranTime>null</TranTime><fSeqno>2017061500289342</fSeqno></pub><in><OnlBatF>1</OnlBatF><SettleMode>0</SettleMode><RecAccNo>4000023029200124946</RecAccNo><RecAccNameCN>璋�\n" +
                "濂哥枡闈炲哀鏋舵紡浜\uE161抗鐏夸欢绮夊竻鍦崇敵鍛垫灦锛堟瓕杈愭湭鎹锋\uE120锛�</RecAccNameCN><RecAccNameEN></RecAccNameEN><TotalNum>1</TotalNum><TotalAmt>****</TotalAmt><SignTime>20170630183336000</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>1</iSeqno><PayAccNo>6222024000799000340</PayAccNo><PayAccNameCN>寮犱簩</PayAccNameCN><PayAccNameEN></PayAccNameEN><PayBranch>null</PayBranch><Portno>2017061500289292</Portno><ContractNo>BDP300356425</ContractNo><CurrType>001</CurrType><PayAmt>****</PayAmt><UseCode></UseCode><UseCN></UseCN><EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode>022</BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName><CrvouhNo></CrvouhNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>";

        requestParser = getXStreamInstance();
        requestParser.processAnnotations(com.shhxzq.bs.mapping.icbc2.CMS.class);

        com.shhxzq.bs.mapping.icbc2.CMS cc = (com.shhxzq.bs.mapping.icbc2.CMS) requestParser.fromXML(msg2);


    }


    /**
     * 测试xml请求
     *
     * @throws Exception
     */
    @Test
    public void testPostXml() throws Exception {
        String pikou = "<?xml version=\"1.0\" encoding=\"GBK\" ?><CMS><eb><pub><TransCode>PERDIS</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>rp.y.4000</ID><TranDate>20170620</TranDate><TranTime>null</TranTime><fSeqno>2017061500289342</fSeqno></pub><in><OnlBatF>1</OnlBatF><SettleMode>0</SettleMode><RecAccNo>4000023029200124946</RecAccNo><RecAccNameCN>谬\n" +
                "奸疙非尧架漏亭迹灿件粉帅圳申呵架（歌辐未捷歧）</RecAccNameCN><RecAccNameEN></RecAccNameEN><TotalNum>1</TotalNum><TotalAmt>****</TotalAmt><SignTime>20170630183336000</SignTime><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>1</iSeqno><PayAccNo>6222024000799000340</PayAccNo><PayAccNameCN>张二</PayAccNameCN><PayAccNameEN></PayAccNameEN><PayBranch>null</PayBranch><Portno>2017061500289292</Portno><ContractNo>BDP300356425</ContractNo><CurrType>001</CurrType><PayAmt>****</PayAmt><UseCode></UseCode><UseCN></UseCN><EnSummary></EnSummary><PostScript></PostScript><Summary></Summary><Ref></Ref><Oref></Oref><ERPSqn></ERPSqn><BusCode>022</BusCode><ERPcheckno></ERPcheckno><CrvouhType></CrvouhType><CrvouhName></CrvouhName><CrvouhNo></CrvouhNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>";
        String queryMsg = "<?xml version=\"1.0\" encoding=\"GBK\" ?><CMS><eb><pub><TransCode>QPERDIS</TransCode><CIS>400090001604411</CIS><BankCode>102</BankCode><ID>rp.y.4000</ID><TranDate>20170621</TranDate><TranTime>170249</TranTime><fSeqno>594A36B00000017A</fSeqno></pub><in><QryfSeqno>2017062100292618</QryfSeqno><QrySerialNo></QrySerialNo><ReqReserved1></ReqReserved1><ReqReserved2></ReqReserved2><rd><iSeqno>1</iSeqno><QryiSeqno>1</QryiSeqno><QryOrderNo></QryOrderNo><ReqReserved3></ReqReserved3><ReqReserved4></ReqReserved4></rd></in></eb></CMS>";

        String duizhang = "<CMS>\n" +
                "  <eb>\n" +
                "    <pub>\n" +
                "      <TransCode>QPERDISTM</TransCode>\n" +
                "      <CIS>400090001604411</CIS>\n" +
                "      <BankCode>102</BankCode>\n" +
                "      <ID>rp.y.4000</ID>\n" +
                "      <TranDate>20170615</TranDate>\n" +
                "      <TranTime>132920</TranTime>\n" +
                "      <fSeqno>2017061500289086</fSeqno>\n" +
                "    </pub>\n" +
                "    <in>\n" +
                "      <BeginTime>20170615000000</BeginTime>\n" +
                "      <EndTime>20170630235959</EndTime>\n" +
                "      <ResultType/>\n" +
                "      <NextTag>31</NextTag>\n" +
                "      <ReqReserved1/>\n" +
                "      <ReqReserved2/>\n" +
                "    </in>\n" +
                "  </eb>\n" +
                "</CMS>";
        org.apache.http.client.methods.HttpPost request = new org.apache.http.client.methods.HttpPost("http://localhost:8080/servlet/ICBCCMPAPIReqServlet?userID=" + "400004059363");

        request.setHeader("Accept-Encoding", "identity");
        List<BasicNameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("Version", "0.0.0.1"));
        param.add(new BasicNameValuePair("BankCode", "102"));
        param.add(new BasicNameValuePair("GroupCIS", "400090001271671"));
        param.add(new BasicNameValuePair("ID", "ncrp.y.4000"));
        param.add(new BasicNameValuePair("Cert", ""));
        //        param.add(new BasicNameValuePair("reqData", msg));
        param.add(new BasicNameValuePair("reqData", getrevFromBASE64(duizhang.getBytes())));
        // Post请求
        // 设置参数
        request.setEntity(new UrlEncodedFormEntity(param, "GB2312"));

        HttpResponse response = httpClient.execute(request);
        String resData = readFromStream2(response.getEntity().getContent(), response.getEntity().getContentLength());
        System.out.println(resData);
        resData = resData.substring(resData.indexOf("reqData=") + 8);

        System.out.println(resData);
        Encrypt enc = new Encrypt();
        resData = enc.base64DecodeByInCode(resData.trim(), "gbk");


        System.out.println(resData);
    }


    private String readFromStream2(InputStream is, long l) throws IOException {
        int i = -1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[(int) l];
        while ((i = is.read(b)) != -1) {
            baos.write(b, 0, i);
        }
        baos.flush();
        String result = baos.toString("GB2312");
        baos.close();
        return result;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

    public String getrevFromBASE64(byte[] s) {
        if (s == null)
            return null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            return encoder.encode(s);
        } catch (Exception e) {
            return null;
        }
    }
}
