package com.shhxzq.bs;

import com.shhxzq.bs.mapping.spdb.Request;
import com.shhxzq.bs.mapping.spdb.Response;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by houjiagang on 16/7/15.
 */
public class XstreamTest {

    private XStream requestParser;

    @Test
    public void testXstream() {

        requestParser = getXStreamInstance();
        requestParser.processAnnotations(Response.class);

        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                "<packet>" +
                "<transName>KJQY</transName>" +
                "<Plain>TranAbbr=KJQY|TranType=1|VerifyCode=684940|Merc_id=2016071100102626|MercDtTm=20160711154054|CheckFlag=1|IdType=1|IdNo=44010619750912002X|AccountType=1|Account=6225211080632168|PayCardName=%C6%D6%B7%A21000824415|MobileNo=13820140804|CVV2=|ValidThru=|MercCode=983708160001401|MercUrl=</Plain>" +
                "<Signature>1939f8ec0f275f075ac3fd7e8b3fe5a00008600c5f71332a1bb16182a92ac91c3aafb0b79311f93fdbe35868c484767ed849c0c4a7acf8fecff410a4adde0150edabe334e03a5c2fdc3c2de5f751ac6c34be001832c1e6bb0637f259f5d9cd4acf1eeb539e29c3644a65fc41e206d56ab4e1ac4f9b09e71d541820ead13e442b</Signature>" +
                "</packet>";

        Response request = (Response) requestParser.fromXML(xml);


        System.out.print(request.getPlain());


    }


    private XStream getXStreamInstance() {
        return new XStream(new DomDriver("GBK", new XmlFriendlyNameCoder("_-", "_")));
    }
}
