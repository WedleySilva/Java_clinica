package clinica;

public class Disponibilidade {
    private int id;
    private String nome;
    private String turno;
    private String dia;

    public Disponibilidade(int id, String nome, String turno, String dia) {
        this.id = id;
        this.nome = nome;
        this.turno = turno;
        this.dia = dia;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Turno: " + turno + ", Dia: " + dia;
    }
}
