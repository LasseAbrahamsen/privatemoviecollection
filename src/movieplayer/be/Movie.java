package movieplayer.be;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Movie {
    
    private String name;
    private ArrayList<Category> categories;
    private double rating;
    private String filelink;
    private final int ID; //it's final because in the database it's a unique constant
    private Date lastview;
    
    public Movie(String name, int rating, ArrayList<Category> categories, String filelink, Date lastview, int ID)
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
    
    public void addCategory(Category category) {
        categories.add(category);
    }
    
    public void removeCategory(Category category) {
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

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }
    
    
    
}
