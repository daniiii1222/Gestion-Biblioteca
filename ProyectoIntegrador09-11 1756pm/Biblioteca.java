import java.util.*;
/**
 * @author (Romero Virginia, Recalde Daniela) 
 * @version 2
 */
public class Biblioteca
{
    private String nombre;
    private ArrayList<Libro> libros;
    private ArrayList<Socio> socios;
    /**
     * Constructor que espera los argumentos que daran una identidad al objeto, al momento de instanciarlo
     */
    public Biblioteca(String p_nombre, ArrayList<Libro> p_libros, ArrayList<Socio> p_socios)
    {
        this.setNombre(p_nombre);
        this.setLibros(p_libros);
        this.setSocios(p_socios);
    }
    
    /**
     * Constructor que espera los argumentos que daran una identidad al objeto, al momento de instanciarlo
     */
    public Biblioteca(String p_nombre)
    {
        this.setNombre(p_nombre);
        this.setLibros(new ArrayList<Libro>());
        this.setSocios(new ArrayList<Socio>());
    }
    /**
     * Agrega un socio a la coleccion 
     * @return true si la operacion es exitosa y false si la operacion fracasa 
     */
    public boolean agregarSocio(Socio p_socio){
        return this.getSocios().add(p_socio);
    }
    
    /**
     * Agrega un libro a la coleccion 
     * @return true si la operacion es exitosa y false si la operacion fracasa 
     */
    public boolean agregarLibro(Libro p_libro){
        return this.getLibros().add(p_libro);
    }
    
    /**
     * Elimina un libro de la coleccion 
     * @return true si la operacion es exitosa y false si la operacion fracasa 
     */
    public boolean quitarLibro(Libro p_libro){
          if(this.getLibros().size() >= 1){
              return this.getLibros().remove(p_libro);
          }else{
              return false;
          }
    }
    
    /**
     * Elimina un socio de la coleccion 
     * @return true si la operacion es exitosa y false si la operacion fracasa 
     */
    public boolean quitarSocio(Socio p_socio){
          if(this.getSocios().size() >= 1){
              return this.getSocios().remove(p_socio);
          }else{
              return false;
          }
    }
    
    /**
     * Establece el nombre de la biblioteca
     * @param p_nombre
     */
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    /**
     * Establece una coleccion de libros 
     * @param p_libros
     */
    private void setLibros(ArrayList<Libro> p_libros){
        this.libros = p_libros;
    }
    
    /**
     * Establece una coleccion de socios
     * @param p_socios
     */
    private void setSocios(ArrayList<Socio> p_socios){
        this.socios = p_socios;
    }
    
    /**
     * Devuelve el nombre de la biblioteca
     * @return nombre
     */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
     * Devuelve la colección de libros
     * @return libros
     */
    public ArrayList<Libro> getLibros(){
        return this.libros;
    }
    
    /**
     * Devuelve la colección de socios
     * @return socios
     */
    public ArrayList<Socio> getSocios(){
        return this.socios;
    }
    
