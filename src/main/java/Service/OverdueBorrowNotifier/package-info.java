/**
 * Orchestrates notifications for overdue borrowed items.
 * <p>
 * Bridges detection results and notification sending, batching messages and
 * applying communication policies such as rate-limiting or grouping.
 * </p>
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Transform overdue detection output into user-facing notifications.</li>
 * <li>Coordinate delivery via available notification senders.</li>
 * <li>Apply scheduling and throttling rules when necessary.</li>
 * </ul>
 */
package Service.OverdueBorrowNotifier;