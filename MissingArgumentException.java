// I worked on the homework assignment alone, using only course materials.

/**
  * An Exception that is thrown when the user does not provide enough arguments
  * to run the ListSongs program.
  *
  * @author iwebb6
  * @version 1.0
  */
public class MissingArgumentException extends RuntimeException {
    /**
      * The constructor that creates a MissingArgumentException with information
      * regarding which argument is missing and what that argument is.
      *
      * @param missingIndex The index of the first missing argument
      * @param description A description of that missing argument
      */
    public MissingArgumentException(int missingIndex, String description) {
        super(String.format("Missing argument at position %d: %s.",
                            missingIndex, description));
    }

    /**
      * The no-args constructor that creates a MissingArgumentException in the
      * event that no arguments are provided at all.
      */
    public MissingArgumentException() {
        super("No arguments provided.");
    }
}
