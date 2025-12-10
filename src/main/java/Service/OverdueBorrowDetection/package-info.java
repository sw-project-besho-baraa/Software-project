/**
 * Detection of overdue borrowed items and related data models.
 * <p>
 * Identifies items that have exceeded their due dates and aggregates results
 * for downstream processing such as notifications or reporting.
 * </p>
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Scan and determine overdue items based on due dates and policies.</li>
 * <li>Provide summarized data structures for consumers.</li>
 * <li>Remain side-effect free; leave state changes to orchestrators.</li>
 * </ul>
 */
package Service.OverdueBorrowDetection;