
/**
 * Write a description of class PantallaBiblioteca here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Image;

//Librerias fecha
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//libreria arraylist
import java.util.*;

//Para Persistencia
import java.io.*;

//PANTALA PRINCIPAL
public class PantallaBiblioteca extends JFrame {
    private Image imagenFondo;
    /**
     * biblioteca será static para poder llamarlo en cualquier parte, aprovechando sus metodos
     */
    private static Biblioteca biblioteca; //Puse static para prueba
    //
    public static void refrescarBiblioteca() {
    biblioteca = new Biblioteca("El Ateneo", leerLibros(), leerSocios());
    }

    
    public static void grabarSocio(Socio p_socio, boolean p_modo){
        try{
        new File("archivos").mkdirs();
        File archivo = new File("archivos/socios.bin");
        boolean existe = archivo.exists(); //No se si es existe o si esta vacío
        FileOutputStream archiFOS = new FileOutputStream(archivo, p_modo);
        ObjectOutputStream archiDOS; //archivo para objetos
        if(existe){
            archiDOS = new AppendableObjectOutputStream(archiFOS); //Este crea una variable que agrega a lo que ya hay (Tema de encabezado de archivos binarios)
        }else{
            archiDOS = new ObjectOutputStream(archiFOS);//Este crea una variable que agrega desde cero (Tema de encabezado de archivos binarios)
        }
        //biblioteca.agregarSocio(p_socio); //Aca agrega a la variable temporal
        archiDOS.writeObject(p_socio);  //Acá graba el objeto en el archivo
        archiDOS.close();
        }
        catch(FileNotFoundException fnfe){ //Estas excepciones son obligatorias
            System.out.println("Archivo no encontrado");
        }
        catch(IOException ioe){//Estas excepciones son obligatorias
            System.out.println("Error al grabar"+ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    
    public static void removerSocio(int p_dni) {
        //biblioteca.quitarSocio(biblioteca.buscarSocio(p_dni));
        ArrayList<Socio> lista = leerSocios();
        
        //Remueve el socio de "lista"
        lista.removeIf(s -> s.getDniSocio() == p_dni);
        
        // Primero, vaciamos el archivo
        File archivo = new File("archivos/socios.bin");
        if (archivo.exists()) {
            archivo.delete(); // 🔹 borra el archivo actual completamente
        }
    
        // Luego, regrabamos todos los libros restantes
        boolean modo = false; // false al inicio para crear el archivo con cabecera
        for (Socio l : lista) {
            grabarSocio(l, modo);
            modo = true; // después del primero, ya agregamos (append)
        }
    }
    public static ArrayList<Socio> leerSocios(){
            ArrayList<Socio> listaSocios = new ArrayList<Socio>();
            try(ObjectInputStream archiDIS = new ObjectInputStream(new FileInputStream("archivos/socios.bin"))){
            while (true){ // bucle infinito y finalizap por excepcion EOFException
                //LU = archiDIS.readInt();
                Socio socio = (Socio) archiDIS.readObject();
                //edad = archiDIS.readInt();
                listaSocios.add(socio);
            }
            } // cierra try
            catch(EOFException e){
                System.out.println("Fin del Archivo"); //Capaz se saca el print porque levanta la consola cada vez que se lee el archivo
            }
            catch(FileNotFoundException fnfe){
                System.out.println("No se encontró el archivo: "+fnfe.getMessage()); 
            }
            catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
            }

            return listaSocios;
    }
    
    //GRABAR LIBRO LEER y REMOVER LIBRO  Los puse como static para poder usarlos donde sea
    public static void grabarLibro(Libro p_libro, boolean p_modo){
        try{
        new File("archivos").mkdirs();
        File archivo = new File("archivos/libros.bin");
        boolean existe = archivo.exists(); //No se si es existe o si esta vacío
        FileOutputStream archiFOS = new FileOutputStream(archivo, p_modo);
        ObjectOutputStream archiDOS; //archivo para objetos
        if(existe){
            archiDOS = new AppendableObjectOutputStream(archiFOS); //Este crea una variable que agrega a lo que ya hay (Tema de encabezado de archivos binarios)
        }else{
            archiDOS = new ObjectOutputStream(archiFOS);//Este crea una variable que agrega desde cero (Tema de encabezado de archivos binarios)
        }
        //biblioteca.agregarLibro(p_libro); //Aca agrega a la variable temporal
        archiDOS.writeObject(p_libro);  //Acá graba el objeto en el archivo
        archiDOS.close();
        }
        catch(FileNotFoundException fnfe){ //Estas excepciones son obligatorias
            System.out.println("Archivo no encontrado");
        }
        catch(IOException ioe){//Estas excepciones son obligatorias
            System.out.println("Error al grabar"+ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    
    public static void removerLibro(String p_titulo) {
        //biblioteca.eliminarLibro(p_titulo);
        ArrayList<Libro> lista = leerLibros();
        
        //Remueve libro de "lista"
        lista.removeIf(l -> l.getTitulo().equalsIgnoreCase(p_titulo));
    
        // Primero, vaciamos el archivo
        File archivo = new File("archivos/libros.bin");
        if (archivo.exists()) {
            archivo.delete(); // 🔹 borra el archivo actual completamente
        }
    
        // Luego, regrabamos todos los libros restantes
        boolean modo = false; // false al inicio para crear el archivo con cabecera
        for (Libro l : lista) {
            grabarLibro(l, modo);
            modo = true; // después del primero, ya agregamos (append)
        }
    }

    
    public static ArrayList<Libro> leerLibros(){
            ArrayList<Libro> listaLibros = new ArrayList<Libro>();
            try(ObjectInputStream archiDIS = new ObjectInputStream(new FileInputStream("archivos/libros.bin"))){
            while (true){ // bucle infinito y finalizap por excepcion EOFException
                //LU = archiDIS.readInt();
                Libro libro = (Libro) archiDIS.readObject();
                //edad = archiDIS.readInt();
                listaLibros.add(libro);
            }
            } // cierra try
            catch(EOFException e){
                System.out.println("Fin del Archivo"); //Capaz se saca el print porque levanta la consola cada vez que se lee el archivo
            }
            catch(FileNotFoundException fnfe){
                System.out.println("No se encontró el archivo: "+fnfe.getMessage()); 
            }
            catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
            }

            return listaLibros;
    }
    
public PantallaBiblioteca() {
    super("Biblioteca");
    setSize(1000, 750);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Imagen de fondo
    imagenFondo = new ImageIcon("fondoBiblioteca.jpg").getImage();

    // Panel con fondo personalizado
    JPanel panelConFondo = new JPanel() {
        @Override
        protected void paintComponent(Graphics grafico) {
            super.paintComponent(grafico);
            grafico.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    };

    panelConFondo.setLayout(new BorderLayout());

    // Instancia de biblioteca
    biblioteca = new Biblioteca("El Ateneo", leerLibros(), leerSocios());

    // Texto de bienvenida
    JLabel etiqueta = new JLabel("Bienvenido a la Biblioteca");
    etiqueta.setForeground(Color.WHITE);
    etiqueta.setFont(new Font("Arial", Font.BOLD, 20));
    etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);

    // PANEL CENTRAL para centrar todo vertical y horizontalmente
    JPanel panelCentral = new JPanel();
    panelCentral.setOpaque(false);
    panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

    // Centro vertical
    panelCentral.add(Box.createVerticalGlue());

    // Agregamos el título
    panelCentral.add(etiqueta);
    panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

    
    agregarMenuDesplegable(panelCentral);

    
    panelCentral.add(Box.createVerticalGlue());

    // Agregar panel central al centro
    panelConFondo.add(panelCentral, BorderLayout.CENTER);

    // Establecer el panel con fondo como contenedor principal
    setContentPane(panelConFondo);

    setVisible(true);
}


// MENU DESPLEGABLE
private void agregarMenuDesplegable(JPanel panel) {

    String[] opciones = {
        "Seleccionar...", "Agregar libro", "Eliminar libro", "Listar libros",
        "Libros guardados", "Agregar Socio", "Eliminar Socio", "Listar Socios",
        "Quien tiene el libro", "Docentes responsables", "Pedir Libro", "Salir"
    };

    JComboBox<String> comboMenu = new JComboBox<>(opciones);
    comboMenu.setMaximumSize(new Dimension(200, 30));  
    comboMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel etiquetaMenu = new JLabel("Menú de opciones:");
    etiquetaMenu.setForeground(Color.WHITE);
    etiquetaMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel panelMenu = new JPanel();
    panelMenu.setOpaque(false);
    panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

    panelMenu.add(etiquetaMenu);
    panelMenu.add(Box.createRigidArea(new Dimension(0, 10)));
    panelMenu.add(comboMenu);

    panel.add(panelMenu);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    // Acciones del combo…
    comboMenu.addActionListener(e -> {
        String opcion = (String) comboMenu.getSelectedItem();
        switch (opcion) {
                    case "Agregar libro":
                        new VentanaAgregarLibro(biblioteca);
                        break;
                    case "Eliminar libro":
                        new VentanaEliminarLibro();
                        break;
                    case "Listar libros":
                        new VentanaListarLibros(biblioteca);
                        break;
                    case "Libros guardados":
                        new VentanaLibrosGrabados();
                        break;
                    case "Agregar Socio":
                        new VentanaAgregarSocio(biblioteca);
                        break;
                    case "Eliminar Socio":
                        new VentanaEliminarSocio();
                        break;
                    case "Listar Socios":
                        new VentanaListarSocios(biblioteca);
                        break;
                        case "Quien tiene el libro":
                        new VentanaQuienTieneElLibro();
                        break;
                    case "Docentes responsables":
                        new VentanaDocentesResponsables(biblioteca);
                        break;
                    case "Pedir Libro":
                        new VentanaPedirLibro(biblioteca);
                        break;
                    case "Salir":
                        System.exit(0);
                        break;
           }
        });
}

    // Ventana para AGREGAR LIBRO
    private class VentanaAgregarLibro extends JFrame {
        public VentanaAgregarLibro(Biblioteca biblioteca) {
            super("Agregar Libro");
            setSize(400, 250);
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(10, 10, 10, 10);
            c.fill = GridBagConstraints.HORIZONTAL;
    
            JLabel lblTitulo = new JLabel("Título:");
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            JTextField txtTitulo = new JTextField(20);
    
            JLabel lblEdicion = new JLabel("Edición:");
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            JTextField txtEdicion = new JTextField(20);
    
            JLabel lblEditorial = new JLabel("Editorial:");
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;     
            JTextField txtEditorial = new JTextField(20);
    
            JLabel lblAnio = new JLabel("Año:");
            c.weightx = 1.0;
            c.fill = GridBagConstraints.HORIZONTAL;     
            JTextField txtAnio = new JTextField(20);
    
            JButton btnGuardar = new JButton("Guardar");
            add(btnGuardar);
               // FILA 1   
                c.gridx = 0; c.gridy = 0;
                add(lblTitulo, c);
                c.gridx = 1;
                add(txtTitulo, c);
    
                // FILA 2
                c.gridx = 0; c.gridy = 1;
                add(lblEdicion, c);
                c.gridx = 1;
                add(txtEdicion, c);
        
                // FILA 3
                c.gridx = 0; c.gridy = 2;
                add(lblEditorial, c);
                c.gridx = 1;
                add(txtEditorial, c);
        
                // FILA 4
                c.gridx = 0; c.gridy = 3;
                add(lblAnio, c);
                c.gridx = 1;
                add(txtAnio, c);
        
                // BOTÓN (FILA 5, centrado)
                c.gridx = 0; 
                c.gridy = 4;
                c.gridwidth = 2;
                c.anchor = GridBagConstraints.CENTER;
                add(btnGuardar, c);
                
                //evento del boton guardar
                btnGuardar.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evento){
                        try{
                            String titulo = txtTitulo.getText();
                            int edicion = Integer.parseInt(txtEdicion.getText());
                            String editorial = txtEditorial.getText();
                            int anio = Integer.parseInt(txtAnio.getText());
                            Libro libro = new Libro(titulo, edicion,editorial,anio);
                            grabarLibro(libro, true);
                            refrescarBiblioteca();
                            
                            //biblioteca.agregarLibro(libro);
                             JOptionPane.showMessageDialog(null, "Libro agregado!");
                            dispose(); 
                        
                            }catch (NumberFormatException formatoNumericoException){
                                JOptionPane.showMessageDialog(null, "Edicion y año deben ser numeros");
                        }
                    }
                });
            
            
            setLocationRelativeTo(null); // Centrar
            setVisible(true);
        }
    }

// Ventana para ELIMINAR LIBRO
private class VentanaEliminarLibro extends JFrame {
        
    public VentanaEliminarLibro() {
        super("Eliminar Libro");
            setSize(300, 150);
            setLayout(new FlowLayout());
            

            add(new JLabel("Ingrese el título del libro a eliminar:"));
            JTextField txtTitulo = new JTextField(15);
            add(txtTitulo);

            
            JButton btnEliminar = new JButton("Eliminar");
            add(btnEliminar);

            setLocationRelativeTo(null);
            setVisible(true);
            
             btnEliminar.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evento){
                 String titulo = txtTitulo.getText();
                 //biblioteca.eliminarLibro(titulo); Esta en el metodo removerLibro
                 removerLibro(titulo); //Remueve en el archivo y el ArrayList de biblioteca
                 refrescarBiblioteca();
                 JOptionPane.showMessageDialog(null, "Libro eliminado");
                }
            });
            
    }
}

