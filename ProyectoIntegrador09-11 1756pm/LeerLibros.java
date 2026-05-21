import java.io.*;
public class LeerLibros{
     public static void main(String[] args) {
         int LU=0;
         String nombre=new String("");
         int edad=0;
         try(ObjectInputStream archiDIS = new ObjectInputStream(new FileInputStream("archivos/libros.bin"))){
             // Creando un objeto de tipo archivo secuencial para leer
            // FileInputStream archiFIS=new FileInputStream("archivos/personas.bin");
            // ObjectInputStream archiDIS = new ObjectInputStream(new FileInputStream("archivos/personas.bin"));
             //leyendo archivo
            while (true){ // bucle infinito y finalizap por excepcion EOFException
                //LU = archiDIS.readInt();
                Libro libro = (Libro) archiDIS.readObject();
                //edad = archiDIS.readInt();
                System.out.println(libro.toString());
            }
         } // cierra try
         catch(EOFException e){
             System.out.println("Fin del Archivo");
         }
         catch(FileNotFoundException fnfe){
             System.out.println("No se encontró el archivo: "+fnfe.getMessage());
         }
         catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
     } // cierra main
} // cierra clase