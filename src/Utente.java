import java.util.Objects;

public class Utente {
    
    private String nome;
    private String password;

    // Costruttore

    public Utente(String nome, String password) {
        this.nome = nome;
        this.password = password;
    }

    // Getter
    public String getNome() {
        return this.nome;
    }

    public String getPassword() {
        return this.password;
    }

    // Funzioni ausiliarie

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }

        Utente altro = (Utente) obj;

        return this.nome.equals(altro.nome) &&
               this.password.equals(altro.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.nome,
            this.password
        );
    }
}
