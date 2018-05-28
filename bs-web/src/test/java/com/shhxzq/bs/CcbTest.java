package com.shhxzq.bs;

import com.shhxzq.bs.mapping.ccb.Tran;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpException;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by houjiagang on 16/7/21.
 */
public class CcbTest {

    private static final String ENCODING = "GBK";

    private XStream tranParser;

    @Test
    public void testParserXml_request_AL0001(){
        String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Tran><Header><txcode>AL0001</txcode><txseq>2016071100103352</txseq><txdate>20160711</txdate><txtime>164147</txtime><tminf></tminf><txsign>bsPAXlxODel3WfU+iZAjpcXwLoL/eQCClPZ2msBwXjWMqPIWgOyyaCTj57tz6PMlYDnUdCQVoMXcQAfWZc/An/IYcQBqY+deEVURtTBbhsJ72Hlp1mjsS0jwVvMvQlU30rDKeY0Nr1K2GrmfYxL2MVpjvBMesEKykeClpVktZOI=</txsign></Header><Body><request><tx_flag>0</tx_flag><shop_no>105290073991097</shop_no><cunt_no>274573715</cunt_no><cert_typ>A</cert_typ><cert_id>440177199903126112</cert_id><cust_nm>练一三</cust_nm><acct_no>4910316030206016</acct_no><mobile>18565882868</mobile><amount></amount><instl_num></instl_num><acct_flag>0</acct_flag></request></Body></Tran>";

        tranParser = getXStreamInstance();
        tranParser.processAnnotations(Tran.class);

        Tran tran = (Tran) tranParser.fromXML(msg);

        System.out.println(tran.getHeader().getTxcode());
        System.out.println(tran.getBody().getRequest().getAcctNo());


    }

    @Test
    public void testParserXml_response_AL0001(){
        String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Tran><Header><txcode>AL0002</txcode><txseq>2016071200103417</txseq><txdate>20160712</txdate><txtime>134214</txtime><tminf></tminf><txsign>Cd+eebZPf2Qa2+Sdf7EWavFnIrQikXKyfjvAVQcTIX0OXnQvI7Re54EnsxWJKAeqKslbDkcu0eJCq491YFLHosg8y/6sKmf4ICrgN9uwMFJBgn3lKSyqMKTl5pblzBpdZsgVaJchyQ5rbSIhaPqOyqHFiZ7F3w4mX/wX4NClgNI=</txsign></Header><Body><tx_flag>0</tx_flag><shop_no>105290073991097</shop_no><cunt_no>274573715</cunt_no><order_no>2016071200103417</order_no><cust_nm>练一三</cust_nm><acct_no>4910316030206016</acct_no><curr_cod>01</curr_cod><curr_iden>0</curr_iden><sms_code></sms_code><amount>0.21</amount><sub_shop_info>105290073991097</sub_shop_info><sub_shop_name>%u4E0A%u6D77%u534E%u4FE1%u8BC1%u5238</sub_shop_name><sub_shop_typ>8999</sub_shop_typ><sub_shop_typnm></sub_shop_typnm><item_info>8999</item_info><item_name></item_name><acct_flag>0</acct_flag><tx_typ>wszf001</tx_typ><site_nm></site_nm><site_url></site_url></Body></Tran>";

        tranParser = getXStreamInstance();
        tranParser.processAnnotations(Tran.class);

        Tran tran = (Tran) tranParser.fromXML(msg);

        System.out.println(tran.getHeader().getTxcode());
        System.out.println(tran.getBody().getResponse().getValidFlag());

    }

    @Test
    public void testSms() throws HttpException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Tran><Header><txcode>AL0003</txcode><txseq>2016071200104400</txseq><txdate>20160713</txdate><txtime>145918</txtime><tminf></tminf><txsign>AIQUBR99e6CUjkkD87ezAm/JltgVrJdjyHCf8fxa16dLP5CzaB5buiMla2cQLir7PbX/tZpoeMlW617AyW1dRIYIMyBNSHjh6dI6ycTGzhAIADpBpvIB1E8GXFsmh1L92n7uJbtl+KqPRFlVYLRw1du7Zem3bRyMOA/g1WWcD0o=</txsign></Header><Body><shop_no>105290073991097</shop_no><ori_date>20160713</ori_date><func_cod>0</func_cod><order_no>2016071200104400</order_no><ori_txseq>2016071300103436</ori_txseq></Body></Tran>";
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Tran><Header><txcode>AL0002</txcode><txseq>2016071200104400</txseq><txdate>20160712</txdate><txtime>134214</txtime><tminf></tminf><txsign>Cd+eebZPf2Qa2+Sdf7EWavFnIrQikXKyfjvAVQcTIX0OXnQvI7Re54EnsxWJKAeqKslbDkcu0eJCq491YFLHosg8y/6sKmf4ICrgN9uwMFJBgn3lKSyqMKTl5pblzBpdZsgVaJchyQ5rbSIhaPqOyqHFiZ7F3w4mX/wX4NClgNI=</txsign></Header><Body><tx_flag>0</tx_flag><shop_no>105290073991097</shop_no><cunt_no>274573715</cunt_no><order_no>2016071200103417</order_no><cust_nm>练一三</cust_nm><acct_no>4910316030206016</acct_no><curr_cod>01</curr_cod><curr_iden>0</curr_iden><sms_code></sms_code><amount>0.21</amount><sub_shop_info>105290073991097</sub_shop_info><sub_shop_name></sub_shop_name><sub_shop_typ>8999</sub_shop_typ><sub_shop_typnm></sub_shop_typnm><item_info>8999</item_info><item_name></item_name><acct_flag>0</acct_flag><tx_typ>wszf001</tx_typ><site_nm></site_nm><site_url></site_url></Body></Tran>";
        String respXml = this.sendPost(String.format("xml=%s&shop_no=%s", xml, "12345"));
        System.out.print(respXml);


    }






    public static String sendPost(String param) throws HttpException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String url = String.format("http://localhost:8080/FastPayment_adapter/FastPayChannelServlet/FastPay");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(Integer.parseInt("30000"));
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw new HttpException();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }
}



