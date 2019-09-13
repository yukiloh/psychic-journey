package xyz.murasakichigo.community.dto;

import java.sql.Date;

public class Reply {
    private Integer reply_id;
    private Integer parent_id;
    private String reply_description;
    private Integer critic_id;
    private Date gmt_reply_create;
    private Date gmt_reply_modified;

    @Override
    public String toString() {
        return "Reply{" +
                "reply_id=" + reply_id +
                ", parent_id=" + parent_id +
                ", parent_id=" + reply_description +
                ", critic_id=" + critic_id +
                ", gmt_reply_create=" + gmt_reply_create +
                ", gmt_reply_modified=" + gmt_reply_modified +
                '}';
    }

    public String getReply_description() {
        return reply_description;
    }

    public void setReply_description(String reply_description) {
        this.reply_description = reply_description;
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getCritic_id() {
        return critic_id;
    }

    public void setCritic_id(Integer critic_id) {
        this.critic_id = critic_id;
    }

    public Date getGmt_reply_create() {
        return gmt_reply_create;
    }

    public void setGmt_reply_create(Date gmt_reply_create) {
        this.gmt_reply_create = gmt_reply_create;
    }

    public Date getGmt_reply_modified() {
        return gmt_reply_modified;
    }

    public void setGmt_reply_modified(Date gmt_reply_modified) {
        this.gmt_reply_modified = gmt_reply_modified;
    }
}
