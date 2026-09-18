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

public class LoginFrame extends JFrame {

    private JTextField nomeField;
    private JPasswordField passwordField;

    private GestoreUtenti gestoreUtenti;
    private SaveUsers userFile;

    public LoginFrame(GestoreUtenti gestoreUtenti, SaveUsers userFile) {

        this.gestoreUtenti = gestoreUtenti;
        this.userFile = userFile;

        setTitle("Login");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titoloPanel = new JPanel();
        JLabel titoloLabel = new JLabel("Login");
        titoloPanel.add(titoloLabel);
        add(titoloPanel, BorderLayout.NORTH);
        titoloLabel.setFont(titoloLabel.getFont().deriveFont(Font.BOLD));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JLabel nomeLabel = new JLabel("Nome utente:");
        this.nomeField = new JTextField(30);
        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        JLabel passwordLabel = new JLabel("Password:");
        this.passwordField = new JPasswordField(100);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton loginButton = new JButton("Login");
        JButton registrazioneButton = new JButton("Registrati");

        buttonPanel.add(loginButton);
        buttonPanel.add(registrazioneButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> login());

        registrazioneButton.addActionListener(e -> registrazione());
    }

    private void login() {
        String nome = this.nomeField.getText();
        String password = new String(this.passwordField.getPassword());
        Utente utente = this.gestoreUtenti.login(nome, password);

        if (utente == null) {
            JOptionPane.showMessageDialog(this, "Login errato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SaveFile saveFile = new SaveFile("saves/" + utente.getNome() + ".txt");
        Rubrica rubrica = new Rubrica(saveFile.load());

        MainFrame frame = new MainFrame(rubrica, saveFile, gestoreUtenti, userFile, utente);
        frame.setVisible(true);

        this.dispose();
    }

    private void registrazione() {
        RegistrazioneFrame frame = new RegistrazioneFrame(this.gestoreUtenti, this.userFile);
        frame.setVisible(true);
    }
}
