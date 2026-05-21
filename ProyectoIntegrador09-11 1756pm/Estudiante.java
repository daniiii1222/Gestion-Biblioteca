import java.util.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
public class Estudiante extends Socio
{
    // instance variables - replace the example below with your own
    private String carrera;
    /**
     * Constructor for objects of class Estudiante
     */
    public Estudiante(String p_carrera, int p_dniSocio, String p_nombre,  ArrayList<Prestamo> p_prestamos)
    {
        super(p_dniSocio, p_nombre, 20);
        this.setCarrera(p_carrera);
    }
    
    public Estudiante(String p_carrera, int p_dniSocio, String p_nombre, Prestamo p_prestamo){
        super(p_dniSocio, p_nombre, 20, p_prestamo);
        this.setCarrera(p_carrera);
        
    }
    
    public Estudiante(int p_dniSocio, String p_nombre, String p_carrera){
        super(p_dniSocio, p_nombre, 20);
        this.setCarrera(p_carrera);
    }
    
    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }
    
    public String getCarrera(){
        return this.carrera;
    }
    
   @Override  
    public boolean puedePedir(){
        
        if(super.puedePedir() && this.cantLibrosPrestados() < 3){
            return true;
        }else{
            return false;
        }
        
    }
    @Override
    public String soyDeLaClase(){
        return "Estudiante";
    }
}

