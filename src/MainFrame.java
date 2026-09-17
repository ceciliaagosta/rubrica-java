import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class MainFrame extends JFrame{

    private SaveFile saveFile;
    
    private Rubrica rubrica;
    private JTable table;

    public MainFrame(Rubrica rubrica) {
        this.saveFile = new SaveFile();
        this.rubrica = rubrica;

        setTitle("Rubrica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton nuovoButton= new JButton("Nuovo");
        JButton modificaButton= new JButton("Modifica");
        JButton eliminaButton= new JButton("Elimina");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(nuovoButton);
        nuovoButton.addActionListener(e -> {
            PersonaEditor editor = new PersonaEditor(rubrica, this);
            editor.setVisible(true);
        });

        buttonPanel.add(modificaButton);
        modificaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                Persona persona = rubrica.getRubrica().get(selectedRow);
                PersonaEditor editor = new PersonaEditor(rubrica, this, persona, selectedRow);
                editor.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(this, "Selezionare una persona da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        buttonPanel.add(eliminaButton);
        eliminaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selezionare una persona da eliminare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Persona persona = rubrica.getRubrica().get(selectedRow);
            int risposta = JOptionPane.showConfirmDialog(this, "Eliminare la persona " + persona.getNome() + " " + persona.getCognome() + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                rubrica.eliminaPersona(selectedRow);
                saveFile.save(rubrica.getRubrica());
                aggiornaTabella();
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

        String[] colonne = {"Nome", "Cognome", "Telefono"};

        String[][] contatti = new String[rubrica.getRubrica().size()][colonne.length];

        for (int i=0; i<rubrica.getRubrica().size(); i++) {
            Persona p = rubrica.getRubrica().get(i);
            contatti[i][0] = p.getNome();
            contatti[i][1] = p.getCognome();
            contatti[i][2] = p.getTelefono();
        }

        this.table = new JTable(contatti, colonne);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Funzioni ausiliarie

    public void aggiornaTabella() {
        String[] colonne = {
            "Nome",
            "Cognome",
            "Telefono"
        };
    
        String[][] contatti = new String[rubrica.getRubrica().size()][colonne.length];
    
        for (int i = 0; i < rubrica.getRubrica().size(); i++) {
    
            Persona persona = rubrica.getRubrica().get(i);
    
            contatti[i][0] = persona.getNome();
            contatti[i][1] = persona.getCognome();
            contatti[i][2] = persona.getTelefono();
        }
    
        table.setModel(new javax.swing.table.DefaultTableModel(contatti, colonne));
    }
}
