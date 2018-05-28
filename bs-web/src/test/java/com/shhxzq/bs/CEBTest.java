package com.shhxzq.bs;

import com.shhxzq.bs.common.HttpClientSend;
import com.shhxzq.bs.mapping.ceb.MessageSuit;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.junit.Test;

/**
 * Created by houjiagang on 16/9/5.
 */
public class CEBTest {

    private static final String ENCODING = "utf-8";

    private XStream messageParser;

    @Test
    public void testCEB() throws Exception {

        String sendMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
                "<Transaction>\n" +
                "    <SystemHead>\n" +
                "        <Language>zh_CN</Language>\n" +
                "        <Encodeing></Encodeing>\n" +
                "        <Version></Version>\n" +
                "        <ServiceName></ServiceName>\n" +
                "        <CifNo>2000729524</CifNo>\n" +
                "        <UserID>014</UserID>\n" +
                "        <SyMacFlag></SyMacFlag>\n" +
                "        <MAC></MAC>\n" +
                "        <SyPinFlag></SyPinFlag>\n" +
                "        <PinSeed></PinSeed>\n" +
                "        <LicenseId></LicenseId>\n" +
                "        <Flag></Flag>\n" +
                "        <Note></Note>\n" +
                "    </SystemHead>\n" +
                "    <TransHead>\n" +
                "        <TransCode>b2e004003</TransCode>\n" +
                "        <BatchID>2223333</BatchID>\n" +
                "        <JnlDate>20030809</JnlDate>\n" +
                "        <JnlTime>144534</JnlTime>\n" +
                "    </TransHead>\n" +
                "    <TransContent>\n" +
                "        <ReqData>\n" +
                "            <ClientPatchID>200385564520061218000000010001</ClientPatchID>\n" +
                "            <ClientBchID>20038556452006121800000002</ClientBchID>\n" +
                "            <ClientPchID>3639174</ClientPchID>\n" +
                "        </ReqData>\n" +
                "    </TransContent>\n" +
                "</Transaction>";
        String url = "http://localhost:8080/ent/b2e004003.do";
        HttpClientSend http = new HttpClientSend();
        String resultxml = http.post(sendMsg, url);

        System.out.println(resultxml);
    }


    @Test
    public void testMessageSuite() {
        messageParser = getXStreamInstance();
        messageParser.processAnnotations(MessageSuit.class);

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<MessageSuit>\n" +
                "    <Message id=\"20160819164745\">\n" +
                "        <Plain id=\"OPReq\">\n" +
                "            <transId>OPReq</transId>\n" +
                "            <merId>370310000094</merId>\n" +
                "            <serialNo>20160819164745</serialNo>\n" +
                "            <date>20160819 16:47:45</date>\n" +
                "            <signNo>370310000094TP20160819E1000016720800000000000</signNo>\n" +
                "            <amount>2133</amount>\n" +
                "            <currency>156</currency>\n" +
                "            <url>www.shhxzq.com</url>\n" +
                "            <merSecName/>\n" +
                "            <productInfo/>\n" +
                "        </Plain>\n" +
                "        <ds:Signature\n" +
                "            xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "            <ds:SignedInfo>\n" +
                "                <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>\n" +
                "                <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n" +
                "                <ds:Reference URI=\"#OPReq\">\n" +
                "                    <ds:Transforms>\n" +
                "                        <ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
                "                    </ds:Transforms>\n" +
                "                    <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>\n" +
                "                    <ds:DigestValue>fNfZqM0OpqA6/oe69R0VJ7qFPio=</ds:DigestValue>\n" +
                "                </ds:Reference>\n" +
                "            </ds:SignedInfo>\n" +
                "            <ds:SignatureValue>RxBUBqWnqsu3NQgzQwwxQNTkUERzvg66GeHJY+Om1bC2e+Z4nasUf/CPvwyl+z4e844gRHml9BqG DHrrYEOpDjaMIzFIA4BgXLX96ekVZz3jVZn5niChkBHsTobEgWZnQgo0Np1FeKIS7afbyImriZJ3 Cn+lAUW4OpomZqU40vA=</ds:SignatureValue>\n" +
                "        </ds:Signature>\n" +
                "    </Message>\n" +
                "</MessageSuit>\n";

        MessageSuit ms = (MessageSuit) messageParser.fromXML(xml);

        System.out.println("12344");

    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver(ENCODING, new XmlFriendlyNameCoder("_-", "_")));
    }

    @Test
    public void testMessageSuite1() {
        messageParser = getXStreamInstance();
        messageParser.processAnnotations(MessageSuit.class);

        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<MessageSuit>\n" +
                "    <Message id=\"2017060800001942\">\n" +
                "        <Plain id=\"MSCReq\">\n" +
                "            <transId>MSCReq</transId>\n" +
                "            <merId>370310000094</merId>\n" +
                "            <signNo>5678909876545678987654345</signNo>\n" +
                "            <phone></phone>\n" +
                "        </Plain>\n" +
                "    </Message>\n" +
                "</MessageSuit>";
        MessageSuit ms = (MessageSuit) messageParser.fromXML(xml);
    }


    @Test
    public void testMSC() throws Exception {

        String sendMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "\n" +
                "<MessageSuit>\n" +
                "    <Message id=\"2017060800001942\">\n" +
                "        <Plain id=\"MSCReq\">\n" +
                "            <transId>MSCReq</transId>\n" +
                "            <merId>370310000094</merId>\n" +
                "            <signNo>5678909876545678987654345</signNo>\n" +
                "            <phone></phone>\n" +
                "        </Plain>\n" +
                "    </Message>\n" +
                "</MessageSuit>";
        String url = "http://localhost:8080/agreeEpayper/connect.do";
        HttpClientSend http = new HttpClientSend();
        String resultxml = http.post(sendMsg, url);

        System.out.println(resultxml);
    }
}
