// I worked on the homework assignment alone, using only course materials.

/**
  * An Exception that is thrown when a directory is missing the contents.txt
  * files used by the ListSongs program.
  *
  * @author iwebb6
  * @version 1.0
  */
public class MissingContentsFileException extends Exception {
    /**
      * A constructor that creates a MissingContentsFileException with a message
      * saying what directory is missing the contents.txt file.
      *
      * @param path The path to the directory missing the contents.txt file
      */
    public MissingContentsFileException(String path) {
        super(String.format("Directory %s does not contain a contents.txt "
                            + "file.", path));
    }
}
