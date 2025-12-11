package Service.BroadcastMessage;

import DTO.UserDTO.UserContactDTO;

/**
 * Holds data for an admin broadcast message.
 *
 * @param userContactDTO
 *            recipient contact info
 * @param message
 *            message content
 */
public record AdminBroadcastMessageData(UserContactDTO userContactDTO, String message) {
}
