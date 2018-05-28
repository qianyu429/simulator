package com.shhxzq.bs;

import com.shhxzq.bs.common.HttpClientSend;
import com.shhxzq.bs.mapping.ccb.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by houjiagang on 16/7/15.
 */
public class SqdbTest {


    @Test
    public void testSpdb() throws Exception {
        //sms & verify
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
//                "<packet>" +
//                "<transName>KJQY</transName>" +
//                "<Plain>TranAbbr=KJQY|TranType=1|VerifyCode=684940|Merc_id=2016071100102626|MercDtTm=20160711154054|CheckFlag=1|IdType=1|IdNo=44010619750912002X|AccountType=1|Account=6225211080632168|PayCardName=乱码去死21000824415|MobileNo=13820140804|CVV2=|ValidThru=|MercCode=983708160001401|MercUrl=</Plain>" +
//                "<Signature>96dc2ba2fb08bf91ec112ed7b00dad7fd008ca62da8d024831fd31aa38886ea6860c8dd8af2399039c0b8409cbd3f14759a9cecf4f282cb4b6ccf6aff25d44833d172ba02eed47a3e348be8e2162673ece2e3a5729894bf8df81cf0cb61e32daaf6222945aa7e110f966ba00859ecc19623734b9ae1e6aea9bb805883268f6db</Signature>" +
//                "</packet>";

        //pay
//        String sendMsg="<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<packet>\n" +
//                "  <transName>KPER</transName>\n" +
//                "  <Plain>TranAbbr=KPER|Merc_id=2016063000102068|MercDtTm=20160720140700|TermSsn=201600103757|OAcqSsn=|OSttDate=|MercCode=983708160001401|SubMercName=%CE%DE|IdType=1|IdNo=44010619750912002X|AccountType=1|Account=6225211080632168|PayCardName=%C6%D6%B7%A21000824415|MobileNo=13820140804|ValidThru=|IsInstl=|InstlNum=|TermCode=0|TranAmt=100.00|Remark1=|Remark2=|MercUrl=|TransPrincipal=|SubMercFlag=2|SubMercGoodsName=</Plain>\n" +
//                "  <Signature>TranAbbr=KPER|Merc_id=2016063000102068|MercDtTm=20160720140700|TermSsn=201600103757|OAcqSsn=|OSttDate=|MercCode=983708160001401|SubMercName=%CE%DE|IdType=1|IdNo=44010619750912002X|AccountType=1|Account=6225211080632168|PayCardName=%C6%D6%B7%A21000824415|MobileNo=13820140804|ValidThru=|IsInstl=|InstlNum=|TermCode=0|TranAmt=100.00|Remark1=|Remark2=|MercUrl=|TransPrincipal=|SubMercFlag=2|SubMercGoodsName=</Signature>\n" +
//                "</packet>";

        //query
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
//                "<packet>" +
//                "<transName>IQSR</transName>" +
//                "<Plain>MercCode=983708160001401|OTranAbbr=KPER|TermSsn=201600102832</Plain>" +
//                "<Signature>036144007839f89ab0d0a49f15b37887d6c6462a7c2b15d3e3b4fefe3e55087c368167ff6854b00321627357ebeffff9c981460a999893ce3926103a5133f92050d6c1479a3883821be811692dade442a4d718df682e97cadb828a1f6204e23e4d03bee135a6127586f9e643cc5ba1b7fc674b41474540700c4420ba9fb4d96a</Signature>" +
//                "</packet>";
        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<packet>\n" +
                "  <transName>IQSR</transName>\n" +
                "  <Plain>MercCode=983708160001401|OTranAbbr=DPER|TermSsn=201700143366</Plain>\n" +
                "  <Signature>225ffd51be1c7f37220473efd831751f279d79e436db0190cc8d57f60730dc8bc5226bedf5c88ed16b7d2dfd2bdda44202a6ed6db99c203333ff105f84b354ef4b20dcac57e0a616c99783e2d44e475b6859af1049721da7863a933a33a907ac2d9c1dbf3ff3919fc39194a376b13f0866e6be164190da7e58c28edc9977a57b</Signature>\n" +
                "</packet>\n";


        /**
         *
         * 银企
         */
        //8801
//        String sendMsg = "<?xml version='1.0' encoding='GB2312'?>" +
//                "<packet>" +
//                "<head>" +
//                "<transCode>8801</transCode>" +
//                "<signFlag>1</signFlag>" +
//                "<masterID>2000040752</masterID>" +
//                "<packetID>201600102840</packetID>" +
//                "<timeStamp>2016-07-11 16:04:41</timeStamp>" +
//                "</head>" +
//                "<body>" +
//                "<signature>" +
//                "<body><elecChequeNo>201600102840</elecChequeNo>" +
//                "<authMasterID></authMasterID>" +
//                "<acctNo>6224080602781</acctNo>" +
//                "<acctName>浦发2000046127</acctName>" +
//                "<bespeakDate></bespeakDate>" +
//                "<payeeAcctNo>6225160100120946</payeeAcctNo>" +
//                "<payeeName>浦发1044465651</payeeName>" +
//                "<payeeType>1</payeeType>" +
//                "<payeeBankName></payeeBankName>" +
//                "<payeeAddress></payeeAddress>" +
//                "<amount>14.07</amount>" +
//                "<sysFlag>0</sysFlag>" +
//                "<remitLocation></remitLocation>" +
//                "<note>SB_201600102840</note>" +
//                "<payeeBankSelectFlag></payeeBankSelectFlag>" +
//                "<payeeBankNo></payeeBankNo>" +
//                "</body>" +
//                "</signature>" +
//                "</body>" +
//                "</packet>";


        //EG01
//        String sendMsg ="<?xml version='1.0' encoding='GB2312'?>" +
//                "<packet>" +
//                "<head>" +
//                "<transCode>EG01</transCode>" +
//                "<signFlag>1</signFlag>" +
//                "<masterID>2000040752</masterID>" +
//                "<packetID>2016071100102842</packetID>" +
//                "<timeStamp>2016-07-11 16:11:57</timeStamp>" +
//                "</head>" +
//                "<body>" +
//                "<signature>" +
//                "<body><acctNo>6224080608888</acctNo>" +
//                "<PayeeBankNo>102100099996</PayeeBankNo>" +
//                "<payeeAcctNo>6223635001004485218</payeeAcctNo>" +
//                "<payeeName>钟煦镠</payeeName>" +
//                "<payeeType>1</payeeType>" +
//                "<amount>14.07</amount>" +
//                "<electronNumber>2016071100108888</electronNumber>" + ///
//                "<note>DB_2016071100102842</note>" +
//                "</body>" +
//                "</signature>" +
//                "</body>" +
//                "</packet>";

        //8804
//        String sendMsg = "<?xml version='1.0' encoding='GB2312'?>" +
//                "<packet>" +
//                "<head>" +
//                "<transCode>8804</transCode>" +
//                "<signFlag>1</signFlag>" +
//                "<masterID>2000040752</masterID>" +
//                "<packetID>57835C83000001D41</packetID>" +
//                "<timeStamp>2016-07-11 16:04:41</timeStamp>" +
//                "</head>" +
//                "<body>" +
//                "<signature>" +
//                "<body><elecChequeNo>201600102841</elecChequeNo>" +
//                "<acctNo>6224080602781</acctNo>" +
//                "<beginDate>20160710</beginDate>" +
//                "<endDate>20160712</endDate>" +
//                "<acceptNo></acceptNo>" +
//                "<serialNo></serialNo>" +
//                "<queryNumber>20</queryNumber>" +
//                "<beginNumber>1</beginNumber>" +
//                "<singleOrBatchFlag>0</singleOrBatchFlag>" +
//                "</body>" +
//                "</signature>" +
//                "</body>" +
//                "</packet>";


        //EG30
//        String sendMsg = "<?xml version='1.0' encoding='GB2312'?>" +
//                "<packet>" +
//                "<head>" +
//                "<transCode>EG30</transCode>" +
//                "<signFlag>1</signFlag>" +
//                "<masterID>2000040752</masterID>" +
//                "<packetID>57835C88000002D4</packetID>" +
//                "<timeStamp>2016-07-11 16:11:57</timeStamp>" +
//                "</head>" +
//                "<body>" +
//                "<signature>" +
//                "<body><businessNo></businessNo>" +
//                "<electronNumber>2016071100108888</electronNumber>" +
//                "<directMasterID>2000040752</directMasterID>" +
//                "</body>" +
//                "" +
//                "</signature>" +
//                "</body>" +
//                "</packet>";

//        //当天充值对账
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<packet>\n" +
//                "  <transName>BQSR</transName>\n" +
//                "  <Plain>MercCode=983708160001401|OTranAbbr=DPER|BTermSsn=201600011288|ETermSsn=201600011303</Plain>\n" +
//                "  <Signature>989911efe57fffdf2ad8696e9e0b0c05bb2673279650b1a4b4a9b32bf0a8bbdf22644a713d9ab5e63466ae8952" +
//                "086234da8ef5e50b002761e3b1aa9f44a0187c92490233791753fcd5deb8fb56b104b87c1a59a8f6207b79d7749c1b8162e69c7" +
//                "e6599f31c17c553446b4f358e66dc6f4417f04731349218a5034d780a0e58e6</Signature>\n" +
//                "</packet>";
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<packet>\n" +
//                "  <transName>BQSR</transName>\n" +
//                "  <Plain>MercCode=983708160001401|OTranAbbr=DPER|BTermSsn=201700113390|ETermSsn=201700113392</Plain>\n" +
//                "  <Signature>236b31bf5229f80cac52a99e646ab6ab63d6850d1c0b06df40036d8762264850291b34ca69b2d23684f5ce8318520a9234e6c263606f1fe01f58887128b7c7a5e92ba2dde73d009734a4c8adaa909d006ff8a3c93c0bb8a836a9601e608b2a973018b80589973b659de297e1a92cec92219862bb7162ceb6e923f7275fc0ff94</Signature>\n" +
//                "</packet>\n";


//        //非当天充值对账
//        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<packet>\n" +
//                "  <transName>IDFR</transName>\n" +
//                "  <Plain>MercCode=983708160001401|OSttDate=20160720|SetFType=1</Plain>\n" +
//                "  <Signature>4d84b39fe947ed539136a32654a27df4810038907b1da4579bb579137d3e84f9ba710f4544a30c9dca45976df82e9d1d94bf031e06d6fcf543ed425317bc417ccf7b7472ed8a3e9be416f315cc0bb1c940bd2b0017f7f59118c3303cebf13872538ec417882c88b2505becd5c32125f2bd802d209ef0d6754d03d2a57dc1f4c8</Signature>\n" +
//                "</packet>\n";

//        //DPER
//       String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
//                "<packet>\n" +
//                "  <transName>DPER</transName>\n" +
//                "  <Plain>TranAbbr=DPER|Merc_id=2017030200113219|MercDtTm=20170303173504|TermSsn=201700123366|OAcqSsn=|OSttDate=|MercCode=983708160001401|TermCode=00000000|TranAmt=123.01|Remark1=|Remark2=|MercUrl=|SubMercFlag=2|SubMercName=N|SubMercGoodsName=</Plain>\n" +
//                "  <Signature>25f5c03665103fb2bf2d37b5a461cc6f5efb1c3ad5188fc96366678f64b50f500ac61bf3b30e1d0b4aab5a6dd612481f7614d681aa7de38163d154842b83322149f7291755ce854034eab60245ce1b0e790ccc5d6ed1575ecdb9e3269fabeefe9681b0f0aaa16c9a9bcb33e6c0814dce152319a6f1ee919587e5811fe81d4188</Signature>\n" +
//                "</packet>\n";

        String url = "http://localhost:8080/payment/main";
        HttpClientSend http = new HttpClientSend();
        String resultxml = http.post(sendMsg, url);
        System.out.print("resultXml is " + resultxml);

    }


