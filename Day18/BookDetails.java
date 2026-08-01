/**
 * Book-specific details.
 */
public class BookDetails {
    private int pages;
    private String publisher;

    public BookDetails(int pages, String publisher) {
        this.pages = pages;
        this.publisher = publisher;
    }

    public int getPages() { return pages; }
    public String getPublisher() { return publisher; }

    @Override
    public String toString() {
        return "Pages: " + pages + ", Publisher: " + publisher;
    }
}
