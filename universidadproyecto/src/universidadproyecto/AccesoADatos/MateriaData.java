package universidad86.AccesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidad86.entidades.Alumno;
import universidad86.entidades.Materia;


public class MateriaData {
     private Connection con=null;
    
    public MateriaData(){
        con=Conexion.getConexion();
    }
    
    public void guardarMateria (Materia materia){
        String sql= "INSERT INTO materia(nombre, anio, estado) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setBoolean(3, materia.isEstado());
            ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            if(rs.next()){
                materia.setIdMateria(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Materioa añadida con exito");  
            }
            ps.close(); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materias "+ex.getMessage());
        }
    }
    public Materia buscarMateria (int id){
        Materia materia =null;
        String sql= "SELECT nombre, anio, estado FROM materia  WHERE idMateria = ? AND estado = 1";
        PreparedStatement ps=null;
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                materia = new Materia();
                materia.setIdMateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(true);
                
            }else{
                JOptionPane.showMessageDialog(null, "No existe la materia");
                ps.close();
            }
            
        }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materia"+ex.getMessage());
                    }
        return materia;
    }
      
    public List<Materia> listarMaterias(){
        List <Materia> materias =new ArrayList<>();
    try{
    String sql = "SELECT * FROM materia WHERE estado=1";
    PreparedStatement ps= con.prepareStatement(sql);
    ResultSet rs= ps.executeQuery();
    while (rs.next()){
        Materia materia = new Materia();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(true);
    }
    ps.close();
}catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno"+ex.getMessage());
                    }
        return materias;
    }
    public void modificarMateria(Materia materia){
        String sql="UPDATE materia  SET anio=?,,nombre=? WHERE idMateria=?";
        PreparedStatement ps=null;
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, materia.getAnio());
            ps.setString(2, materia.getNombre());
            ps.setInt(3, materia.getIdMateria());
            int exito=ps.executeUpdate();
            if(exito==1){
                JOptionPane.showMessageDialog(null, "Modicado");
            }else{
                JOptionPane.showMessageDialog(null, "No existe la materia");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Eror al acceder a la tabla Materia"+ex.getMessage());
        }
    }
    public void eliminarMateria(int id){
        try{
            String sql="UPDATE materia SET estado=0 WHERE idMateria=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila=ps.executeUpdate();
            if(fila==1){
               JOptionPane.showMessageDialog(null, "Se elimino la materia"); 
            }
            ps.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Eror al acceder a la tabla Materia ");
        }
    }
}


