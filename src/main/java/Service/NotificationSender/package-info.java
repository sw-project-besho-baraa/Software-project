/**
 * Abstractions and contracts for sending notifications.
 * <p>
 * Defines a channel-agnostic interface for delivering messages (e.g., email),
 * enabling substitution of transport providers and easy testing.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Keep interfaces minimal and expressive.</li>
 * <li>Do not leak transport-specific types outside this package.</li>
 * <li>Ensure operations are idempotent where callers might retry.</li>
 * </ul>
 */
package Service.NotificationSender;