import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveFile {

    private File saveFile;

    // Costruttori

    public SaveFile() {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.saveFile = new File("saves/informazioni.txt");
    }

    public SaveFile(String filename) {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.saveFile = new File(filename);
    }

    // Metodi

    public List<Persona> load() {
        List<Persona> rubrica = new ArrayList<>();

        if (!this.saveFile.exists()) { return rubrica; }

        try (Scanner scanner = new Scanner(saveFile)) {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                if (line.isBlank()) { continue; }

                Persona p = deserialize(line);
                rubrica.add(p);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Impossibile leggere il file: " + saveFile.getPath());
        }

        return rubrica;
    }

    public void save(List<Persona> rubrica) {
        try (PrintStream output = new PrintStream(saveFile)) {

            for (Persona persona : rubrica) {
                output.println(serialize(persona));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Impossibile salvare il file: " + saveFile.getPath());
        }

    }

    // Funzioni ausiliarie

    private String serialize(Persona persona) {

        return persona.getNome() + ";" +
               persona.getCognome() + ";" +
               persona.getIndirizzo() + ";" +
               persona.getTelefono() + ";" +
               persona.getEta();
    }

    private Persona deserialize(String line) {

        String[] fields = line.split(";");

        String nome = fields[0];
        String cognome = fields[1];
        String indirizzo = fields[2];
        String telefono = fields[3];
        int eta = Integer.parseInt(fields[4]);

        return new Persona(nome, cognome, indirizzo, telefono, eta);
    }

    public boolean rinomina(String vecchioNome, String nuovoNome) {

        File vecchioFile = new File("saves/" + vecchioNome + ".txt");
        File nuovoFile = new File("saves/" + nuovoNome + ".txt");
    
        if (!vecchioFile.exists()) { return true;}
        if (nuovoFile.exists()) { return false; }
    
        return vecchioFile.renameTo(nuovoFile);
    }

    public boolean elimina(String nome) {
        
        File file = new File("saves/" + nome + ".txt");
        if (!file.exists()) { return true; }
        return file.delete();
    }
}
