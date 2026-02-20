package com.mapfinder.modal;

import java.util.Date;

public class Announcement {
    private int announcementsId;
    private String title;
    private String msg;
    private int createdBy;
    private boolean isActive;
    private Date createdAt;



    public Announcement() {
    }


    public Announcement(int announcementsId, String title, String msg, int createdBy, boolean isActive, Date createdAt) {
        this.announcementsId = announcementsId;
        this.title = title;
        this.msg = msg;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    public Announcement( String title, String msg, int createdBy, boolean isActive, Date createdAt) {
        this.title = title;
        this.msg = msg;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Announcement( String title, String msg, int createdBy, boolean isActive) {
        this.title = title;
        this.msg = msg;
        this.createdBy = createdBy;
        this.isActive = isActive;
    }
    

    public int getAnnouncementsId() {
        return this.announcementsId;
    }

    public void setAnnouncementsId(int announcementsId) {
        if(announcementsId>=0)
        this.announcementsId = announcementsId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if(title!=null || !title.isEmpty())
        this.title = title;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        if(msg!=null || !msg.isEmpty())
        this.msg = msg;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(int createdBy) {
        if(createdBy>=0)
        this.createdBy = createdBy;
    }

    public boolean isIsActive() {

        return this.isActive;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {

        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        if(createdAt!=null)
        this.createdAt = createdAt;
    }


}
