package Util.MessageFormater;

/**
 * Defines a generic contract for converting message data objects into formatted output.
 * <p>
 * Typically used to generate HTML email content or structured text from
 * typed message data objects.
 *
 * @param <IMessageObj> the type of input message data object
 */
public interface IMessageFormater<IMessageObj> {

    /**
     * Formats the given message object into a string representation.
     *
     * @param messageObjM the input message data object
     * @return a formatted string (e.g., HTML or plain text)
     */
    String formatMessage(IMessageObj messageObjM);
}
