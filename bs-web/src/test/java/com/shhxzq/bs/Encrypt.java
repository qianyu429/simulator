package com.shhxzq.bs;

/******************************************
 *  这个类主要用于对密码进行加,由于加密理论讲是不加逆的
 *MD5算法.
 *  加密的方法为: addPwd()
 *  校验密码的方法为:checkPassword()
 *******************************************/

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;


public class Encrypt {

	protected static final Log log = LogFactory.getLog(Encrypt.class.
            getName());
	
    /**********************************************
     *解密方法:
     *参数: str_pwds 用户录入的密码
     *      str_passwd 从数据库查查出的用户加密的口令文本
     *
     *返回: boolean TRUE 正确 FALSE 为失败
     *
     ************************************************/

    public boolean checkPassword(String str_pwds, String str_passwd) {
        try {
            //String myinfo=str_pwds+"0";
            String myinfo = str_pwds;
            MessageDigest alga = MessageDigest.getInstance("MD5");
            //MessageDigest alga=MessageDigest.getInstance("SHA-1");
            alga.update(myinfo.getBytes());

            byte[] digesta = alga.digest();

            //MessageDigest algb=MessageDigest.getInstance("SHA-1");
            String ls_str = byte2hex(digesta);

            return ls_str.equals(str_passwd);
        }
        catch (java.security.NoSuchAlgorithmException ex) {
        	log.error("没有这个加密算法请检查JDK版本",ex);
        }
        return false;
    }

    /**********************************************
     *
     *将加密后的数据(二进制转为16进制的字符)
     *
     *
     *
     *
     ************************************************/
    public String byte2hex(byte[] b) { //二行制转字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            }
            else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs;
            }
        }
        //return hs.toUpperCase();
        return hs;
    }


    /**********************************************
     *解密方法:
     *参数: str_pwds 要加密的明文
     *
     *
     *返回: 加密过的密文
     *
     ************************************************/
    public String addPwd(String str_pwds) {
        try {
            //String myinfo=str_pwds+"0";
            String myinfo = str_pwds;
            MessageDigest alga = MessageDigest.getInstance("MD5");
            //MessageDigest alga=MessageDigest.getInstance("SHA-1");
            alga.update(myinfo.getBytes());

            byte[] digesta = alga.digest();

            //MessageDigest algb=MessageDigest.getInstance("SHA-1");
            String ls_str = byte2hex(digesta);

            return ls_str;
        }
        catch (java.security.NoSuchAlgorithmException ex) {
        	log.error("没有这个加密算法请检查JDK版本",ex);
        }
        return null;
    }


    /**********************************************
     *加密口令:
     *参数: str_pwds 要加密的明文
     *
     *
     *返回: 加密过的密文
     *
     ************************************************/
    /*public String passwordEncrypt(String str_pwds) {
        return addPwd(str_pwds);
    }*/
    public String passwordEncrypt(String originalPwd){
    	StringBuffer returnPsw = new StringBuffer();
//		byte  szKey[] = {0x28, 0x45, -126, 0x10, 0x23, 0x47, 0x36, 0x21};
		byte  szKey[] = {0x18, 0x46, -126, 0x20, 0x41, 0x57, 0x66, 0x13};
        //byte [] szMessage = "999999".getBytes();//"12345678".getBytes();
        byte [] szMessage = originalPwd.getBytes();
        
        long res = 0L;
        Blowfish t = new Blowfish();
        byte rrr = -126;
        
        t.BlowfishKeyInit(szKey,szKey.length);
        int e2= 0xe2;
        int i,j,k;
        int len=szMessage.length;
        int rsLen = len%8;
        byte [] sdMsg;
        if (rsLen!=0) {
        	sdMsg = new byte[len-rsLen+8];
        	for (i=0;i<len-rsLen+8;i++) {
        		if (i<len) 
        		    sdMsg[i] = szMessage[i];
        		else  
        		    sdMsg[i] = 0x20;
        	}
        	szMessage = sdMsg;
        	len = szMessage.length;
        }
        
        int []X = new int[2];
        byte []B4 = new byte[4];
        int []A4 = new int[4];
        for (i=0;i<len;i+=8){
        	  for(j=0;j<4;j++)B4[j]=szMessage[i+j];
            X[0]=t.get4ByteSum(B4);
            for(j=0;j<4;j++)B4[j]=szMessage[i+j+4];
            X[1]=t.get4ByteSum(B4);
            t.BlowfishEncipher(X);
            t.getUnsignedInt(X[0],A4);
            for(j=0;j<4;j++)szMessage[i+j]=(byte)A4[j];
            t.getUnsignedInt(X[1],A4);
            for(j=0;j<4;j++)szMessage[i+j+4]=(byte)A4[j];
        }
        for (i=0;i<szMessage.length;i++){
            res <<= 8;
            res += szMessage[i];
        }
        
        for(i=0;i<len;i++){
        	String psw = t.getUnsignChar(szMessage[i])+"";
        	while(psw.length()<3){
        		psw = "0"+psw;
        	}
        	returnPsw.append(psw);
        }
        
        return returnPsw.toString();
		
	}
    

    /**********************************************
     *加密口令:
     *参数: str_pwds 要加密的明文
     *
     *
     *返回: 加密过的密文
     *
     ************************************************/
    public String passwordEncryptUpper(String str_pwds) {
        return addPwd(str_pwds).toUpperCase();
    }


    /**********************************************
     *检查口令是否相等:
     *参数: str_pwds 口令的明文
     *      str_passwd 口令的密文在数据库在保存
     *
     *返回: 加密过的密文
     *
     ************************************************/
    public boolean isPass(String str_pwds, String str_passwd) {
        return checkPassword(str_pwds, str_passwd);
    }


    public String base64Decode(String password)
            throws Exception {
        BASE64Decoder dc = new BASE64Decoder();
        String tmpStr = new String(dc.decodeBuffer(password));
        return tmpStr.substring(0, tmpStr.length() - 1);
    }
    
    public String base64DecodeByInCode(String password , String encode)
            throws Exception {
        BASE64Decoder dc = new BASE64Decoder();
        String tmpStr = new String(dc.decodeBuffer(password),encode);
        return tmpStr.substring(0, tmpStr.length() - 1);
    }

    public String base64Encode(String password)
            throws Exception {
        BASE64Encoder en = new BASE64Encoder();
        password = password + 1;
        return en.encode(password.getBytes());
    }
/**-----------------------------------------########################################-----------*/

    public String stringToHexString(String intputString) throws Exception
    {
        if (intputString == null)
        {
            throw new Exception("调用stringToHexString时出错，原因：intputString为空！");
        }

        byte[] byteInputStrings = intputString.getBytes();
        byte byteInputString;

        String tempString = "";
        StringBuffer sbReturnString = new StringBuffer();

        for (int i = 0; i < byteInputStrings.length; i++)
        {
            tempString = "";
            byteInputString = byteInputStrings[i];

            tempString = Integer.toHexString(byteInputString);

            if (tempString.length() < 2)
            {
                tempString = "0" + tempString;
            }

            tempString = tempString.substring(tempString.length() - 2, tempString.length());

            sbReturnString.append("%");
            sbReturnString.append(tempString);
        }

        return sbReturnString.toString().toUpperCase();
    }
}
