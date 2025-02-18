/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class Viewing {
    private int viewID;
    private int novelID;

    public Viewing() {
    }

    public Viewing(int novelID) {
        this.novelID = novelID;
    }

    public int getViewID() {
        return viewID;
    }

    public void setViewID(int viewID) {
        this.viewID = viewID;
    }

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    @Override
    public String toString() {
        return "Viewing{" +
                "viewID=" + viewID +
                ", novelID=" + novelID +
                '}';
    }
}
