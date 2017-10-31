package fr.wcs.hackathon1groupe2;

public class Gift {
    private String title, description;
    private int picture,menu;

    public Gift() {}

    Gift(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {return title;}
    public void setTitle(String name) {this.title = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public int getPicture() {return picture;}
    public void setPicture(int picture) {this.picture = picture;}

    public int getMenu() {return menu;}
    public void setMenu(int menu) {this.menu = menu;}
}