//Ventana LISTAR LIBROS
private class VentanaListarLibros extends JFrame {
        public VentanaListarLibros(Biblioteca biblioteca) {
        super("Listado de Libros");
        setSize(350, 250);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        
        String lista = biblioteca.listaDeLibros();
        area.setText(lista);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}


//ventana LIBROS GRABADO
private class VentanaLibrosGrabados extends JFrame {
        public VentanaLibrosGrabados() {
        super("Listado de Libros");
        setSize(350, 250);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);
         
        //ArrayList<Libro> libros = leerLibros();
        area.setText(leerLibros().toString());

        
        setLocationRelativeTo(null);
        setVisible(true);
}
}

//ventana AGREGAR SOCIO   
private class VentanaAgregarSocio extends JFrame {

    private JPanel panelCampos;   // Panel donde aparecerán los inputs
    private JCheckBox checkDocente;
    private JCheckBox checkAlumno;

    public VentanaAgregarSocio(Biblioteca biblioteca) {
        super("Agregar Socio");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Panel superior con los tipos de socio
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new FlowLayout());

        checkDocente = new JCheckBox("Docente");
        checkAlumno = new JCheckBox("Alumno");

        panelOpciones.add(new JLabel("Seleccione tipo de socio:"));
        panelOpciones.add(checkDocente);
        panelOpciones.add(checkAlumno);

