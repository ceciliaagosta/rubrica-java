import java.util.ArrayList;
import java.util.List;

public class Rubrica {

    private List<Persona> rubrica; 

    // Costruttore

    public Rubrica() {
        this.rubrica = new ArrayList<Persona>();
    }

    public Rubrica(List<Persona> rubrica) {
        this.rubrica = rubrica;
    }

    // Funzioni rubrica

    public void creaPersona(Persona p) {
        this.rubrica.add(p);
    }

    public void eliminaPersona(int indice) {
        this.rubrica.remove(indice);
    }

    public void modificaPersona(int indice, Persona p) {
        this.rubrica.set(indice, p);
    }

    // Getter

    public List<Persona> getRubrica() {
        return this.rubrica;
    }
}
