package modelo;

public class Cancion {
    private int id;
    private String titulo;
    private String artista;
    private double duracion;

    public Cancion(int id, String titulo, String artista, double duracion) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public double getDuracion() { return duracion; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%.0fs)", id, titulo, artista, duracion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cancion)) return false;
        return id == ((Cancion) o).id;
    }
}