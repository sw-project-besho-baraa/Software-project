package Validation;

import Entity.Book;

public class BookValidator implements IValidator<Book>
{
    public void validate(Book book)
    {
        if (book == null)
        {
            throw new IllegalArgumentException("Book cannot be null");
        }
    }
}
