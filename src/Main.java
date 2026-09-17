import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SaveFile saveFile = new SaveFile();
        Rubrica rubrica = new Rubrica(saveFile.load());

        for (Persona persona : rubrica.getRubrica()) {
            System.out.println(
                persona.getNome() + " " +
                persona.getCognome() + " " +
                persona.getTelefono());
        }

        SwingUtilities.invokeLater(() -> 
            {MainFrame frame = new MainFrame(rubrica);
            frame.setVisible(true);});

    }

}
