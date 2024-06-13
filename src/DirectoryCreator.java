import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;


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



  public static void clearDirectory(Path directory) throws IOException {
    if (!Files.exists(directory)) {
      System.out.println("Directory does not exist: " + directory);
      return;
    }

    try (Stream<Path> paths = Files.list(directory)) {
      paths.forEach(path -> {
        try {
          if (Files.isRegularFile(path)) {
            Files.delete(path);
          } else if (Files.isDirectory(path)) {
            deleteDirectoryRecursively(path);
          }
        } catch (IOException e) {
          System.err.println("Failed to delete file: " + path + " - " + e.getMessage());
        }
      });
    }
  }

  private static void deleteDirectoryRecursively(Path directory) throws IOException {
    Files.walkFileTree(directory, new SimpleFileVisitor<>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
      }
    });
  }

}