        add(panelOpciones, BorderLayout.NORTH);

        // Panel donde aparecerán los campos dinámicos
        panelCampos = new JPanel();
        panelCampos.setLayout(new GridLayout(0, 2, 10, 10));
        add(panelCampos, BorderLayout.CENTER);

        

        checkDocente.addActionListener(e -> mostrarCamposDocente(biblioteca));
        checkAlumno.addActionListener(e -> mostrarCamposAlumno(biblioteca));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void mostrarCamposDocente(Biblioteca biblioteca) {
        checkAlumno.setSelected(false); // Desmarcar alumno
        panelCampos.removeAll();        // Limpiar panel

        // Campos
        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtDepto = new JTextField();
        JTextField txtDias = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        panelCampos.add(new JLabel("DNI:"));
        panelCampos.add(txtDni);

        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);
        
        panelCampos.add(new JLabel("Días préstamo:"));
        panelCampos.add(txtDias);

        panelCampos.add(new JLabel("Departamento:"));
        panelCampos.add(txtDepto);

        panelCampos.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                String nombre = txtNombre.getText();
                int dias = Integer.parseInt(txtDias.getText());
                String depto = txtDepto.getText();
                Socio unSocio = new Docente(dni, nombre, dias, depto);
                //biblioteca.agregarSocio(unSocio);
                grabarSocio(unSocio, true);
                refrescarBiblioteca();