    @Test
    public void testCompany() throws Exception {
        String sendMsg = "<?xml version='1.0' encoding='GB2312'?>\n" +
                "<packet>\n" +
                "<head>\n" +
                "<transCode>9004</transCode>\n" +
                "<signFlag>1</signFlag>\n" +
                "<masterID>2000040752</masterID>\n" +
                "<packetID>578443F8000002D41</packetID>\n" +
                "<timeStamp>2016-07-12 09:13:02</timeStamp>\n" +
                "</head>\n" +
                "<body>\n" +
                "<signature>\n" +
                "<body><acctNo>6224080602781</acctNo>\n" +
                "<beginDate>20160720</beginDate>\n" +
                "<endDate>20160712</endDate>\n" +
                "<queryPage>1</queryPage>\n" +
                "<fileName></fileName>\n" +
                "</body>\n" +
                "</signature>\n" +
                "</body>\n" +
                "</packet>\n";


        PostMethod post = null;
        HttpConnection httpConn = null;
        String resp = null;


        post = new PostMethod();
        post.addRequestHeader("User-Agent", "NSTC");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addRequestHeader("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        post.addRequestHeader("Connection", "keep-alive");
        post.setPath("http://10.199.101.162:8080/payment/main1");
        post.setRequestEntity(new StringRequestEntity(sendMsg));

        httpConn = new HttpConnection("10.199.101.162", Integer.parseInt("8080"));
        httpConn.open();
        int retCode = post.execute(new HttpState(), httpConn);
        if (retCode != 200) {
            throw new Exception("http发送返回码未知retCode[" + retCode + "]");
        }
        resp = readStream(post.getResponseBodyAsStream());
        System.out.print(resp);

    }


    private static String readStream(InputStream in) throws Exception {
        StringBuilder sub = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
        String line = "";
        while ((line = br.readLine()) != null) {
            sub.append(line);
            sub.append("\n");
        }
        return sub.toString();
    }
}
