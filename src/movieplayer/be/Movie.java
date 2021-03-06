package movieplayer.be;

import java.util.ArrayList;
import java.sql.Date;

public class Movie {
    
    private String name;
    private ArrayList<Category> categories;
    private int rating;
    private String filelink;
    private final int ID; //it's final because in the database it's a unique constant
    private Date lastview;
    private double imdbRating;
    
    public Movie(String name, int rating, ArrayList<Category> categories, String filelink, Date lastview, int ID, double imdbRating)
    {
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.filelink = filelink;
        this.lastview = lastview;
        this.ID = ID;
        this.imdbRating = imdbRating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }
}