                JOptionPane.showMessageDialog(null, "Docente cargado exitosamente");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "DNI y días deben ser números");
            }
        });

        panelCampos.revalidate();
        panelCampos.repaint();
    }

    private void mostrarCamposAlumno(Biblioteca biblioteca) {
        checkDocente.setSelected(false); // Desmarcar docente
        panelCampos.removeAll();

        JTextField txtDni = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCarrera = new JTextField();
        JButton btnGuardar = new JButton("Guardar");

        panelCampos.add(new JLabel("DNI:"));
        panelCampos.add(txtDni);

        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Carrera:"));
        panelCampos.add(txtCarrera);

        panelCampos.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                String nombre = txtNombre.getText();
                String carrera = txtCarrera.getText();

                Socio unSocio = new Estudiante(dni, nombre, carrera);
                //biblioteca.agregarSocio(unSocio);
                grabarSocio(unSocio, true);
                refrescarBiblioteca();

                JOptionPane.showMessageDialog(null, "Alumno cargado exitosamente");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "DNI debe ser número");
            }
        });

        panelCampos.revalidate();
        panelCampos.repaint();
    }
}

// ventana ELIMINAR SOCIO
 private class VentanaEliminarSocio extends JFrame {
    public VentanaEliminarSocio() {
       super("Eliminar Socio");
            setSize(300, 150);
            setLayout(new FlowLayout());
            

            add(new JLabel("Ingrese el dni del socio a eliminar:"));
            JTextField txtDni = new JTextField(15);
            add(txtDni);

            
            JButton btnEliminar = new JButton("Eliminar");
            add(btnEliminar);

            setLocationRelativeTo(null);
            setVisible(true);
            
             btnEliminar.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evento){
                 int dni = Integer.parseInt(txtDni.getText());
                 //biblioteca.bajaSocio(dni);
                 removerSocio(dni);
                 refrescarBiblioteca();
                 JOptionPane.showMessageDialog(null, "Socio eliminado!");
                 dispose();
                }
            });
 }
    }
    
