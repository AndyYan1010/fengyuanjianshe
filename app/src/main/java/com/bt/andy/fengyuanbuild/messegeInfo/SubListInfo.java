package com.bt.andy.fengyuanbuild.messegeInfo;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/31 20:03
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */

public class SubListInfo {

    /**
     * fyType : 运费
     * fyName : 申通
     * fnum : 1
     * funit : 3.14
     * fprice : 23.12
     * sqmoney : 100.12
     */

    private String fyType;
    private String fyName;
    private int    fnum;
    private double funit;
    private double fprice;
    private double sqmoney;
    /**
     * fyTypeID : 0011
     */

    private String fyTypeID;

    public String getFyType() {
        return fyType;
    }

    public void setFyType(String fyType) {
        this.fyType = fyType;
    }

    public String getFyName() {
        return fyName;
    }

    public void setFyName(String fyName) {
        this.fyName = fyName;
    }

    public int getFnum() {
        return fnum;
    }

    public void setFnum(int fnum) {
        this.fnum = fnum;
    }

    public double getFunit() {
        return funit;
    }

    public void setFunit(double funit) {
        this.funit = funit;
    }

    public double getFprice() {
        return fprice;
    }

    public void setFprice(double fprice) {
        this.fprice = fprice;
    }

    public double getSqmoney() {
        return sqmoney;
    }

    public void setSqmoney(double sqmoney) {
        this.sqmoney = sqmoney;
    }

    public String getFyTypeID() {
        return fyTypeID;
    }

    public void setFyTypeID(String fyTypeID) {
        this.fyTypeID = fyTypeID;
    }
}
