package Exception;

public class ItemNotBorrowedByUserException extends RuntimeException
{
    public ItemNotBorrowedByUserException()
    {
        super();
    }

    public ItemNotBorrowedByUserException(String message)
    {
        super(message);
    }

    public ItemNotBorrowedByUserException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ItemNotBorrowedByUserException(Throwable cause)
    {
        super(cause);
    }
}
