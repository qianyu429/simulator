package com.shhxzq.bs;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by houjiagang on 2016/10/27.
 */
public class CMBTest {

    @Test
    public void test(){
//        String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<CMBSDKPGK> \n" +
//                "  <INFO> \n" +
//                "    <FUNNAM>AgentRequest</FUNNAM>  \n" +
//                "    <DATTYP>2</DATTYP>  \n" +
//                "    <LGNNAM>PAY1</LGNNAM> \n" +
//                "  </INFO>  \n" +
//                "  <SDKATSRQX> \n" +
//                "    <EPTDAT>20161027</EPTDAT>  \n" +
//                "    <BUSCOD>N03020</BUSCOD>  \n" +
//                "    <BUSMOD>00001</BUSMOD>  \n" +
//                "    <TRSTYP>BYBC</TRSTYP>  \n" +
//                "    <DBTACC>755900008010306</DBTACC>  \n" +
//                "    <BBKNBR>69</BBKNBR>  \n" +
//                "    <SUM>1000.9</SUM>  \n" +
//                "    <TOTAL>1</TOTAL>  \n" +
//                "    <CCYNBR>10</CCYNBR>  \n" +
//                "    <YURREF>TEST201501290923</YURREF>  \n" +
//                "    <MEMO>代发工资测试</MEMO>  \n" +
//                "    <DMANBR>000001</DMANBR>  \n" +
//                "    <GRTFLG>Y</GRTFLG> \n" +
//                "  </SDKATSRQX>  \n" +
//                "  <SDKATDRQX> \n" +
//                "    <ACCNBR>075512000038</ACCNBR>  \n" +
//                "    <CLTNAM>测试</CLTNAM>  \n" +
//                "    <TRSAMT>1000.9</TRSAMT>  \n" +
//                "    <TRSDSP>工资</TRSDSP> \n" +
//                "  </SDKATDRQX> \n" +
//                "</CMBSDKPGK>\n";
        String data = "<?xml version='1.0' encoding = 'GBK'?><CMBSDKPGK><INFO><FUNNAM>GetAgentInfo</FUNNAM><DATTYP>2</DATTYP><LGNNAM>银企直连专用普通1</LGNNAM></INFO><SDKATSQYX><ENDDAT>20151118</ENDDAT><DATFLG>A</DATFLG><OPRLGN></OPRLGN><YURREF>2016102700110025</YURREF><BUSCOD>N03020</BUSCOD><BGNDAT>20151117</BGNDAT></SDKATSQYX></CMBSDKPGK>";
        OutputStream os = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL("http://127.0.0.1:8080");
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            os = conn.getOutputStream();
            os.write(data.getBytes("GBK"));
            os.flush();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null ){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null ){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(sb.toString());

    }
}
