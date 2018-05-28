package com.shhxzq.bs;

import com.shhxzq.bs.mapping.cmb.*;
import org.junit.Test;

/**
 * Created by houjiagang on 16/8/15.
 */
public class CmbTest {

    @Test
    public void testCMb() throws Exception {
        String str = "CMBA0255BKQD201610271128521381610270000515                                                          CMBCHINA  7B66ABCC613E32172016102700109975    ************1595    0574                                                                                                                                                                           D                                       ";
        PkgHeader pkgHeader = new PkgHeader(str);
        String sub = str.substring(126);

        PkgBodyCMDBReq response = new PkgBodyCMDBReq(sub);
        System.out.println(str.substring(126).length());

    }

    @Test
    public void test11(){
        String str = "000051";
        System.out.println(Integer.parseInt(str));
    }
}
