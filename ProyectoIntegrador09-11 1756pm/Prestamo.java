import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;//para dar formato a Calendar
import java.io.Serializable;
/**
 * Write a description of class Prestamo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Prestamo implements Serializable
{
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;
    
    public Prestamo(Calendar p_fechaRetiro, Calendar p_fechaDevolucion, Socio p_socio, Libro p_libro)
    {
        this.setFechaRetiro(p_fechaRetiro);
        this.setFechaDevolucion(p_fechaDevolucion);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }
    
    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro)
    {
        this.setFechaRetiro(p_fechaRetiro);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }
    
    private void setFechaRetiro(Calendar p_fechaRet){
        this.fechaRetiro = p_fechaRet;
    }
    
    private void setFechaDevolucion(Calendar p_fechaDev){
        this.fechaDevolucion = p_fechaDev;
    }
    
    private void setSocio(Socio p_socio){
        this.socio = p_socio;
    }
    
    private void setLibro(Libro p_libro){
        this.libro = p_libro;
    }
    
    public Calendar getFechaRetiro(){
        return this.fechaRetiro;
    }
    
    public Calendar getFechaDevolucion(){
        return this.fechaDevolucion;
    }
    
    public Socio getSocio(){
        return this.socio;
    }
    
    public Libro getLibro(){
        return this.libro;
    }
    
    public void registrarFechaDevolucion(Calendar p_fecha){
        this.setFechaDevolucion(p_fecha);
    }
    
    public boolean vencido(Calendar p_fecha){
        //Con clone generamos una copia de la fecha de retiro
        Calendar maximoDevolucion = (Calendar) this.getFechaRetiro().clone();
        maximoDevolucion.add(Calendar.DAY_OF_MONTH, this.getSocio().getDiasPrestamo());
        if(p_fecha.after(maximoDevolucion) && this.getFechaDevolucion() == null){
            return true;
        }else{
            return false;
        }
    }
    
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(this.getFechaDevolucion() == null){
            return "Retiro: "+ sdf.format(this.getFechaRetiro().getTime())+ " - Devolución: Sin Devolucion todavía"+ "\n Libro: "+ 
            this.getLibro().getTitulo() + "\n Socio: "+ this.getSocio().getNombre();
        }else{
            return "Retiro: "+ sdf.format(this.getFechaRetiro().getTime())+ " - Devolución: "+ sdf.format(this.getFechaDevolucion().getTime())+
            "\n Libro: "+ this.getLibro().getTitulo() + "\n Socio: "+ this.getSocio().getNombre();
        }
    }
}