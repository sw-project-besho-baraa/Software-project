
/**
 * Fine management services and strategies.
 * <p>
 * Provides fine calculation, aggregation, and updates for overdue items. Uses
 * pluggable strategies per media type and can support periodic or asynchronous
 * recalculation.
 * </p>
 * <h2>Responsibilities</h2>
 * <ul>
 * <li>Compute fines based on due dates, grace periods, and media-specific
 * rules.</li>
 * <li>Aggregate and update user balances as fines change.</li>
 * <li>Support background processing for recalculation when applicable.</li>
 * </ul>
 */
package Service.Fine;