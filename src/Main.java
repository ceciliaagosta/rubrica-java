import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SaveUsers userFile = new SaveUsers();

        GestoreUtenti gestoreUtenti = new GestoreUtenti(userFile.load());
    
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame(gestoreUtenti, userFile);
            frame.setVisible(true);
        });
    }

}
