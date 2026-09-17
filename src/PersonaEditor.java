import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PersonaEditor extends JFrame{
    
    private SaveFile saveFile;

    private boolean edit;

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField telefonoField;
    private JTextField indirizzoField;
    private JTextField etaField;
    
    public PersonaEditor(Rubrica rubrica, MainFrame frame, Persona p, int index) {

        this.saveFile = new SaveFile();
        this.edit = (p != null);

        setTitle("Editor");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel nomeLabel = new JLabel("Nome");
        this.nomeField = new JTextField();
        editorPanel.add(nomeLabel);
        editorPanel.add(nomeField);

        JLabel cognomeLabel = new JLabel("Cognome");
        this.cognomeField = new JTextField();
        editorPanel.add(cognomeLabel);
        editorPanel.add(cognomeField);

        JLabel telefonoLabel = new JLabel("Telefono");
        this.telefonoField = new JTextField();
        editorPanel.add(telefonoLabel);
        editorPanel.add(telefonoField);

        JLabel indirizzoLabel = new JLabel("Indirizzo");
        this.indirizzoField = new JTextField();
        editorPanel.add(indirizzoLabel);
        editorPanel.add(indirizzoField);

        JLabel etaLabel = new JLabel("Età");
        this.etaField = new JTextField();
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
        salvaButton.addActionListener(e -> {

            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String indirizzo = indirizzoField.getText();
            String telefono = telefonoField.getText();
            int eta = Integer.parseInt(etaField.getText());
        
            Persona persona = new Persona(
                nome,
                cognome,
                indirizzo,
                telefono,
                eta
            );
            
            if (edit) { rubrica.modificaPersona(index, persona); }
            else { rubrica.creaPersona(persona); }

            saveFile.save(rubrica.getRubrica());
            frame.aggiornaTabella();
        
            dispose();
            
        });

        JButton annullaButton = new JButton("Annulla");
        annullaButton.addActionListener(e -> {
            dispose();
        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(salvaButton);
        buttonPanel.add(annullaButton);

        add(editorPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public PersonaEditor(Rubrica rubrica, MainFrame frame) {
        this(rubrica, frame, null, -1);
    }


}