//ventana LISTAR SOCIOS
private class VentanaListarSocios extends JFrame {
    public VentanaListarSocios(Biblioteca biblioteca) {
        super("Listado de Socios");
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10)); 
        setLocationRelativeTo(null);

       
        JTextArea areaLista = new JTextArea();
        areaLista.setEditable(false);
        areaLista.setText(biblioteca.listaDeSocios());
        JScrollPane scrollLista = new JScrollPane(areaLista);

        
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));

        JTextArea areaDocentes = new JTextArea();
        areaDocentes.setEditable(false);
        areaDocentes.setBackground(new Color(240, 240, 240));
        areaDocentes.setText("Cantidad de docentes: " + biblioteca.cantidadDeSociosPorTipo("Docente"));

        JTextArea areaAlumnos = new JTextArea();
        areaAlumnos.setEditable(false);
        areaAlumnos.setBackground(new Color(240, 240, 240));
        areaAlumnos.setText("Cantidad de alumnos: " + biblioteca.cantidadDeSociosPorTipo("Estudiante"));

        panelInferior.add(areaDocentes);
        panelInferior.add(areaAlumnos);

        
        add(scrollLista, BorderLayout.CENTER);  
        add(panelInferior, BorderLayout.SOUTH); 

        setVisible(true);
    }
}


//Ventana PEDIR LIBRO
private class VentanaPedirLibro extends JFrame {
    public VentanaPedirLibro(Biblioteca biblioteca) {
        super("Pedir libro");
        setSize(450, 300);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null); // Centra la ventana

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0; // Permite expandir horizontalmente

        // ---- Campos ----
        JLabel lblFechaRetiro = new JLabel("Fecha de retiro (dd/MM/yyyy):");
        JTextField txtFechaRetiro = new JTextField(20); // Aumenta el ancho

        //JLabel lblFechaDevolucion = new JLabel("Fecha de devolución (dd/MM/yyyy):");
        //JTextField txtFechaDevolucion = new JTextField(20);

        JLabel lblDni = new JLabel("DNI del socio:");
        JTextField txtDni = new JTextField(20);

        JLabel lblTitulo = new JLabel("Título del libro:");
        JTextField txtTitulo = new JTextField(20);

        JButton btnPedirLibro = new JButton("Pedir Libro");

        // ---- FILAS ----
        c.gridx = 0; c.gridy = 0;
        add(lblFechaRetiro, c);
        c.gridx = 1;
        add(txtFechaRetiro, c);

