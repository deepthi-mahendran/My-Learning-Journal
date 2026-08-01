/**
 * Generic class representing a library item.
 * @param <T> the type of item-specific details (e.g., BookDetails, DVDDetails)
 */
public class LibraryItem<T> {
    private String title;
    private String author;
    private String itemID;
    private T details;

    public LibraryItem(String title, String author, String itemID, T details) {
        this.title = title;
        this.author = author;
        this.itemID = itemID;
        this.details = details;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getItemID() { return itemID; }
    public T getDetails() { return details; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setItemID(String itemID) { this.itemID = itemID; }
    public void setDetails(T details) { this.details = details; }

    @Override
    public String toString() {
        return "ID: " + itemID + " | Title: " + title + " | Author: " + author +
               " | Details: " + details;
    }
}

