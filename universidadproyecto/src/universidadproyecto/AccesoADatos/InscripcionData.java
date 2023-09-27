package universidad86.AccesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universidad86.entidades.Alumno;
import universidad86.entidades.Inscripcion;
import universidad86.entidades.Materia;


public class InscripcionData {
     private Connection con=null;
     private MateriaData md= new MateriaData();
     private AlumnoData ad = new AlumnoData();
     
    
    public InscripcionData(){
        this.con=Conexion.getConexion();
    }
    
    public void guardarInscripcion (Inscripcion inscripcion){
        String sql= "INSERT INTO inscripcion (nota, idAlumno, idMateria) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, (int) inscripcion.getNota());
            ps.setInt(2, inscripcion.getAlumno().getIdAlumno());
            ps.setInt(3, inscripcion.getMateria().getIdMateria());
            ps.executeUpdate();
            ResultSet rs= ps.getGeneratedKeys();
            if(rs.next()){
                inscripcion.setIdInscripto(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Inscripcion exitosa");  
            }
            ps.close(); 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al inscribir "+ex.getMessage());
        }
    }
    
    public List<Inscripcion> obtenerInscripciones(){
        List <Inscripcion> cursada =new ArrayList<>();
    try{
    String sql = "SELECT * FROM inscripcion";
    PreparedStatement ps= con.prepareStatement(sql);
    ResultSet rs= ps.executeQuery();
    while (rs.next()){
        Inscripcion insc = new Inscripcion();
        insc.setIdInscripto(rs.getInt("idInscripto"));        
        Alumno alu =ad.buscarAlumno(rs.getInt("idAlumno"));
        Materia mat = md.buscarMateria(rs.getInt("idMateria"));
        insc.setAlumno(alu);
        insc.setMateria(mat);
        insc.setNota(rs.getDouble("nota"));
        cursada.add(insc); 
            }    ps.close();
}catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion"+ex.getMessage());
                    }
        return cursada ;
    }
    
}

