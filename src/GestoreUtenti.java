import java.util.List;
import java.util.ArrayList;

public class GestoreUtenti {
    
    private List<Utente> utenti;

    // Costruttori

    public GestoreUtenti() {
        this.utenti = new ArrayList<Utente>();
    }

    public GestoreUtenti(List<Utente> utenti) {
        this.utenti = utenti;
    }

    // Funzioni utenti

    public void creaUtente(Utente u) {
        this.utenti.add(u);
    }

    public void eliminaUtente(int indice) {
        this.utenti.remove(indice);
    }

    public void eliminaUtente(Utente u) {
        this.utenti.remove(u);
    }

    public void modificaUtente(int indice, Utente u) {
        this.utenti.set(indice, u);
    }

    public Utente login(String nome, String password) {
        for (Utente utente : this.utenti) {
            if (utente.getNome().equals(nome) && utente.getPassword().equals(password)) {
                return utente;
            }
        }
        return null;
    }

    // Funzioni ausiliarie

    public boolean usernameDisponibile(String nome) {
        for (Utente utente : this.utenti) {
            if (utente.getNome().equals(nome)) {
                return false;
            }
        }
        return true;
    }

    // Getter

    public List<Utente> getUtenti() {
        return this.utenti;
    }

}
