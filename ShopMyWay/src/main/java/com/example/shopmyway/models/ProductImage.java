package com.example.shopmyway.models;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private  String type;

    // @Lob = Large Object — tells the database "this is a BIG chunk of data"
    // The image is stored as raw bytes (0s and 1s), just like how files are stored on a computer
    @Column(name = "image_data", columnDefinition = "BYTEA")
    private byte[] imageData;

    // Links this image back to its product
    // One product can have one image (you can extend to many later)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public ProductImage(@Nullable String originalFilename, @Nullable String contentType, byte[] bytes, Product product) {
    }

    public ProductImage(Integer id, String name, String type, byte[] imageData, Product product) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.product = product;
    }

    public ProductImage() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
