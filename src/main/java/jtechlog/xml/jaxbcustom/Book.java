package jtechlog.xml.jaxbcustom;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Book {

    private Isbn10 isbn10;

    private String title;

    private String author;

    private String year;

    private String publisher;

    private Catalog catalog;

    @XmlAttribute
    @XmlJavaTypeAdapter(Isbn10Adapter.class)
    public Isbn10 getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(Isbn10 isbn10) {
        this.isbn10 = isbn10;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @XmlTransient
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        this.catalog = (Catalog)parent;
    }
}
