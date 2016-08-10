package com.young.leshengbao.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MyMsg implements Serializable {

    private String m_messageid;//消息id
    private String m_userid;//用户id
    private String m_content;//消息
    private String m_type;//类型
    private int m_isread;//是否已读（1：已读；0：未读）
    private String m_createtime;//创建时间

    public MyMsg(String m_messageid, String m_userid, String m_content, String m_type, int m_isread, String m_createtime) {
        this.m_messageid = m_messageid;
        this.m_userid = m_userid;
        this.m_content = m_content;
        this.m_type = m_type;
        this.m_isread = m_isread;
        this.m_createtime = m_createtime;
    }

    public String getM_messageid() {
        return m_messageid;
    }

    public void setM_messageid(String m_messageid) {
        this.m_messageid = m_messageid;
    }

    public String getM_userid() {
        return m_userid;
    }

    public void setM_userid(String m_userid) {
        this.m_userid = m_userid;
    }

    public String getM_content() {
        return m_content;
    }

    public void setM_content(String m_content) {
        this.m_content = m_content;
    }

    public String getM_type() {
        return m_type;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public int getM_isread() {
        return m_isread;
    }

    public void setM_isread(int m_isread) {
        this.m_isread = m_isread;
    }

    public String getM_createtime() {
        return m_createtime;
    }

    public void setM_createtime(String m_createtime) {
        this.m_createtime = m_createtime;
    }

    @Override
    public String toString() {
        return "MyMsg{" +
                "m_messageid='" + m_messageid + '\'' +
                ", m_userid='" + m_userid + '\'' +
                ", m_content='" + m_content + '\'' +
                ", m_type='" + m_type + '\'' +
                ", m_isread=" + m_isread +
                ", m_createtime=" + m_createtime +
                '}';
    }
}
