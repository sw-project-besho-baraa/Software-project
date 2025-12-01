package Exception;

import java.util.List;

public class OverdueItemsException extends RuntimeException
{

    public OverdueItemsException(List<String> overdueMessages)
    {
        super(String.join("\n",overdueMessages));
    }
}
