package id.ac.uin_malang.application;

/**
 * Created by lenovo on 28/11/17.
 */

public class Card {
    private String imgUrl;
    private String title;
    public Card(String imgUrl, String title){
        this.imgUrl=imgUrl;
        this.title=title;
    }

    public String getImgUrl(){
        return imgUrl;
    }
    public void setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
}
