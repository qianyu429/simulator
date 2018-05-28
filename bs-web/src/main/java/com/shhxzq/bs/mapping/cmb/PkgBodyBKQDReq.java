package com.shhxzq.bs.mapping.cmb;


/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * <p>Remark: 客户签定电子协议 银行请求</p>
 *
 * @author 
 * @version 1.0
 */
public class PkgBodyBKQDReq extends PkgCommon
{
    private String custCode; //客户号（协议号）
    private String acctno; //账号(安全考虑，一部分用*代替)
    private String areaCode; //账户地区
    private String subCorpNo; //子商户号/产品类型
    private String transPassword; //支付密码
    private String acctnm; //姓名
    private String sex; //性别
    private String birthday; //出生日期
    private String idtp; //证件类型
    private String idno; //证件号码
    private String mobileno; //手机号码
    private String email; //电子邮件
    private String hometel; //家庭电话
    private String officetel; //办公电话
    private String faxno; //传真号码
   
    private String daylim; //客户每日限额
    private String monthlim; //客户每月限额
    private String province; //所在省
    private String city; //所在市
    private String addr; //帐单地址
    private String postcode; //邮政编码
    private String memo; //备注字段

    private Integer pkgPkgBodyLen; //包体长度

    public PkgBodyBKQDReq()
    {
        //初始化变量
        Init();
    }

    public PkgBodyBKQDReq(String inputStr) throws Exception
    {
        if (inputStr == null)
        {
            throw new Exception("报文返回字符串为空！");
        }

        //初始化变量
        Init();


        /**
         * 赋值并转换成byte数组
         */
        bankMessage = inputStr;
        byteBankMessage = inputStr.getBytes(); //转换成byte数组

        /**
         * 初始化计数器和银行返回信息长度
         */
        lenthCount = 0;
        byteBankMessageLen = byteBankMessage.length;

        /**
         * 注意，以下一定要按照招行文档的顺序，否则数据分析肯定报错！！
         */
        custCode = getFieldValue(custCode);//客户号
        acctno = getFieldValue(acctno);//账号(安全考虑，一部分用*代替)
        areaCode = getFieldValue(areaCode);//账户地区
        subCorpNo = getFieldValue(subCorpNo);//子商户号/产品类型
        transPassword = getFieldValue(transPassword);//支付密码
        acctnm = getFieldValue(acctnm);//姓名
        sex = getFieldValue(sex);//性别
        birthday = getFieldValue(birthday);//出生日期
        idtp = getFieldValue(idtp);//证件类型
        idno = getFieldValue(idno);//证件号码
        mobileno = getFieldValue(mobileno);//手机号码
        email = getFieldValue(email);//电子邮件
        memo = getFieldValue(memo);//备注字段
        
        hometel = getFieldValue(hometel);//家庭电话
        officetel = getFieldValue(officetel);//办公电话
        faxno = getFieldValue(faxno);//传真号码
        
        daylim = getFieldValue(daylim);//客户每日限额
        monthlim = getFieldValue(monthlim);//客户每月限额
        province = getFieldValue(province);//所在省
        city = getFieldValue(city);//所在市
        addr = getFieldValue(addr);//帐单地址
        postcode = getFieldValue(postcode);//邮政编码
        
        
    }

    public void Init()
    {
        custCode = new String(new char[20]); //客户号（协议号）
        acctno = new String(new char[20]); //账号(安全考虑，一部分用*代替)
        areaCode = new String(new char[4]); //账户地区
        subCorpNo = new String(new char[5]); //子商户号/产品类型
        transPassword = new String(new char[6]); //支付密码
        acctnm = new String(new char[60]); //姓名
        sex = new String(new char[1]); //性别
        birthday = new String(new char[8]); //出生日期
        idtp = new String(new char[1]); //证件类型
        idno = new String(new char[20]); //证件号码
        mobileno = new String(new char[30]); //手机号码
        email = new String(new char[40]); //电子邮件
        memo = new String(new char[40]); //备注字段
        
        hometel = new String(new char[30]); //家庭电话
        officetel = new String(new char[30]); //办公电话
        faxno = new String(new char[30]); //传真号码
        
        daylim = new String(new char[20]); //客户每日限额
        monthlim = new String(new char[20]); //客户每月限额
        province = new String(new char[20]); //所在省
        city = new String(new char[20]); //所在市
        addr = new String(new char[100]); //帐单地址
        postcode = new String(new char[6]); //邮政编码
        
    }

