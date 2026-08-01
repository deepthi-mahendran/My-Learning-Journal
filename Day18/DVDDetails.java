/**
 * DVD-specific details.
 */
public class DVDDetails {
    private String director;
    private int duration; // in minutes

    public DVDDetails(String director, int duration) {
        this.director = director;
        this.duration = duration;
    }

    public String getDirector() { return director; }
    public int getDuration() { return duration; }

    @Override
    public String toString() {
        return "Director: " + director + ", Duration: " + duration + " min";
    }
}