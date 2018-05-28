package com.shhxzq.bs.mapping.cmb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/10/27.
 */
@Setter
@Getter
@XStreamAlias("CMBSDKPGK")
public class CMBPack {

    @XStreamAlias("INFO")
    private Info info;

    @XStreamAlias("SDKATSRQX")
    private SDKATSRQX sdkatsrqx;

    @XStreamAlias("SDKATDRQX")
    private SDKATDRQX sdkatdrqx;

    @XStreamAlias("NTREQNBRY")
    private NTREQNBRY ntreqnbry;

    @XStreamAlias("SDKATSQYX")
    private SDKATSQYX sdkatsqyx;

    @XStreamAlias("NTQATSQYZ")
    private NTQATSQYZ ntqatsqyz;







    @Setter
    @Getter
    public static class Info{

        @XStreamAlias("FUNNAM")
        private String funNam;

        @XStreamAlias("DATTYP")
        private String datTyp;

        @XStreamAlias("LGNNAM")
        private String lgnNam;

        @XStreamAlias("ERRMSG")
        private String errMsg;

        @XStreamAlias("RETCOD")
        private String retCod;
    }


    @Setter
    @Getter
    public static class SDKATSRQX{

        @XStreamAlias("EPTDAT")
        private String eptDat;

        @XStreamAlias("BUSCOD")
        private String busCod;

        @XStreamAlias("BUSMOD")
        private String busMod;

        @XStreamAlias("TRSTYP")
        private String trsTyp;

        @XStreamAlias("DBTACC")
        private String dbtAcc;

        @XStreamAlias("BBKNBR")
        private String bbkNbr;

        @XStreamAlias("SUM")
        private String sum;

        @XStreamAlias("TOTAL")
        private String total;

        @XStreamAlias("CCYNBR")
        private String ccyNbr;

        @XStreamAlias("YURREF")
        private String yurRef;

        @XStreamAlias("MEMO")
        private String memo;

        @XStreamAlias("DMANBR")
        private String dmaNbr;

        @XStreamAlias("GRTFLG")
        private String grtFlg;
    }




    @Setter
    @Getter
    public static class SDKATDRQX{

        @XStreamAlias("ACCNBR")
        private String accNbr;

        @XStreamAlias("CLTNAM")
        private String cltNam;

        @XStreamAlias("TRSAMT")
        private String trsAmt;

        @XStreamAlias("TRSDSP")
        private String trsDsp;

    }



    @Setter
    @Getter
    public static class NTREQNBRY{

        @XStreamAlias("REQNBR")
        private String reqNbr;

        @XStreamAlias("RSV50Z")
        private String rsv50z;
    }



    @Setter
    @Getter
    public static class SDKATSQYX{

        @XStreamAlias("BUSCOD")
        private String busCod;

        @XStreamAlias("BGNDAT")
        private String bgnDat;

        @XStreamAlias("ENDDAT")
        private String endDat;

        @XStreamAlias("YURREF")
        private String yurRef;

        @XStreamAlias("DATFLG")
        private String datFlg;

        @XStreamAlias("OPRLGN")
        private String oprLgn;
    }



    @Setter
    @Getter
    public static class NTQATSQYZ{

        @XStreamAlias("REQNBR")
        private String reqNbr;

        @XStreamAlias("BUSCOD")
        private String busCod;

        @XStreamAlias("C_BUSCOD")
        private String cbusCod;

        @XStreamAlias("BUSMOD")
        private String busMod;

        @XStreamAlias("OPRDAT")
        private String oprDat;

        @XStreamAlias("EPTDAT")
        private String eptDat;

        @XStreamAlias("EPTTIM")
        private String eptTim;

        @XStreamAlias("BBKNBR")
        private String bbkNbr;

        @XStreamAlias("C_BBKNBR")
        private String cbbkNbr;

        @XStreamAlias("ACCNBR")
        private String accNbr;

        @XStreamAlias("ACCNAM")
        private String accNam;

        @XStreamAlias("TRSNUM")
        private String trsNum;

        @XStreamAlias("TOTAMT")
        private String totAmt;

        @XStreamAlias("SUCNUM")
        private String sucNum;

        @XStreamAlias("SUCAMT")
        private String sucAmt;

        @XStreamAlias("CCYNBR")
        private String ccyNbr;

        @XStreamAlias("C_CCYNBR")
        private String cccyNbr;

        @XStreamAlias("TRSTYP")
        private String trsTyp;

        @XStreamAlias("C_TRSTYP")
        private String ctrsTyp;

        @XStreamAlias("NUSAGE")
        private String nUsage;

        @XStreamAlias("YURREF")
        private String yurRef;

        @XStreamAlias("REQSTA")
        private String reqSta;

        @XStreamAlias("C_REQSTA")
        private String creqSta;

        @XStreamAlias("RTNFLG")
        private String rtnFlg;

        @XStreamAlias("C_RTNFLG")
        private String crtnFlg;


    }


}
