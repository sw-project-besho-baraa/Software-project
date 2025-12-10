/**
 * Data access layer responsible for reading and writing entities to underlying
 * storage.
 * <p>
 * Repositories abstract persistence concerns and provide query methods required
 * by services. Avoid leaking persistence-specific types beyond this layer.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Keep interfaces small and focused on aggregates.</li>
 * <li>Return domain types or DTOs, not low-level persistence primitives.</li>
 * <li>Handle transactions at the appropriate boundary (typically service
 * layer).</li>
 * </ul>
 */
package Repository;