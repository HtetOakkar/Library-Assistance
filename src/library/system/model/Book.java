/*
 * Hay!
 * Hello
 * I'm Htet Oakkar
 */
package library.system.model;

/**
 *
 * @author Htet-Oakkar
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private boolean available;

    public Book(int id, String title, String author, String publisher, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Book(int id, String title, String author, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }
}
