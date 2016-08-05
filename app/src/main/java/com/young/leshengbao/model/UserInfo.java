package com.young.leshengbao.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/28.
 */
public class UserInfo implements Serializable{


    private double u_xtb;
    private String u_createtime;
    private String u_mobile;
    private int safepwd;

    public UserInfo(double u_xtb, String u_createtime, String u_mobile, int safepwd) {
        this.u_xtb = u_xtb;
        this.u_createtime = u_createtime;
        this.u_mobile = u_mobile;
        this.safepwd = safepwd;
    }

    public double getU_xtb() {
        return u_xtb;
    }

    public void setU_xtb(double u_xtb) {
        this.u_xtb = u_xtb;
    }

    public String getU_createtime() {
        return u_createtime;
    }

    public void setU_createtime(String u_createtime) {
        this.u_createtime = u_createtime;
    }

    public String getU_mobile() {
        return u_mobile;
    }

    public void setU_mobile(String u_mobile) {
        this.u_mobile = u_mobile;
    }

    public int getSafepwd() {
        return safepwd;
    }

    public void setSafepwd(int safepwd) {
        this.safepwd = safepwd;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "u_xtb=" + u_xtb +
                ", u_createtime='" + u_createtime + '\'' +
                ", u_mobile='" + u_mobile + '\'' +
                ", safepwd=" + safepwd +
                '}';
    }
}
