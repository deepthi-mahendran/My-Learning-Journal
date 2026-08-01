/**
 * Magazine-specific details.
 */
public class MagazineDetails {
    private int issueNumber;
    private String publisher;

    public MagazineDetails(int issueNumber, String publisher) {
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() { return issueNumber; }
    public String getPublisher() { return publisher; }

    @Override
    public String toString() {
        return "Issue: " + issueNumber + ", Publisher: " + publisher;
    }
}