import java.util.Objects;

public class Persona {

    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    // Costruttore
    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    // Getter
    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public int getEta() {
        return this.eta;
    }

    // Funzioni ausiliarie

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }

        Persona altra = (Persona) obj;

        return this.eta == altra.eta &&
               this.nome.equals(altra.nome) &&
               this.cognome.equals(altra.cognome) &&
               this.indirizzo.equals(altra.indirizzo) &&
               this.telefono.equals(altra.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.nome,
            this.cognome,
            this.indirizzo,
            this.telefono,
            this.eta
        );
    }
}