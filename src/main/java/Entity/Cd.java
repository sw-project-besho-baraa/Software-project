package Entity;

import Enum.MediaItemType;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Cd extends MediaItem
{

    public Cd(String title)
    {
        super(title);

    }

    @Override
    public MediaItemType getMediaType()
    {
        return MediaItemType.Cd;
    }

}
