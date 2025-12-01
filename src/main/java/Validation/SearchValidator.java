package Validation;

public class SearchValidator implements IValidator<String> {

    @Override
    public void validate(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Search string cannot be null or empty");
        }
    }
}
