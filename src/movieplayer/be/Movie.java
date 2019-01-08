package movieplayer.be;

import java.util.List;

public class Movie {
    
    private String name;
    private List<String> categories;
    private double rating;
    private String filelink;
    private final int ID; //it's final because in the database it's a unique constant
    private String lastview;
    
    public Movie(String name, int rating, List<String> categories, String filelink, String lastview, int ID)
    {
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.filelink = filelink;
        this.lastview = lastview;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addCategory(String category) {
        categories.add(category);
    }
    
    public void removeCategory(String category) {
        categories.remove(category);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }
    
    public int getID() {
        return ID;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLastview() {
        return lastview;
    }

    public void setLastview(String lastview) {
        this.lastview = lastview;
    }
    
    
    
}
