import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;

public class ProfiloFrame extends JFrame {

    private GestoreUtenti gestoreUtenti;
    private SaveUsers saveUsers;
    private SaveFile saveFile;
    private Utente utente;

    private MainFrame mainFrame;

    private JTextField nomeField;
    private JPasswordField passwordField;
    private JPasswordField confermaPasswordField;

    private Color ELIMINA_COLOR = new Color(200, 60, 60);
    private Color ELIMINA_TEXT = Color.WHITE;

    public ProfiloFrame(GestoreUtenti gestoreUtenti, SaveUsers saveUsers, SaveFile saveFile, Utente utente, MainFrame mainFrame) {

        this.gestoreUtenti = gestoreUtenti;
        this.saveUsers = saveUsers;
        this.saveFile = saveFile;
        this.utente = utente;
        this.mainFrame = mainFrame;

        setTitle("Profilo");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titoloPanel = new JPanel();
        JLabel titoloLabel = new JLabel("Modifica profilo");

        titoloPanel.add(titoloLabel);

        add(titoloPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JLabel nomeLabel = new JLabel("Nome utente:");
        this.nomeField = new JTextField(this.utente.getNome());

        JLabel passwordLabel = new JLabel("Password:");
        this.passwordField = new JPasswordField(this.utente.getPassword());

        JLabel confermaPasswordLabel = new JLabel("Conferma password:");
        this.confermaPasswordField = new JPasswordField(this.utente.getPassword());

        formPanel.add(nomeLabel);
        formPanel.add(nomeField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(confermaPasswordLabel);
        formPanel.add(confermaPasswordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton salvaButton = new JButton("Salva");
        JButton annullaButton = new JButton("Annulla");
        JButton eliminaButton = new JButton("Elimina account");
        eliminaButton.setBackground(ELIMINA_COLOR);
        eliminaButton.setForeground(ELIMINA_TEXT);
        eliminaButton.setOpaque(true);
        eliminaButton.setBorderPainted(false);

        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);
        buttonPanel.add(eliminaButton);

        add(buttonPanel, BorderLayout.SOUTH);

        salvaButton.addActionListener(e -> salva());

        annullaButton.addActionListener(e -> dispose());

        eliminaButton.addActionListener(e -> eliminaProfilo());
    }

    private void salva() {

        String vecchioNome = this.utente.getNome();
        String nuovoNome = this.nomeField.getText();

        String nuovaPassword = new String(this.passwordField.getPassword());
        String confermaPassword = new String(this.confermaPasswordField.getPassword());


        if (nuovoNome.isBlank() || nuovaPassword.isBlank()) {
            JOptionPane.showMessageDialog(this, "Nome utente e password non possono essere vuoti.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nuovaPassword.equals(confermaPassword)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!vecchioNome.equals(nuovoNome) && !this.gestoreUtenti.usernameDisponibile(nuovoNome)) { 
            JOptionPane.showMessageDialog(this, "Il nome utente è già utilizzato.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Utente nuovoUtente = new Utente(nuovoNome, nuovaPassword);

        if (!vecchioNome.equals(nuovoNome)) {
            boolean rinominato = this.saveFile.rinomina(vecchioNome, nuovoNome);

            if (!rinominato) {
                JOptionPane.showMessageDialog(this, "Impossibile rinominare il file della rubrica.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        this.mainFrame.setSaveFile("saves/" + nuovoNome +".txt");
        this.mainFrame.setUtente(nuovoUtente);

        this.gestoreUtenti.modificaUtente(this.utente, nuovoUtente);

        this.saveUsers.save(this.gestoreUtenti.getUtenti());

        this.utente = nuovoUtente;

        JOptionPane.showMessageDialog(this, "Profilo aggiornato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void eliminaProfilo() {

        int risposta = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare il profilo? L'azione è irreversibile!", "Elimina profilo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
        if (risposta != JOptionPane.YES_OPTION) { return; }
    
        boolean eliminato = this.saveFile.elimina(this.utente.getNome());
    
        if (!eliminato) {
            JOptionPane.showMessageDialog(this, "Impossibile eliminare il file della rubrica.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        this.gestoreUtenti.eliminaUtente(this.utente);
    
        this.saveUsers.save(this.gestoreUtenti.getUtenti());
    
        this.mainFrame.dispose();
    
        LoginFrame loginFrame = new LoginFrame(this.gestoreUtenti, this.saveUsers);
        loginFrame.setVisible(true);
        dispose();
    }
}