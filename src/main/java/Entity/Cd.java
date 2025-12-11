package Entity;

import Enum.MediaItemType;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

/**
 * Represents a CD item in the library.
 */
@Entity
@NoArgsConstructor
public class Cd extends MediaItem {

    /**
     * Creates a new CD with a title.
     *
     * @param title CD title
     */
    public Cd(String title) {
        super(title);
    }

    /**
     * Returns the media type for this item.
     *
     * @return Cd type
     */
    @Override
    public MediaItemType getMediaType() {
        return MediaItemType.Cd;
    }
}