    public void setCustCode(String custCode) throws Exception
    {
        custCode = getFillStr(this.custCode, custCode);

        this.custCode = custCode;
    }

    public void setAcctno(String acctno) throws Exception
    {
        acctno = getFillStr(this.acctno, acctno);

        this.acctno = acctno;
    }

    public void setAreaCode(String areaCode) throws Exception
    {
        areaCode = getFillStr(this.areaCode, areaCode);

        this.areaCode = areaCode;
    }

    public void setSubCorpNo(String subCorpNo) throws Exception
    {
        subCorpNo = getFillStr(this.subCorpNo, subCorpNo);

        this.subCorpNo = subCorpNo;
    }

    public void setTransPassword(String transPassword) throws Exception
    {
        transPassword = getFillStr(this.transPassword, transPassword);

        this.transPassword = transPassword;
    }

    public void setSex(String sex) throws Exception
    {
        sex = getFillStr(this.sex, sex);

        this.sex = sex;
    }

    public void setBirthday(String birthday) throws Exception
    {
        birthday = getFillStr(this.birthday, birthday);

        this.birthday = birthday;
    }

    public void setIdtp(String idtp) throws Exception
    {
        idtp = getFillStr(this.idtp, idtp);

        this.idtp = idtp;
    }

    public void setIdno(String idno) throws Exception
    {
        idno = getFillStr(this.idno, idno);

        this.idno = idno;
    }

    public void setAcctnm(String acctnm) throws Exception
    {
        acctnm = getFillStr(this.acctnm, acctnm);

        this.acctnm = acctnm;
    }

    public void setMobileno(String mobileno) throws Exception
    {
        mobileno = getFillStr(this.mobileno, mobileno);

        this.mobileno = mobileno;
    }

    public void setHometel(String hometel) throws Exception
    {
        hometel = getFillStr(this.hometel, hometel);

        this.hometel = hometel;
    }

    public void setOfficetel(String officetel) throws Exception
    {
        officetel = getFillStr(this.officetel, officetel);

        this.officetel = officetel;
    }

    public void setFaxno(String faxno) throws Exception
    {
        faxno = getFillStr(this.faxno, faxno);

        this.faxno = faxno;
    }

    public void setEmail(String email) throws Exception
    {
        email = getFillStr(this.email, email);

        this.email = email;
    }

    public void setDaylim(String daylim) throws Exception
    {
        daylim = getFillZeroStr(this.daylim, daylim);

        this.daylim = daylim;
    }

    public void setMonthlim(String monthlim) throws Exception
    {
        monthlim = getFillZeroStr(this.monthlim, monthlim);

        this.monthlim = monthlim;
    }

    public void setProvince(String province) throws Exception
    {
        province = getFillStr(this.province, province);

        this.province = province;
    }

    public void setCity(String city) throws Exception
    {
        city = getFillStr(this.city, city);

        this.city = city;
    }

    public void setAddr(String addr) throws Exception
    {
        addr = getFillStr(this.addr, addr);

        this.addr = addr;
    }

    public void setMemo(String memo) throws Exception
    {
        memo = getFillStr(this.memo, memo);

        this.memo = memo;
    }

    public void setPostcode(String postcode) throws Exception
    {
        postcode = getFillStr(this.postcode, postcode);

        this.postcode = postcode;
    }

    public String getCustCode()
    {
        return custCode;
    }

