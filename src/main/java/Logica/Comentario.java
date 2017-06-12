package Logica;

public class Comentario {
    private long id;
    private String comentario;
    private Usuario author;

    public Comentario(long id, String comentario, Usuario author) {

        this.id = id;
        this.comentario = comentario;
        this.author = author;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAuthor() {
        return author;
    }

    public void setAuthor(Usuario author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", comentario='" + comentario + '\'' +
                ", author=" + author +
                '}';
    }
}
