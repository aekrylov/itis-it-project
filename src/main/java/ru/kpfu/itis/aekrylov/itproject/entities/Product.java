package ru.kpfu.itis.aekrylov.itproject.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:03 PM
 */
@javax.persistence.Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int price;
    @Lob
    private String description;
    private String photo;
    private String brand;
    private String model;
    private String cpu_name;
    private int cores;
    private int ram_gb;
    private String video_card;
    private String hdd_name;
    private int hdd_capacity;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getCpu_name() {
        return cpu_name;
    }

    public void setCpu_name(String cpu_name) {
        this.cpu_name = cpu_name;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getRam_gb() {
        return ram_gb;
    }

    public void setRam_gb(int ram_gb) {
        this.ram_gb = ram_gb;
    }

    public String getVideo_card() {
        return video_card;
    }

    public void setVideo_card(String video_card) {
        this.video_card = video_card;
    }

    public String getHdd_name() {
        return hdd_name;
    }

    public void setHdd_name(String hdd_name) {
        this.hdd_name = hdd_name;
    }

    public int getHdd_capacity() {
        return hdd_capacity;
    }

    public void setHdd_capacity(int hdd_capacity) {
        this.hdd_capacity = hdd_capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

