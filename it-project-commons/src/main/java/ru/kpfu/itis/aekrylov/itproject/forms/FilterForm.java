package ru.kpfu.itis.aekrylov.itproject.forms;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/30/17 4:48 PM
 */
public class FilterForm {

    protected String type;
    protected String brand;
    protected String model;

    //using wrappers here to check for nulls and avoid spring errors
    protected Integer price_low;
    protected Integer price_high;
    protected Integer cores;

    protected Integer author;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice_low() {
        return price_low;
    }

    public void setPrice_low(Integer price_low) {
        this.price_low = price_low;
    }

    public Integer getPrice_high() {
        return price_high;
    }

    public void setPrice_high(Integer price_high) {
        this.price_high = price_high;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }
}
