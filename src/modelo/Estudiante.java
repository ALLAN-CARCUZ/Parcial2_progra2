package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class Estudiante extends Persona{
    
    private String carnet,genero,email;
    private Conexion cn;

    public Estudiante() {}
    public Estudiante(String carnet, String genero, String email, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.carnet = carnet;
        this.genero = genero;
        this.email = email;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query;
        query = "Select id_estudiante as carnet,genero,email,nombres,apellidos,direccion,telefono,fecha_nacimiento from clientes;";
         ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
         
          String encabezado[] = {"id","Carnet","Genero","Email","Nombres","Apellidos","Direccion","Telefono","Nacimiento"};
          tabla.setColumnIdentifiers(encabezado);
          
          String datos[]=new String[7];
          while(consulta.next()){
      datos[0] = consulta.getString("id");
      datos[1] = consulta.getString("carnet");
      datos[2] = consulta.getString("genero");
      datos[3] = consulta.getString("email");
      datos[4] = consulta.getString("nombres");
      datos[5] = consulta.getString("apellidos");
      datos[6] = consulta.getString("direccion");
      datos[7] = consulta.getString("telefono");
      datos[8] = consulta.getString("fecha_nacimiento");
      tabla.addRow(datos);
      }
      cn.cerrar_conexion();
         
    }catch(SQLException ex){
        cn.cerrar_conexion();
    System.out.println("Error: "+ ex.getMessage());
    }
    return tabla;
    }
    @Override
    public void agregar(){
    try{
        PreparedStatement parametro;
        String query ="INSERT INTO estudiantes(carnet,nombres,apellidos,direccion,telefono,genero,email,fecha_nacimiento) VALUES(?,?,?,?,?,?,?,?);";
        cn = new Conexion();
        cn.abrir_conexion();
        parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
        parametro.setString(1, getCarnet());
        parametro.setString(2, getNombres());
        parametro.setString(3, getApellidos());
        parametro.setString(4, getDireccion());
        parametro.setString(5, getTelefono());
        parametro.setString(6, getGenero());
        parametro.setString(7, getEmail());
        parametro.setString(8, getFecha_nacimiento());
        
        int executar = parametro.executeUpdate();
        cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro ingresado","Agregar",JOptionPane.INFORMATION_MESSAGE);
         
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ ex.getMessage());
     }
    };
    @Override
    public void actualizar(){
   try{
         PreparedStatement parametro;
         cn = new Conexion();
         cn.abrir_conexion();
         String query;
        query = "update estudiantes set carnet = ?,nombres= ?,apellidos= ?,direccion= ?,telefono= ?,genero= ?,email= ?,fecha_nacimiento= ? "+
                 "where id_estudiante = ?";
         parametro  = (PreparedStatement) cn.conexionBD.prepareStatement(query);
         parametro.setString(1, getCarnet());
         parametro.setString(2, getNombres());
         parametro.setString(3, getApellidos());
         parametro.setString(4, getDireccion());
         parametro.setString(5, getTelefono());
         parametro.setString(6, getGenero());
         parametro.setString(7, getEmail());
         parametro.setString(8, getFecha_nacimiento());
         parametro.setInt(9, getId_estudiantes());
         
         int executar= parametro.executeUpdate();
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Actualizado",
             "Mensaje",JOptionPane.INFORMATION_MESSAGE);
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ex.getMessage());
     }
    }
    @Override
    public void eliminar(){
    try{
         PreparedStatement parametro;
         cn = new Conexion();
         cn.abrir_conexion();
         String query;
        query = "delete from estudiantes where id_estudiante = ?";
                 
         parametro  = (PreparedStatement) cn.conexionBD.prepareStatement(query);
         parametro.setInt(1, getId());
         
         int executar= parametro.executeUpdate();
         cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Eliminado",
             "Mensaje",JOptionPane.INFORMATION_MESSAGE);
     }catch(HeadlessException | SQLException ex){
         System.out.println("Error"+ex.getMessage());
     }
    }
    
}
