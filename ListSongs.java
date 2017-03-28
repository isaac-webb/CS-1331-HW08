import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
  * A class that prompts the user for input to search their music library.
  *
  * @author iwebb6
  * @version 1.0
  */
public class ListSongs {

    /**
      * Given the path to a directory and a Scanner that has been initalized to
      * read from the directory's contents.txt file, returns a 2-dimensional
      * String array of length 2 representing the contents of the directory.
      * The first inner array contains the paths of each file in the directory,
      * while the second inner array contains the paths to each sub-directory.
      *
      * @param baseDir the path to the directory to get the contents of
      * @param contentsScanner a Scanner that has been initialized to read from
      *        the directory's contents.txt file.
      * @return the paths of the files and sub-directories contained in baseDir
      */
    private static String[][] parseContentsFile(String baseDir,
                                                Scanner contentsScanner) {

        String[] files = new String[0];
        String[] dirs = new String[0];

        if (contentsScanner.hasNextLine()) {
            String line = contentsScanner.nextLine();
            if (line.split("files: ").length > 1) {
                files = line.split("files: ")[1].split(", ");
            }
        }

        if (contentsScanner.hasNextLine()) {
            String line = contentsScanner.nextLine();
            if (line.split("dirs: ").length > 1) {
                dirs = line.split("dirs: ")[1].split(", ");
            }
        }

        for (int i = 0; i < files.length; i++) {
            files[i] = baseDir + "/" + files[i];
        }

        for (int i = 0; i < dirs.length; i++) {
            dirs[i] = baseDir + "/" + dirs[i];
        }

        return new String[][] {files, dirs};
    }

    private static void validateArguments(String[] args) {
        switch (args.length) {
        case 0:
            throw new MissingArgumentException();
        case 1:
            throw new MissingArgumentException(1, "artist or genre");
        case 2:
            if (args[1].equals("artist")) {
                throw new MissingArgumentException(2, "artist name");
            } else if (args[1].equals("genre")) {
                throw new MissingArgumentException(2, "genre name");
            } else {
                throw new IllegalArgumentException(
                          String.format(
                              "Invalid argument at position 1: expected "
                              + "\"artist\" or \"genre\" but got \"%s\"",
                              args[1]));
            }
        case 3:
            if (!(args[1].equals("artist") || args[1].equals("genre"))) {
                throw new IllegalArgumentException(
                          String.format(
                              "Invalid argument at position 1: expected "
                              + "\"artist\" or \"genre\" but got \"%s\"",
                              args[1]));
            }
            break;
        default:
            throw new IllegalArgumentException("Illegal number of arguments: "
                                               + "expected 3 but got "
                                               + args.length);
        }
    }

    private static String parseSongFile(String attribute,
                                        String value,
                                        Scanner input) {
        // Create Strings to store the output
        String name = "";
        String genre = "";
        String artist = "";
        boolean match = false;

        // Loop through the file and extract the data. This algorithm is a bit
        // overcomplicated because I am not assuming the order/existence of
        // attributes in the file.
        while (input.hasNextLine()) {
            // Extract the values from the file
            String[] line = input.nextLine().split(": ");

            // Compensate for the possibility of having a colon in the song name
            if (line.length > 1) {
                // Exract everything after the first colon
                String temp = "";
                for (int i = 1; i < line.length; i++) {
                    temp += line[i];
                }
                temp = temp.trim();

                // Assign the value based on the key/identifier
                if (line[0].trim().equals("name")) {
                    name = temp;
                } else if (line[0].trim().equals("genre")) {
                    genre = temp;
                } else if (line[0].trim().equals("artist")) {
                    artist = temp;
                }
            }
        }

        // Check the attribute specified by the input to match
        if (attribute.equals("artist")) {
            match = artist.equals(value);
        } else if (attribute.equals("genre")) {
            match = genre.equals(value);
        }

        // Return the value based on whether or not it was a match
        return match ? String.format("%s by %s\n", name, artist) : "";
    }

    private static String findSongs(String directory,
                                    String attribute,
                                    String value) throws
    MissingContentsFileException {
        // Get the contents.txt file and see if we can read it/if it exists.
        // Parse the contents file if this succeeds.
        String[][] contentsData;
        try {
            contentsData =
                parseContentsFile(directory,
                                  new Scanner(new File(directory
                                                       + File.separator
                                                       + "contents.txt")));
        } catch (FileNotFoundException e) {
            throw new MissingContentsFileException(directory);
        }

        // Read all of the song files
        String output = "";
        for (String songFile : contentsData[0]) {
            try {
                Scanner input = new Scanner(new File(songFile));
                output += parseSongFile(attribute, value, input);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + songFile);
            }
        }

        // Read all of the directories
        for (String path : contentsData[1]) {
            try {
                output += findSongs(path, attribute, value);
            } catch (MissingContentsFileException e) {
                System.out.println("Directory " + path
                                   + " does not contain a contents.txt file.");
            }
        }

        return output;
    }

    /**
      * Runs the program, using the inputs provided at execution
      *
      * @param args The command line arguments provided at execution
      */
    public static void main(String[] args) {
        // Validate the inputs to the program
        validateArguments(args);

        String output = "";
        args[0] = args[0].substring(0, args[0].length() - 1);
        try {
            output = findSongs(args[0], args[1], args[2]);

            if (!output.isEmpty()) {
                System.out.println();
                System.out.println(output);
            } else if (args[1].equals("artist")) {
                throw new NoSuchArtistException(args[2], args[0]);
            } else {
                throw new NoSuchGenreException(args[2], args[0]);
            }
        } catch (MissingContentsFileException e) {
            e.printStackTrace();
        }
    }
}
