/**
 * Application service layer orchestrating business use-cases.
 * <p>
 * Services coordinate repositories, entities, and utilities to implement
 * application workflows. They are the primary boundary for transactional work,
 * validation beyond basic checks, and cross-cutting concerns such as
 * authorization and error translation.
 * </p>
 * <h2>Guidelines</h2>
 * <ul>
 * <li>Keep methods cohesive and side-effect aware.</li>
 * <li>Do not embed UI or persistence logic here.</li>
 * <li>Expose interfaces where appropriate to allow testing and
 * substitution.</li>
 * <li>Validate inputs at boundaries and keep transactions short-lived.</li>
 * </ul>
 */
package Service;