        //c.gridx = 0; c.gridy = 1;
        //add(lblFechaDevolucion, c);
        //c.gridx = 1;
        //add(txtFechaDevolucion, c);

        c.gridx = 0; c.gridy = 2;
        add(lblDni, c);
        c.gridx = 1;
        add(txtDni, c);

        c.gridx = 0; c.gridy = 3;
        add(lblTitulo, c);
        c.gridx = 1;
        add(txtTitulo, c);

        // ---- Botón ----
        c.gridx = 0; c.gridy = 4;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(btnPedirLibro, c);

        // ---- EVENTO BOTÓN ----
        btnPedirLibro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    Date fechaR = sdf.parse(txtFechaRetiro.getText());
                    //Date fechaD = sdf.parse(txtFechaDevolucion.getText());

                    Calendar fechaRetiro = Calendar.getInstance();
                    fechaRetiro.setTime(fechaR);

                    int dni = Integer.parseInt(txtDni.getText());
                    String titulo = txtTitulo.getText();

                    Socio socio = biblioteca.buscarSocio(dni);
                    Libro libro = biblioteca.buscarLibro(titulo);

                    //Prestamo prestamo = new Prestamo(fechaRetiro, socio, libro);
                    //libro.agregarPrestamo(prestamo);
                    biblioteca.prestarLibro(fechaRetiro,socio,libro);
                    JOptionPane.showMessageDialog(null, "Préstamo registrado correctamente.");
                    
                    removerSocio(dni);
                    grabarSocio(socio, true);
                    removerLibro(titulo);
                    grabarLibro(libro, true);
                    refrescarBiblioteca();
                    
                    dispose();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: revise los datos o el formato de fecha (dd/MM/yyyy)");
                    e.printStackTrace();
                }
            }
        });

        setVisible(true);
    }
}

//Ventana QUIEN TIENE EL LIBRO
private class VentanaQuienTieneElLibro extends JFrame {
    public VentanaQuienTieneElLibro() {
       super("Quien tiene el libro");
            setSize(300, 150);
            setLayout(new FlowLayout());
            

            add(new JLabel("Ingrese el nombre del libro:"));
            JTextField txtTitulo = new JTextField(15);
            add(txtTitulo);

            
            JButton btnBuscar = new JButton("Buscar");
            add(btnBuscar);

            setLocationRelativeTo(null);
            setVisible(true);
            
             btnBuscar.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evento){
                 String titulo = txtTitulo.getText().trim();
                 Libro libro = biblioteca.buscarLibro(titulo);
                 //Libro libro = biblioteca.buscarLibro(titulo);
                System.out.println("titulo buscado: >" + txtTitulo.getText() + "<");
                if(libro == null){
                    JOptionPane.showMessageDialog(null, "No se encontró el libro \"" + titulo + "\".");
                    return;
                }
                 try {
                     JOptionPane.showMessageDialog(null, biblioteca.quienTieneElLibro(libro));
                     System.out.println(biblioteca.quienTieneElLibro(libro));
                     
                } catch (LibroNoPrestadoException e) {
                    JOptionPane.showMessageDialog(null,
                    "El libro no está prestado.","Aviso", JOptionPane.WARNING_MESSAGE);
                    
                } finally {
                   dispose();
            }
                }
            });
 }
    }
    /**
    btnBuscar.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent evento){
        String titulo = txtTitulo.getText().trim();
        Libro libro = biblioteca.buscarLibro(titulo);

        if(libro == null){
            JOptionPane.showMessageDialog(null,
                "No se encontró el libro con ese título.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String socio = biblioteca.quienTieneElLibro(libro);
            JOptionPane.showMessageDialog(null,
                "El libro está prestado a: " + socio,
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } catch (LibroNoPrestadoException e) {
            JOptionPane.showMessageDialog(null,
                "El libro no está prestado.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
});
*/
    
    
    
//ventana LISTAR DOCENTES RESPONSABLES
private class VentanaDocentesResponsables extends JFrame {
        public VentanaDocentesResponsables(Biblioteca biblioteca) {
        super("Listado de docentes responsables");
        setSize(350, 250);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        
        String lista = biblioteca.listaDeDocentesResponsables();
        area.setText(lista);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

    
}


