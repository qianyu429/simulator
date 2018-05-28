package com.shhxzq.bs.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * Created by wanglili on 17/5/4.
 */
public class XStreamUtils {

    public static XStream getXStreamInstance(String encoding) {
        return new XStream(new DomDriver(encoding, new XmlFriendlyNameCoder("_-", "_")));
    }

}
