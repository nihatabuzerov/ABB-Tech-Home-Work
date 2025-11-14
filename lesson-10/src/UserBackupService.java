import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserBackupService {

    public void saveUsers(List<User> users, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public List<User> loadUsers(String filePath) {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            users = (List<User>) ois.readObject();
            System.out.println("Users loaded successfully from " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return users;
    }

    public void backupFile(String sourceFile, String backupDir) {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path backupDirectory = Paths.get(backupDir);

            if (!Files.exists(backupDirectory)) {
                Files.createDirectories(backupDirectory);
            }

            Path backupFile = backupDirectory.resolve(sourcePath.getFileName());
            Files.copy(sourcePath, backupFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created at " + backupFile);
        } catch (IOException | InvalidPathException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    public void deleteBackup(String backupFilePath) {
        try {
            Path backupFile = Paths.get(backupFilePath);
            if (Files.exists(backupFile)) {
                Files.delete(backupFile);
                System.out.println("Backup file deleted successfully: " + backupFilePath);
            } else {
                System.out.println("Backup file does not exist: " + backupFilePath);
            }
        } catch (IOException | SecurityException e) {
            System.err.println("Error deleting backup: " + e.getMessage());
        }
    }
}
