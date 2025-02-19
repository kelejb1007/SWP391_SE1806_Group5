/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nguyen Thanh Trung
 */
public class Favorite {
     private int favoriteID;
    private int userID;
    private int novelID;
    private boolean isFavorite;

    public Favorite() {
    }

    public Favorite(int favoriteID, int userID, int novelID, boolean isFavorite) {
        this.favoriteID = favoriteID;
        this.userID = userID;
        this.novelID = novelID;
        this.isFavorite = isFavorite;
    }
    

    public int getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(int favoriteID) {
        this.favoriteID = favoriteID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

   

    public int getNovelID() {
        return novelID;
    }

    public void setNovelID(int novelID) {
        this.novelID = novelID;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteID=" + favoriteID +
                ", userID=" + userID +
                ", novelID=" + novelID +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
