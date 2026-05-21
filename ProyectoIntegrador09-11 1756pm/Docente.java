import java.util.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
public class Docente extends Socio
{
    // instance variables - replace the example below with your own
    private String area;
    /**
     * Constructor for objects of class Docente
     */
    public Docente(int p_dni, String p_nombre, int p_diasPrestamo, String p_area, ArrayList<Prestamo> p_prestamos) 
    {
        super(p_dni, p_nombre, p_diasPrestamo, p_prestamos);
        this.setArea(p_area);
    }
    
    public Docente(int p_dni, String p_nombre, int p_diasPrestamo, String p_area){
        super(p_dni, p_nombre, p_diasPrestamo);
        this.setArea(p_area);
    }
    
    public Docente(int p_dni, String p_nombre, int p_diasPrestamo, String p_area, Prestamo p_prestamo){
        super(p_dni, p_nombre, p_diasPrestamo, p_prestamo);
        this.setArea(p_area);
    }
    
    private void setArea(String p_area){
        this.area = p_area;
    }
    
    public String getArea(){
        return this.area;
    }
    
    public boolean esResponsable(){
        boolean vencido = false;
        Calendar hoy = Calendar.getInstance(); //verificar si conviene instanciarlo como String
        for(Prestamo unPrestamo: this.getPrestamos()){
            if(unPrestamo.vencido(hoy)){
                vencido = true;
                break;
            }
        }
        if(!vencido){
            return true;
        }else{
            return false;
        }
    }
    
    public void cambiarDiasDePrestamo(int p_dias){
        this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
        System.out.println("Como premio a la responsabilidad, al Docente: " + this.getNombre() + "Se le otorga " + p_dias + "adicionales de préstamo");
    } 
    
    @Override 
    public boolean puedePedir(){
        if(super.puedePedir() && this.esResponsable()){
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public String soyDeLaClase(){
        return "Docente";
    }
}