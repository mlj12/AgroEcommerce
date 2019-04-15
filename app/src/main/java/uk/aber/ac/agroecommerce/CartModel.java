package uk.aber.ac.agroecommerce;

public class CartModel {

    private String pname, description, price, image,quantity,pid;



    public CartModel() {

        //empty constructor
    }

    public CartModel(String pname, String description, String price, String image, String quantity,String pid) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.pid = pid;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
