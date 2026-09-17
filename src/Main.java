import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SaveFile saveFile = new SaveFile();
        Rubrica rubrica = new Rubrica(saveFile.load());

        SwingUtilities.invokeLater(() -> 
            {MainFrame frame = new MainFrame(rubrica);
            frame.setVisible(true);});

    }

}
