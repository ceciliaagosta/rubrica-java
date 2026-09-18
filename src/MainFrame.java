import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

public class MainFrame extends JFrame{

    private SaveFile saveFile;
    
    private Rubrica rubrica;
    private JTable table;

    private Dimension BUTTON_DIM = new Dimension(100, 30);
    private Color ELIMINA_COLOR = new Color(200, 60, 60);
    private Color ELIMINA_TEXT = Color.WHITE;
    private Color PANEL_COLOR = new Color(163, 197, 249);
    private Color TABLE_COLOR = new Color(249, 215, 163);
    private Color HEADER_COLOR = new Color(45, 113, 249);

    public MainFrame(Rubrica rubrica) {
        
        this.saveFile = new SaveFile();
        this.rubrica = rubrica;

        setTitle("Rubrica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton nuovoButton= new JButton("Nuovo");
        nuovoButton.setPreferredSize(BUTTON_DIM);

        JButton modificaButton= new JButton("Modifica");
        modificaButton.setPreferredSize(BUTTON_DIM);

        JButton eliminaButton= new JButton("Elimina");
        eliminaButton.setPreferredSize(BUTTON_DIM);
        eliminaButton.setBackground(ELIMINA_COLOR);
        eliminaButton.setForeground(ELIMINA_TEXT);
        eliminaButton.setOpaque(true);
        eliminaButton.setBorderPainted(false);

        JToolBar buttonPanel = new JToolBar();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
        buttonPanel.setFloatable(false);
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.setBorderPainted(false);

        buttonPanel.add(nuovoButton);
        nuovoButton.addActionListener(e -> {
            PersonaEditor editor = new PersonaEditor(this.rubrica, this);
            editor.setVisible(true);
        });

        buttonPanel.add(modificaButton);
        modificaButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                Persona persona = this.rubrica.getRubrica().get(selectedRow);
                PersonaEditor editor = new PersonaEditor(this.rubrica, this, persona, selectedRow);
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

            Persona persona = this.rubrica.getRubrica().get(selectedRow);
            int risposta = JOptionPane.showConfirmDialog(this, "Eliminare la persona " + persona.getNome() + " " + persona.getCognome() + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                this.rubrica.eliminaPersona(selectedRow);
                this.saveFile.save(this.rubrica.getRubrica());
                aggiornaTabella();
            }
        });

        add(buttonPanel, BorderLayout.NORTH);

        String[] colonne = {"Nome", "Cognome", "Telefono"};

        String[][] contatti = new String[this.rubrica.getRubrica().size()][colonne.length];

        for (int i=0; i<this.rubrica.getRubrica().size(); i++) {
            Persona p = this.rubrica.getRubrica().get(i);
            contatti[i][0] = p.getNome();
            contatti[i][1] = p.getCognome();
            contatti[i][2] = p.getTelefono();
        }

        DefaultTableModel model = new DefaultTableModel(contatti, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.table = new JTable(model);
        this.table.setRowHeight(25);
        this.table.setFillsViewportHeight(true);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.table.getTableHeader().setFont(this.table.getTableHeader().getFont().deriveFont(Font.BOLD));
        this.table.setSelectionBackground(TABLE_COLOR);
        this.table.setSelectionForeground(Color.BLACK);
        this.table.getTableHeader().setBackground(HEADER_COLOR);
        this.table.getTableHeader().setForeground(Color.WHITE);

        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        Persona persona = rubrica.getRubrica().get(selectedRow);

                        PersonaEditor editor = new PersonaEditor(rubrica, MainFrame.this, persona, selectedRow);

                        editor.setVisible(true);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(this.table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Funzioni ausiliarie

    public void aggiornaTabella() {
        String[] colonne = {
            "Nome",
            "Cognome",
            "Telefono"
        };
    
        String[][] contatti = new String[this.rubrica.getRubrica().size()][colonne.length];
    
        for (int i = 0; i < this.rubrica.getRubrica().size(); i++) {
    
            Persona persona = this.rubrica.getRubrica().get(i);
    
            contatti[i][0] = persona.getNome();
            contatti[i][1] = persona.getCognome();
            contatti[i][2] = persona.getTelefono();
        }
    
        DefaultTableModel model = new DefaultTableModel(contatti, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.table.setModel(model);
    }
}
