package agendaContactos;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        Scanner scanner = new Scanner(System.in);

        try {
            connection = MySQLConnection.getConnection();
            statement = connection.createStatement();

            while (true) {
                System.out.println("Bienvenido a su agenda");
                System.out.println("Seleccione la tarea que quiere realizar");
                System.out.println("1 Agregar Contacto");
                System.out.println("2 Editar Contacto");
                System.out.println("3 Borrar Contacto");
                System.out.println("4 Buscar Contacto ");
                System.out.println("5 Mostrar Todos");
                System.out.println("6 Exit");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        // Agregar contacto//
                        System.out.println("Ingrese Nombre:");
                        String nombre = scanner.nextLine();
                        System.out.println("Ingrese email:");
                        String email = scanner.nextLine();
                        System.out.println("Ingrese numero de telefono:");
                        String telefono = scanner.nextLine();
                        Contacto nuevoContacto = new Contacto(nombre, email, telefono);
                        String ingresoConsulta = "INSERT INTO mytable (nombre, email, telefono) VALUES (?, ?, ?)";
                        PreparedStatement ingresoRegistro = connection.prepareStatement(ingresoConsulta);
                        ingresoRegistro.setString(1, nuevoContacto.getNombre());
                        ingresoRegistro.setString(2, nuevoContacto.getCorreo());
                        ingresoRegistro.setString(3, nuevoContacto.getTelefono());
                        ingresoRegistro.executeUpdate();
                        System.out.println("Contacto Agregado!");

                        break;
                        
                        
                    case 2:
                        // Editar contacto//
                        System.out.println("Ingrese ID del contacto a editar:");
                        int editarId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        System.out.println("Ingrese el Nuevo nombre:");
                        nombre = scanner.nextLine();
                        System.out.println("Ingrese el nuevo email:");
                        email = scanner.nextLine();
                        System.out.println("Ingrese el nuevo numero de telefono :");
                        telefono = scanner.nextLine();
                        Contacto editarContacto = new Contacto(nombre, email, telefono);
                        String actualizacionContacto = "UPDATE mytable SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
                        PreparedStatement actualizarRegistro = connection.prepareStatement(actualizacionContacto);
                        actualizarRegistro.setString(1, editarContacto.getNombre());
                        actualizarRegistro.setString(2, editarContacto.getCorreo());
                        actualizarRegistro.setString(3, editarContacto.getTelefono());
                        actualizarRegistro.setInt(4, editarId);
                        actualizarRegistro.executeUpdate();
                        System.out.println("Contacto editado");

                        break;
                        
                        
                    case 3:
                        // Eliminar contacto//
                        System.out.println("Ingrese ID del contacto a eliminar");
                        int borrarID = scanner.nextInt();
                        String borrarConsulta = "DELETE FROM mytable WHERE id = ?";
                        PreparedStatement borrarStatement = connection.prepareStatement(borrarConsulta);
                        borrarStatement.setInt(1, borrarID);
                        borrarStatement.executeUpdate();
                        System.out.println("Contacto eliminado");

                        break;
                        
                        
                    case 4:
                        // Buscar contacto//
                        System.out.println("Ingrese busqueda ");
                        String buscarTermino = scanner.nextLine();
                        String bucarConsulta = "SELECT * FROM mytable WHERE nombre LIKE ? OR email LIKE ? OR telefono LIKE ?";
                        PreparedStatement consultaBusqueda = connection.prepareStatement(bucarConsulta);
                        consultaBusqueda.setString(1, "%" + buscarTermino + "%");
                        consultaBusqueda.setString(2, "%" + buscarTermino + "%");
                        consultaBusqueda.setString(3, "%" + buscarTermino + "%");
                        ResultSet resultadBusqueda = consultaBusqueda.executeQuery();
                        
                        boolean resultado = false;

                        while (resultadBusqueda.next()) {
                        	resultado = true;
                            int id = resultadBusqueda.getInt("id");
                            String contactoNombre = resultadBusqueda.getString("nombre");
                            String contactoEmail = resultadBusqueda.getString("email");
                            String contactoTelefono = resultadBusqueda.getString("telefono");
                            System.out.println("Contact found:");
                            System.out.println("ID: " + id);
                            System.out.println("Nombre: " + contactoNombre);
                            System.out.println("Email: " + contactoEmail);
                            System.out.println("Telefono: " + contactoTelefono);
                            System.out.println();
                        }
                        
                        if (!resultado) {
                            System.out.println("No se encontro ningun contacto ");
                        }
                        

                        break;

                    case 5:
                        // Ver todos los contactos//
                        String consultaSeleccionarTodo = "SELECT * FROM mytable";
                        ResultSet todosLosContactos = statement.executeQuery(consultaSeleccionarTodo);
                        while (todosLosContactos.next()) {
                            int id = todosLosContactos.getInt("id");
                            String contactoNombre = todosLosContactos.getString("nombre");
                            String contactoEmail = todosLosContactos.getString("email");
                            String contactoTelefono = todosLosContactos.getString("telefono");
                            System.out.println("ID: " + id);
                            System.out.println("nombre: " + contactoNombre);
                            System.out.println("Email: " + contactoEmail);
                            System.out.println("telefono: " + contactoTelefono);
                            System.out.println();
                        }

                        break;
                        
                        
                    case 6:
                        // Salir del programa//
                        System.out.println("FIN");
                        return;
                    default:
                        System.out.println("Opcion invalida, Reintente");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                scanner.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
