   
import java.util.*;
import java.io.Serializable;

/**
 * Clase abstracta Socio: representa a un socio de la biblioteca.
 * Contiene datos personales, días de préstamo y la lista de préstamos activos.
 * Es la clase base para Docente y Estudiante.
 * 
 * @author Ibarra Samuel
 * @version 01/11/25
 */
public abstract class Socio implements Serializable {
   
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor: inicializa los datos del socio y la lista de préstamos.
     * @param p_dni número de documento
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de días de préstamo
     */
    public Socio(int p_dni, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo) {
        this.setDniSocio(p_dni);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
    }
    
    /**
     * Constructor sobrecargado: permite inicializar al socio con una lista de préstamos existente.
     * @param p_dni número de documento
     * @param p_nombre nombre del socio
     * @param p_diasPrestamo cantidad de días de préstamo
     * @param p_prestamos lista de préstamos ya asociados al socio
     */
    public Socio(int p_dni, String p_nombre, int p_diasPrestamo, ArrayList<Prestamo> p_prestamos) {
        this.setDniSocio(p_dni);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    //prestamo solito
    public Socio(int p_dni, String p_nombre, int p_diasPrestamo, Prestamo p_prestamo) {
        this.setDniSocio(p_dni);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo>());
        this.agregarPrestamo(p_prestamo);
    }

    
    // Setters

    /**
     * Asigna el número de documento del socio.
     * @param p_dni número de documento
     */
    private void setDniSocio(int p_dni) { 
        this.dniSocio = p_dni; 
    }
    
    /**
     * Asigna el nombre del socio.
     * @param p_nombre nombre del socio
     */
    private void setNombre(String p_nombre) { 
        this.nombre = p_nombre; 
    }
    
    /**
     * Asigna la cantidad de días de préstamo del socio.
     * @param p_diasPrestamo cantidad de días de préstamo
     */
    protected void setDiasPrestamo(int p_diasPrestamo) { 
        this.diasPrestamo = p_diasPrestamo;
    }
    
    /**
     * Asigna la lista de préstamos del socio.
     * @param p_prestamos lista de préstamos
     */
    private void setPrestamos(ArrayList<Prestamo> p_prestamos) { 
        this.prestamos = p_prestamos;
    }

    
    // Getters
    /**
     * Devuelve el número de documento del socio.
     * @return número de documento (DNI)
     */
    public int getDniSocio() {
        return this.dniSocio;
    }
    
    /**
     * Devuelve el nombre del socio.
     * @return nombre del socio
     */
    public String getNombre() {
        return this.nombre; 
    }
    
    /**
     * Devuelve la cantidad de días de préstamo permitidos.
     * @return cantidad de días de préstamo
     */
    public int getDiasPrestamo() { 
        return this.diasPrestamo;
    }
    
    /**
     * Devuelve la lista de préstamos asociados al socio.
     * @return lista de préstamos
     */
    public ArrayList<Prestamo> getPrestamos() {
        return this.prestamos; 
    }


    /**
     * Agrega un préstamo a la lista del socio.
     * @param p_prestamo objeto Prestamo
     */
    public boolean agregarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().add(p_prestamo);
    }

    /**
     * Quita un préstamo de la lista del socio.
     * @param p_prestamo objeto Prestamo
     */
    public boolean quitarPrestamo(Prestamo p_prestamo) {
        return this.getPrestamos().remove(p_prestamo);
    }

    /**
     * Cuenta los préstamos no devueltos.
     * @return cantidad de libros actualmente prestados
     */
    public int cantLibrosPrestados() {
        int cont = 0;
        for (Prestamo p : this.getPrestamos()) {
            // Si la fecha de devolución es null, el libro sigue prestado
            if (p.getFechaDevolucion() == null) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Indica si puede solicitar un nuevo préstamo.
     * @param p_fecha Calendar - fecha actual
     * @return true si no tiene préstamos vencidos
     */
    public boolean puedePedir() {
        Calendar hoy = Calendar.getInstance();
        for (Prestamo prestamo : this.getPrestamos()) {
            // Se comprueba que no esté devuelto y esté vencido según la fecha pasada
            if (prestamo.getFechaDevolucion() == null && prestamo.vencido(hoy)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método abstracto que identifica la clase del socio (Docente o Estudiante).
     * @return tipo de socio en formato String
     */
    public abstract String soyDeLaClase();

    /**
     * Muestra información básica del socio.
     */
    public String toString() {
        return "D.N.I.: " + this.getDniSocio() + " || " + this.getNombre() +
               " (" + this.soyDeLaClase() + ") || Libros Prestados: " + this.cantLibrosPrestados();
    }
}
