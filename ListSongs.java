import java.util.Scanner;

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
}
