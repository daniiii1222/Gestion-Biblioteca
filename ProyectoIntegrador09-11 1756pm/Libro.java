import java.util.*;
import java.io.Serializable;
/**
 * Write a description of class Libro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Libro implements Serializable
{
    // instance variables - replace the example below with your own
    private ArrayList<Prestamo> prestamos;
    private String titulo;
    private int edicion;
    private String editorial;
    private int anio;
    /**
     * Constructor for objects of class Libro
     */
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, ArrayList<Prestamo> p_prestamos)
    {
        this.setPrestamos(p_prestamos);
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
    }
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio, Prestamo p_prestamo)
    {
        this.setPrestamos(new ArrayList<Prestamo>());
        this.agregarPrestamo(p_prestamo);
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
    }
    public Libro(String p_titulo, int p_edicion, String p_editorial, int p_anio)
    {
        this.setPrestamos(new ArrayList<Prestamo>());
        this.setTitulo(p_titulo);
        this.setEdicion(p_edicion);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
    }

    private void setPrestamos(ArrayList<Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    private void setTitulo(String p_titulo){
        this.titulo = p_titulo;
    }
    private void setEdicion(int p_edicion){
        this.edicion = p_edicion;
    }
    private void setEditorial(String p_editorial){
        this.editorial = p_editorial;
    }
    private void setAnio(int p_anio){
        this.anio = p_anio;
    }
    public ArrayList<Prestamo> getPrestamos(){
        return this.prestamos;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public int getEdicion(){
        return this.edicion;
    }
    public String getEditorial(){
        return this.editorial;
    }
    public int getAnio(){
        return this.anio;
    }
    
    public boolean agregarPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    
    public boolean eliminarPrestamo(Prestamo p_prestamo){
        if(this.getPrestamos().size() == 0 || this.getPrestamos().isEmpty()){
            return false; //para que no descuente negativo
        }
        return this.getPrestamos().remove(p_prestamo);
    }
    
    public boolean prestado(){
        for(Prestamo unPrestamo: this.getPrestamos()){
            if(unPrestamo.getLibro() == this && unPrestamo.getFechaDevolucion() == null){
                return true;
            }
        }
        return false;
    }
    
    public Prestamo ultimoPrestamo(){
        Prestamo ultimoPrestamo = this.getPrestamos().get(0);
        for(Prestamo unPrestamo: this.getPrestamos()){
            if(unPrestamo.getFechaRetiro().after(ultimoPrestamo.getFechaRetiro())){
                ultimoPrestamo = unPrestamo;
            }
        }
        return ultimoPrestamo;
    }  
    
    public String toString(){
        return "Titulo: "+ this.getTitulo();
    }
}