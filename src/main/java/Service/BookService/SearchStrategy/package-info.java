/**
 * Strategy implementations for book search.
 * <p>
 * Encapsulates search by various criteria (e.g., author, title, identifiers) to
 * keep the search layer modular and extendable.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Implement single-responsibility strategies for each criterion.</li>
 * <li>Remain stateless and side-effect free.</li>
 * <li>Ensure predictable ordering and pagination where relevant.</li>
 * </ul>
 */
package Service.BookService.SearchStrategy;