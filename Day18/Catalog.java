import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Generic catalog that stores library items of a specific detail type.
 * @param <T> the type of item details (e.g., BookDetails)
 */
public class Catalog<T> {
    private List<LibraryItem<T>> items;

    public Catalog() {
        items = new ArrayList<>();
    }

    /**
     * Adds a new library item to the catalog.
     * @param item the LibraryItem to add
     */
    public void addItem(LibraryItem<T> item) {
        items.add(item);
    }

    /**
     * Removes an item by its ID.
     * @param itemID the ID of the item to remove
     * @throws IllegalArgumentException if the item is not found
     */
    public void removeItem(String itemID) throws IllegalArgumentException {
        boolean removed = items.removeIf(item -> item.getItemID().equals(itemID));
        if (!removed) {
            throw new IllegalArgumentException("Item with ID '" + itemID + "' not found.");
        }
    }

    /**
     * Retrieves an item by its ID.
     * @param itemID the ID to search for
     * @return an Optional containing the item, or empty if not found
     */
    public Optional<LibraryItem<T>> getItem(String itemID) {
        return items.stream()
                    .filter(item -> item.getItemID().equals(itemID))
                    .findFirst();
    }

    /**
     * Returns a copy of all items in the catalog.
     * @return list of all items
     */
    public List<LibraryItem<T>> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Returns the number of items in the catalog.
     * @return catalog size
     */
    public int size() {
        return items.size();
    }
}