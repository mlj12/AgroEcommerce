package uk.aber.ac.agroecommerce;

public class Products
{
    private String Name, description, Price, image, category, pid, date, time;

    public Products()
    {

    }

    public Products(String Name, String description, String Price, String image, String category, String pid, String date, String time) {
        this.Name = Name;
        this.description = description;
        this.Price = Price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    // variable return is what the name field in Firebase -> Should be the same.

    public String getName() {
        return Name;
    }

    public void setname(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
