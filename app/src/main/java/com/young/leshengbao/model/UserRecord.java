package com.young.leshengbao.model;

/**
 * Created by Administrator on 2016/8/2. 流水账单
 */
public class UserRecord {

    private String id ; /*记录id*/
    private String xtb; /*金额*/
    private String createtime; /*创建时间*/
    private String createtime_d ; /*创建时间带时分秒*/
    private String flag ; /*出入*/
    private String userid ; /*用户id*/
    private String typeCN ; /*记录类型*/
    private String type ; /*记录类型标识*/
    private String u_name ; /*用户名*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXtb() {
        return xtb;
    }

    public void setXtb(String xtb) {
        this.xtb = xtb;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime_d() {
        return createtime_d;
    }

    public void setCreatetime_d(String createtime_d) {
        this.createtime_d = createtime_d;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTypeCN() {
        return typeCN;
    }

    public void setTypeCN(String typeCN) {
        this.typeCN = typeCN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
