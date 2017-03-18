package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shash on 2/25/2017.
 */
public class Publisher implements Serializable{
    private static final long serialVersionUID = 1282140613137773421L;


    private int publisherId;
    private String publisherName;
    private String publisherAddress;
    private String publisherPhone;
    private List<Book> books;

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherAddress() {
        return publisherAddress;
    }

    public void setPublisherAddress(String publisherAddress) {
        this.publisherAddress = publisherAddress;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
