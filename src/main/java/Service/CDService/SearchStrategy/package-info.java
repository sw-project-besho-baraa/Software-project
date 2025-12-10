/**
 * Strategy implementations for CD search operations.
 * <p>
 * Encapsulates search criteria variations (e.g., by title or other attributes)
 * to keep the search logic composable and extendable.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Keep strategies side-effect free and focused on a single criterion.</li>
 * <li>Return deterministic results for the same input.</li>
 * <li>Favor composition to build complex searches from simple ones.</li>
 * </ul>
 */
package Service.CDService.SearchStrategy;
