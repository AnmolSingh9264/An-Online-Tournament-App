package com.anmol.clashking;

public class matchmodel {
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    String status,kill,fee,prize,size,title;
    public matchmodel(){

    }
    public matchmodel(String status, String kill, String fee, String prize, String size,String title) {
        this.status = status;
        this.kill = kill;
        this.fee = fee;
        this.prize = prize;
        this.size = size;
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKill() {
        return kill;
    }

    public void setKill(String kill) {
        this.kill = kill;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