    /**
     * Crea (instancia) un nuevo libro
     * @param p_titulo, p_edicion, p_editorial, p_anio
     */
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio){
        Libro unLibro = new Libro(p_titulo, p_edicion, p_editorial, p_anio);
        this.agregarLibro(unLibro);
    }
    
    /**
     * Crea (instancia) un nuevo libro
     * @param p_dniSocio, p_nombre, p_carrera
     */
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera){
        Socio socioEstudiante = new Estudiante(p_dniSocio, p_nombre, p_carrera);
        this.agregarSocio(socioEstudiante);
    }
        
    //dias prestamo para docente empieza en 5 
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area){
        Socio unDocente = new Docente (p_dniSocio, p_nombre, 5, p_area);
        this.agregarSocio(unDocente);
    }
    
        public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro){
        Prestamo unPrestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
        return p_socio.agregarPrestamo(unPrestamo) && p_libro.agregarPrestamo(unPrestamo);
    }

    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        Calendar fecha = Calendar.getInstance();
        if(!p_libro.prestado()){
            throw new LibroNoPrestadoException(
            "El libro: " + p_libro.getTitulo() + " No se puede devolver ya que se encuentra en la biblioteca");
        }else{
            p_libro.ultimoPrestamo().registrarFechaDevolucion(fecha);
        }
    }
    
    public int  cantidadDeSociosPorTipo(String p_objeto){
        int contar = 0;
        for(Socio socio: this.getSocios()){
            if (socio.soyDeLaClase().equalsIgnoreCase(p_objeto)){
                contar ++;
            }
        }
        return contar;
    }
    
    //Se especificó el tipo del Arraylist para facilitar el recorrido del for each
     public ArrayList<Prestamo> prestamosVencidos(){
        Calendar fecha = Calendar.getInstance();
        ArrayList<Prestamo> prestamosVencidos = new ArrayList<Prestamo>();
            for (Socio socio : this.getSocios()) { 
                for (Prestamo prestamo : socio.getPrestamos()) {        
                if(prestamo.vencido(fecha)){
                    prestamosVencidos.add(prestamo);
                }
    
           }
        }
        return prestamosVencidos;
    }      

    public ArrayList<Socio> docentesResponsables(){
        ArrayList<Socio> docentesResponsables = new ArrayList<Socio>();
        for(Socio socio: this.getSocios()){
        if(socio.soyDeLaClase().equalsIgnoreCase("Docente")){
             Docente unDocente = (Docente)socio;
             if(unDocente.esResponsable()){
                 docentesResponsables.add(unDocente);
             }   
        }
       }
       return docentesResponsables;
    }
      /**  
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
        if(!p_libro.prestado()){
            throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
        } else {
            for(Socio socio: this.getSocios()){
                for(Prestamo prestamo : socio.getPrestamos()){
                    if(prestamo.getLibro() == p_libro){
                        return socio.getNombre();
                    }
                }
            }
            return "No se encuentra el libro:";
        }
    }*/
    
    public String quienTieneElLibro(Libro p_libro) throws LibroNoPrestadoException {
    if (p_libro == null) {
        return "El libro no existe.";
    }

    if (!p_libro.prestado()) {
        throw new LibroNoPrestadoException("El libro se encuentra en la biblioteca");
    } else {
        for (Socio socio : this.getSocios()) {
            for (Prestamo prestamo : socio.getPrestamos()) {
                if (prestamo.getLibro().getTitulo().equalsIgnoreCase(p_libro.getTitulo())) {
                    return socio.getNombre() + " (" + socio.getDniSocio() + ")";
                }
            }
        }
        return "No se encontró quién tiene el libro.";
    }
    }

    
    public String listaDeSocios(){
         ArrayList <String> impresion = new ArrayList<String>();
        
        for (Socio socio: this.getSocios()){
                impresion.add(socio.toString());
        }

        return String.join("\n", impresion);
    }
    
    public Socio buscarSocio(int p_dni){
        for(Socio socio: this.getSocios()){
            if(socio.getDniSocio() == p_dni){
            return socio;
        }
        }
        return null;
   }
    
   //se agrega metodo buscarLibro
   /**
    public Libro buscarLibro(String p_titulo){
        Libro encontrado = this.getLibros().get(0);
        if(this.getLibros().size() > 1){
            for(Libro unLibro: this.getLibros()){
                if(unLibro.getTitulo().equalsIgnoreCase(p_titulo)){
                    return unLibro;
                }
            }
        }
        return encontrado;
    }*/
   public Libro buscarLibro(String p_titulo) {
        for (Libro unLibro : this.getLibros()) {
            if (unLibro.getTitulo().equalsIgnoreCase(p_titulo)) {
                return unLibro;
            }
        }
        return null; // 🔹 si no lo encuentra, devuelve null
   }
   
    public String listaDeTitulos(){
         HashSet <String> impresion = new HashSet<String>();
        for(Libro libro: libros){
           impresion.add(libro.toString());
        }
        return String.join("\n", impresion);
    }
    
   
        public String listaDeLibros(){
        ArrayList <String> impresion = new ArrayList<String>();
        for (Libro libro : libros){
              impresion.add(libro.toString());
        }
        return String.join("\n", impresion);
    }
    
    public String listaDeDocentesResponsables(){
        ArrayList <String> impresion = new ArrayList<String>();
        for(Socio docenteResponsable : this.docentesResponsables()){
            impresion.add(docenteResponsable.toString()); 
        }
        return String.join("\n", impresion);
    }
    
    public void eliminarLibro(String p_titulo) {
        Libro encontrado = null;
        for (Libro libro : libros) {
        if (libro.getTitulo().equalsIgnoreCase(p_titulo)) {
            encontrado = libro;
            break;
        }
        }

        if (encontrado != null) {
            libros.remove(encontrado);
            System.out.println("Libro eliminado correctamente.");
            } else {
            System.out.println("No se encontró ningún libro con ese nombre.");
        }
    }
    
    public void bajaSocio(int p_dni) {
        Socio encontrado = null;

        for (Socio socio : socios) {
        if (socio.getDniSocio() == p_dni) {
            encontrado = socio;
            break;
        }
        }

        if (encontrado != null) {
            socios.remove(encontrado);
            System.out.println("Socio eliminado correctamente.");
            } else {
            System.out.println("No se encontró ningún socio con ese DNI.");
        }
    }
}