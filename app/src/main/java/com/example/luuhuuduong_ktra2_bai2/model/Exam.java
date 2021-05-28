package com.example.luuhuuduong_ktra2_bai2.model;

public class Exam {
     String id,subname,exdate,extime,ischecked;

    public Exam(String id, String subname, String exdate, String extime, String ischecked) {
        this.id = id;
        this.subname = subname;
        this.exdate = exdate;
        this.extime = extime;
        this.ischecked = ischecked;
    }

    public Exam() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getExdate() {
        return exdate;
    }

    public void setExdate(String exdate) {
        this.exdate = exdate;
    }

    public String getExtime() {
        return extime;
    }

    public void setExtime(String extime) {
        this.extime = extime;
    }

    public String getIschecked() {
        return ischecked;
    }

    public void setIschecked(String ischecked) {
        this.ischecked = ischecked;
    }
}
