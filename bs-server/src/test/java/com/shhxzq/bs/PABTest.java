package com.shhxzq.bs;

import org.junit.Test;

/**
 * Created by houjiagang on 2016/11/4.
 */
public class PABTest {


    @Test
    public void test(){
        String datas = "A0010101010090107980000003600000000007714047       01201610251338102016102500049940                                                                                                              000000            00000000000\n" +
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Result>\n" +
                "  <ThirdVoucher>2016102500049940</ThirdVoucher>\n" +
                "  <AGREE_NO>E000013797</AGREE_NO>\n" +
                "  <BusiType>M8YQD</BusiType>\n" +
                "  <PayType>0</PayType>\n" +
                "  <Currency>RMB</Currency>\n" +
                "  <OthBankFlag>N</OthBankFlag>\n" +
                "  <SrcAccNo>11014501996009</SrcAccNo>\n" +
                "  <TotalNum>1</TotalNum>\n" +
                "  <TotalAmount>0.01</TotalAmount>\n" +
                "  <SettleType>0</SettleType>\n" +
                "  <HOResultSet4047R>\n" +
                "    <SThirdVoucher>2016102500049940</SThirdVoucher>\n" +
                "    <CstInnerFlowNo/>\n" +
                "    <OthAreaFlag/>\n" +
                "    <IdType>1</IdType>\n" +
                "    <IdNo>123426197905180526</IdNo>\n" +
                "    <OppBankName/>\n" +
                "    <OppAccNo>6029070100044038</OppAccNo>\n" +
                "    <OppAccName>平安测试三零零八七</OppAccName>\n" +
                "    <OppBranchId/>\n" +
                "    <Province/>\n" +
                "    <City/>\n" +
                "    <Amount>0.01</Amount>\n" +
                "    <PostScript/>\n" +
                "    <RemarkFCR/>\n" +
                "  </HOResultSet4047R>\n" +
                "</Result>\n";



        String s = datas.substring(222);
        System.out.println(s);


    }

    @Test
    public void test11(){
        String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
        System.out.println(String.valueOf(XML_DECLARATION.length()));

        double amnt = Double.valueOf("0.01").valueOf("0.01");
        System.out.println(amnt);
    }

}
