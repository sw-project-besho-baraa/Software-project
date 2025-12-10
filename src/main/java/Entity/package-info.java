/**
 * Domain entities that model core business concepts.
 * <p>
 * Entities are plain Java classes that represent persistent or in-memory state
 * used by the application. They should encapsulate invariants and domain
 * behavior that does not require external services.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Keep fields private and expose behavior through methods.</li>
 * <li>Prefer value objects where applicable.</li>
 * <li>Ensure equals, hashCode and toString are implemented when instances are
 * compared or stored in collections.</li>
 * </ul>
 */
package Entity;