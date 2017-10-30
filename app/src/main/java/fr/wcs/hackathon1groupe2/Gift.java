package fr.wcs.hackathon1groupe2;

public class Gift {
    private String title, description;
    private int thumbnail;

    public Gift() {}

    Gift(String title, String description) {
        this.title = title;
        this.description = description;
    }

    String getTitle() {return title;}
    public void setTitle(String name) {this.title = name;}

    String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    int getThumbnail() {return thumbnail;}
    public void setThumbnail(int thumbnail) {this.thumbnail = thumbnail;}
}
