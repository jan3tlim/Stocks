import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Util class to creat directory.
 */
public class DirectoryCreator {

  /**
   * Creates a directory if it does not already exist.
   * @param directoryPath The path of the directory to create.
   * @return The Path object representing the directory.
   * @throws IOException If an I/O error occurs.
   */

  public static void ensureDirectoryExists(String directoryPath) {
    Path path = Paths.get(directoryPath);
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
        System.out.println("Directory created: " + path.toString());
      } catch (IOException e) {
        System.err.println("Failed to create directory: " + directoryPath);
        e.printStackTrace();
      }
    } else {
      System.out.println("Directory already exists: " + path.toString());
    }
  }
}

