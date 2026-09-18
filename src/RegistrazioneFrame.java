import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class RegistrazioneFrame extends JFrame {

    private JTextField nomeField;
    private JPasswordField passwordField;
    private JPasswordField confermaPasswordField;

    private GestoreUtenti gestoreUtenti;
    private SaveUsers userFile;

    public RegistrazioneFrame(GestoreUtenti gestoreUtenti, SaveUsers userFile) {

        this.gestoreUtenti = gestoreUtenti;
        this.userFile = userFile;

        // Impostazioni finestra
        setTitle("Registrazione");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titoloPanel = new JPanel();
        JLabel titoloLabel = new JLabel("Registrazione");
        titoloLabel.setFont(titoloLabel.getFont().deriveFont(Font.BOLD));

        titoloPanel.add(titoloLabel);

        add(titoloPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JLabel nomeLabel = new JLabel("Nome utente:");
        this.nomeField = new JTextField();
        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        JLabel passwordLabel = new JLabel("Password:");
        this.passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JLabel confermaPasswordLabel = new JLabel("Conferma password:");
        this.confermaPasswordField = new JPasswordField();
        formPanel.add(confermaPasswordLabel);
        formPanel.add(confermaPasswordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton registrazioneButton = new JButton("Registrati");
        JButton annullaButton = new JButton("Annulla");

        buttonPanel.add(registrazioneButton);
        buttonPanel.add(annullaButton);

        add(buttonPanel, BorderLayout.SOUTH);

        registrazioneButton.addActionListener(e -> registra());

        annullaButton.addActionListener(e -> dispose());
    }

    private void registra() {

        String nome = this.nomeField.getText();
        String password = new String(this.passwordField.getPassword());
        String confermaPassword = new String(this.confermaPasswordField.getPassword());

        if (nome.isBlank() || password.isBlank() || confermaPassword.isBlank()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contieneSeparatore()) {
            JOptionPane.showMessageDialog(this, "Il carattere ';' non è consentito nei campi.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!this.gestoreUtenti.usernameDisponibile(nome)) {
            JOptionPane.showMessageDialog(this, "Il nome utente è già in uso.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utente nuovoUtente = new Utente(nome, password);
        this.gestoreUtenti.creaUtente(nuovoUtente);
        this.userFile.save(this.gestoreUtenti.getUtenti());

        JOptionPane.showMessageDialog(this, "Registrazione completata!");
        dispose();
    }

    private boolean contieneSeparatore() {
        String password = new String(this.passwordField.getPassword());
        return this.nomeField.getText().contains(";") ||
               password.contains(";");
    }
}