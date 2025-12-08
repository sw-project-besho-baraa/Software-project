package Service.BroadcastMessage;

import DTO.UserDTO.UserContactDTO;

public record AdminBroadcastMessageData(UserContactDTO userContactDTO, String message) {
}