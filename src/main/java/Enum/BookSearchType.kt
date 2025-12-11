package Enum

/**
 * Defines search types for books.
 *
 * @see Entity.Book
 */
enum class BookSearchType {
    /** Search by book title. */
    TITLE,

    /** Search by book author. */
    AUTHOR,

    /** Search by book ISBN. */
    ISBN
}
