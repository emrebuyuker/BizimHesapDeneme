package com.buyukeryazilim.WinpolHesabim;

public class ListViewItem {

    private String solUst;
    private String orta;
    private String sagAlt;

    public ListViewItem(String solUst, String orta, String sagAlt) {
        this.solUst = solUst;
        this.orta = orta;
        this.sagAlt = sagAlt;
    }

    public String getSolUst() {
        return solUst;
    }

    public void getSolUst(String solUst) {
        this.solUst = solUst;
    }

    public String getOrta() {
        return orta;
    }

    public void setOrta(String orta) {
        this.orta = orta;
    }

    public String getSagAlt() {
        return sagAlt;
    }

    public void setSagAlt(String sagAlt) {
        this.sagAlt = sagAlt;
    }

}
