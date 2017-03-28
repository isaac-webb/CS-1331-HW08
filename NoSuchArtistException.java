// I worked on the homework assignment alone, using only course materials.

/**
  * An Exception thrown when the entire music library contains no songs by the
  * artist specified by the user.
  *
  * @author iwebb6
  * @version 1.0
  */
public class NoSuchArtistException extends RuntimeException {
    /**
      * A constructor that creates a NoSuchArtistException specifying the artist
      * and directory for which no songs were found.
      *
      * @param artist The artist the user attempted to search for
      * @param directory The top level directory the user attempted to search
      */
    public NoSuchArtistException(String artist, String directory) {
        super(String.format("Could not find any songs by artist %s in %s.",
                            artist, directory));
    }
}
