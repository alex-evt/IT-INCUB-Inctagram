package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.*;

@AllArgsConstructor
@Builder
@Data
public class User {
    private String userName;
    private String email;
    private String password;
    private String passwordConfirm;

    public static void writeUserDataToFile(String username, String password, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/text/registeredUsers.txt"))) {
            writer.write("Username: " + username);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("Password: " + password);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Writing in file error: " + e);
        }
    }

    public static User readUserDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/text/registeredUsers.txt"))) {
            String line;
            String username = null;
            String email = null;
            String password = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    username = line.substring("Username: ".length());
                } else if (line.startsWith("Email: ")) {
                    email = line.substring("Email: ".length());
                } else if (line.startsWith("Password: ")) {
                    password = line.substring("Password: ".length());
                }
                if (username != null && email != null && password != null) {
                    break;
                }
            }

            System.out.println(username + " " + email + " " + password);

            return User.builder().
                    userName(username)
                    .email(email)
                    .password(password)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Reading file error" + e);
        }

    }
}
