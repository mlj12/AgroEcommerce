
package uk.aber.ac.agroecommerce;

public class User {

    private String name,address,image,email,password;
    public User() {
    }

    public User(String name, String address, String image, String email ) {
        this.name = name;
        this.address = address;

        this.image = image;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address= address;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
