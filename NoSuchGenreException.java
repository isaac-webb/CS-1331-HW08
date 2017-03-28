// I worked on the homework assignment alone, using only course materials.

/**
  * An Exception thrown when the entire music library contains no songs in the
  * genre specified by the user.
  *
  * @author iwebb6
  * @version 1.0
  */
public class NoSuchGenreException extends RuntimeException {
    /**
      * A constructor that creates a NoSuchGenreException specifying the genre
      * and directory for which no songs were found.
      *
      * @param genre The genre the user attempted to search for
      * @param directory The top level directory the user attempted to search
      */
    public NoSuchGenreException(String genre, String directory) {
        super(String.format("Could not find any songs belonging to the %s "
                            + "genre in %s.", genre, directory));
    }
}