    public String getAcctno()
    {
        return acctno;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public String getSubCorpNo()
    {
        return subCorpNo;
    }

    public String getTransPassword()
    {
        return transPassword;
    }

    public String getSex()
    {
        return sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getIdtp()
    {
        return idtp;
    }

    public String getIdno()
    {
        return idno;
    }

    public String getAcctnm()
    {
        return acctnm;
    }

    public String getMobileno()
    {
        return mobileno;
    }

    public String getHometel()
    {
        return hometel;
    }

    public String getOfficetel()
    {
        return officetel;
    }

    public String getFaxno()
    {
        return faxno;
    }

    public String getEmail()
    {
        return email;
    }

    public String getDaylim()
    {
        return daylim;
    }

    public String getMonthlim()
    {
        return monthlim;
    }

    public String getProvince()
    {
        return province;
    }

    public String getCity()
    {
        return city;
    }

    public String getAddr()
    {
        return addr;
    }

    public String getMemo()
    {
        return memo;
    }

    public String getPostcode()
    {
        return postcode;
    }

    /**
     * 获取包体
     * @return String
     */
    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(custCode);
        sb.append(acctno);
        sb.append(areaCode);
        sb.append(subCorpNo);
        sb.append(transPassword);
        sb.append(acctnm);
        sb.append(sex);
        sb.append(birthday);
        sb.append(idtp);
        sb.append(idno);
        sb.append(mobileno);
        sb.append(email);
        sb.append(memo);
        
        sb.append(hometel);
        sb.append(officetel);
        sb.append(faxno);
        sb.append(daylim);
        sb.append(monthlim);
        sb.append(province);
        sb.append(city);
        sb.append(addr);
        sb.append(postcode);
        

        return sb.toString();
    }

    /**
     * 获取包体长度
     * @return Integer
     */
    public Integer getPkgBodyLen()
    {
        int iPkgBodyLen = 0;

        if(custCode != null)
        {
            iPkgBodyLen += custCode.getBytes().length;
        }

        if(acctno != null)
        {
            iPkgBodyLen += acctno.getBytes().length;
        }

        if(areaCode != null)
        {
            iPkgBodyLen += areaCode.getBytes().length;
        }

        if(subCorpNo != null)
        {
            iPkgBodyLen += subCorpNo.getBytes().length;
        }

        if(transPassword != null)
        {
            iPkgBodyLen += transPassword.getBytes().length;
        }

        if(acctnm != null)
        {
            iPkgBodyLen += acctnm.getBytes().length;
        }

        if(sex != null)
        {
            iPkgBodyLen += sex.getBytes().length;
        }

        if(birthday != null)
        {
            iPkgBodyLen += birthday.getBytes().length;
        }

        if(idtp != null)
        {
            iPkgBodyLen += idtp.getBytes().length;
        }

        if(idno != null)
        {
            iPkgBodyLen += idno.getBytes().length;
        }

        if(mobileno != null)
        {
            iPkgBodyLen += mobileno.getBytes().length;
        }

        if(hometel != null)
        {
            iPkgBodyLen += hometel.getBytes().length;
        }

        if(officetel != null)
        {
            iPkgBodyLen += officetel.getBytes().length;
        }

        if(faxno != null)
        {
            iPkgBodyLen += faxno.getBytes().length;
        }

        if(email != null)
        {
            iPkgBodyLen += email.getBytes().length;
        }

        if(daylim != null)
        {
            iPkgBodyLen += daylim.getBytes().length;
        }

        if(monthlim != null)
        {
            iPkgBodyLen += monthlim.getBytes().length;
        }

        if(province != null)
        {
            iPkgBodyLen += province.getBytes().length;
        }

        if(city != null)
        {
            iPkgBodyLen += city.getBytes().length;
        }

        if(addr != null)
        {
            iPkgBodyLen += addr.getBytes().length;
        }

        if(postcode != null)
        {
            iPkgBodyLen += postcode.getBytes().length;
        }

        if(memo != null)
        {
            iPkgBodyLen += memo.getBytes().length;
        }

        pkgPkgBodyLen = new Integer(iPkgBodyLen);

        return pkgPkgBodyLen;
    }

}
