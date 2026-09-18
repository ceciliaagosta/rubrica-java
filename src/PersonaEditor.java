import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;

public class PersonaEditor extends JFrame{
    
    private Rubrica rubrica;
    private SaveFile saveFile;

    private boolean edit;

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField telefonoField;
    private JTextField indirizzoField;
    private JTextField etaField;

    private Dimension BUTTON_DIM = new Dimension(100, 30);
    private Color SALVA_COLOR = new Color(60, 170, 80);
    private Color SALVA_TEXT = Color.WHITE;
    
    public PersonaEditor(Rubrica rubrica, MainFrame frame, Persona p, int index, SaveFile saveFile) {

        this.rubrica = rubrica;
        this.saveFile = saveFile;
        this.edit = (p != null);

        setTitle("Editor");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new GridLayout(5, 2, 5, 5));
        editorPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel nomeLabel = new JLabel("Nome");
        this.nomeField = new JTextField(100);
        editorPanel.add(nomeLabel);
        editorPanel.add(nomeField);

        JLabel cognomeLabel = new JLabel("Cognome");
        this.cognomeField = new JTextField(100);
        editorPanel.add(cognomeLabel);
        editorPanel.add(cognomeField);

        JLabel telefonoLabel = new JLabel("Telefono");
        this.telefonoField = new JTextField(100);
        editorPanel.add(telefonoLabel);
        editorPanel.add(telefonoField);

        JLabel indirizzoLabel = new JLabel("Indirizzo");
        this.indirizzoField = new JTextField(100);
        editorPanel.add(indirizzoLabel);
        editorPanel.add(indirizzoField);

        JLabel etaLabel = new JLabel("Età");
        this.etaField = new JTextField(3);
        editorPanel.add(etaLabel);
        editorPanel.add(etaField);

        if (edit) {
            nomeField.setText(p.getNome());
            cognomeField.setText(p.getCognome());
            telefonoField.setText(p.getTelefono());
            indirizzoField.setText(p.getIndirizzo());
            etaField.setText(String.valueOf(p.getEta()));
        }

        JButton salvaButton = new JButton("Salva");
        salvaButton.setPreferredSize(BUTTON_DIM);
        salvaButton.setBackground(SALVA_COLOR);
        salvaButton.setForeground(SALVA_TEXT);
        salvaButton.setOpaque(true);
        salvaButton.setBorderPainted(false);
        salvaButton.addActionListener(e -> {

            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String indirizzo = indirizzoField.getText();
            String telefono = telefonoField.getText();

            if (nome.isBlank() || cognome.isBlank() || indirizzo.isBlank() || telefono.isBlank()) {
                JOptionPane.showMessageDialog(this,
                "Tutti i campi devono essere compilati.",
                "Errore",
                JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int eta;
            try {
                eta = Integer.parseInt(etaField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                "L'età deve essere un numero intero.",
                "Errore",
                JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            Persona persona = new Persona(
                nome,
                cognome,
                indirizzo,
                telefono,
                eta
            );
            
            if (edit) { this.rubrica.modificaPersona(index, persona); }
            else { this.rubrica.creaPersona(persona); }

            this.saveFile.save(this.rubrica.getRubrica());
            frame.aggiornaTabella();
        
            dispose();
            
        });

        JButton annullaButton = new JButton("Annulla");
        annullaButton.setPreferredSize(BUTTON_DIM);
        annullaButton.addActionListener(e -> {
            dispose();
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);

        add(editorPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public PersonaEditor(Rubrica rubrica, MainFrame frame, SaveFile saveFile) {
        this(rubrica, frame, null, -1, saveFile);
    }


}
