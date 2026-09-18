import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveUsers {

    private File userFile;

    // Costruttori

    public SaveUsers() {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.userFile = new File("saves/utenti.txt");
    }

    public SaveUsers(String filename) {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.userFile = new File(filename);
    }

    // Metodi

    public List<Utente> load() {
        List<Utente> utenti = new ArrayList<>();

        if (!this.userFile.exists()) { return utenti; }

        try (Scanner scanner = new Scanner(userFile)) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (line.isBlank()) { continue; }

                Utente u = deserialize(line);
                utenti.add(u);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Impossibile leggere il file: " + userFile.getPath());
        }

        return utenti;
    }

    public void save(List<Utente> utenti) {
        try (PrintStream output = new PrintStream(userFile)) {

            for (Utente u : utenti) {
                output.println(serialize(u));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Impossibile salvare il file: " + userFile.getPath());
        }

    }

    // Funzioni ausiliarie

    private String serialize(Utente u) {

        return u.getNome() + ";" +
               u.getPassword() + ";";
    }

    private Utente deserialize(String line) {

        String[] fields = line.split(";");

        String nome = fields[0];
        String password = fields[1];

        return new Utente(nome, password);
    }

}
