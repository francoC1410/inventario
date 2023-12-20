package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class RegistroDao {

    ConnectionSQL cn = new ConnectionSQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public boolean registerProductQuery(Registro registro) {
        String query = "INSERT INTO registro (code, name, vencimiento, entrada) VALUES(?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getCode());
            pst.setString(2, registro.getName());
            pst.setString(3, registro.getDate());
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al ingresar el registro:  " + e.getMessage());
            return false;
        }
    }

    public List listProductQuery(String value) {
        List<Registro> list_registro = new ArrayList();
        String query = "SELECT * FROM registro";
        String query_search_category = "SELECT * FROM registro WHERE code LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setCode(rs.getString("code"));
                registro.setName(rs.getString("name"));
                registro.setCreated(rs.getString("entrada"));
                registro.setDate(rs.getString("vencimiento"));
                list_registro.add(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no se puede enlistar : " + e.toString());
        }
        return list_registro;
    }

    public boolean registerProductDetailsQuery(Registro registro) {
        String query = "INSERT INTO registro_detalle (code,name) VALUES(?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getCode());
            pst.setString(2, registro.getName());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al ingresar el registro2:  " + e.getMessage());
            return false;
        }
    }

    public List listProductDetailsQuery(String value) {
        List<Registro> list_registro = new ArrayList();
        String query = "SELECT * FROM registro_detalle";
        String query_search_category = "SELECT * FROM registro_detalle WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setId(rs.getInt("id"));
                registro.setCode(rs.getString("code"));
                registro.setName(rs.getString("name"));
                registro.setPackages(rs.getString("entven"));
                registro.setUpdated(rs.getString("salida"));
                list_registro.add(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no se puede enlistar2 : " + e.toString());
        }
        return list_registro;
    }

    public int registroId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM registro";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    public boolean updateProductQuery(Registro registro) {
        String query = "UPDATE registro_detalle SET code = ?, name = ?, entven = ?, salida = ? WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getCode());
            pst.setString(2, registro.getName());
            pst.setString(3, registro.getPackages());
            pst.setTimestamp(4, datetime);
            pst.setInt(5, registro.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al modificar los datos del producto " + e);
            return false;
        }
    }

    public List listProductTotalQuery() {
        List<Registro> list_registro = new ArrayList();
        String query = "select * from registro_total";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setCode(rs.getString("code"));
                registro.setName(rs.getString("name"));
                registro.setCreated(rs.getString("f_ingreso"));
                registro.setUpdated(rs.getString("f_egreso"));
                registro.setPackages(rs.getString("entven"));
                registro.setDate(rs.getString("vencimiento"));
                list_registro.add(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no se puede enlistar3 : " + e.toString());
        }
        return list_registro;
    }
    
    public boolean deleteRowsExceptNullEgreso2() {
    String query = "DELETE FROM registro_detalle WHERE salida IS NOT NULL";

    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.execute();
        return true;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar filas de la tabla: " + e);
        return false;
    }
}

    public boolean deleteProductQuery() {
        String query = "delete from registro" ;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " No puede eliminar un producto que tenga relación con otra tabla " + e);
            return false;
        }
    }
    
    public boolean registerProductTotalQuery(Registro registro) {
        String query = "INSERT INTO registro_total (code,name,f_ingreso, vencimiento) VALUES(?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getCode());
            pst.setString(2, registro.getName());
            pst.setTimestamp(3, datetime);
            pst.setString(4, registro.getDate());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al ingresar el registro3:  " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateProductTotalQuery(Registro registro) {
        String query = "UPDATE registro_total SET code = ?, name = ?, entven = ?, f_egreso = ? WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getCode());
            pst.setString(2, registro.getName());
            pst.setString(3, registro.getPackages());
            pst.setTimestamp(4, datetime);
            pst.setInt(5, registro.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al modificar los datos del producto2 " + e);
            return false;
        }
    }
    
    public boolean deleteRowsExceptNullEgreso() {
    String query = "DELETE FROM registro_total WHERE f_egreso IS NOT NULL";

    try {
        conn = cn.getConnection();
        pst = conn.prepareStatement(query);
        pst.execute();
        return true;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al eliminar filas de la tabla: " + e);
        return false;
    }
}
    
    public boolean registerQuantityQuery(Registro registro) {
        String query = "INSERT INTO registro_cantidad (mercadoL, cantidad1, pickit, cantidad2, created) VALUES(?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, registro.getName1());
            pst.setInt(2, registro.getQuantity());
            pst.setString(3, registro.getName2());
            pst.setInt(4, registro.getQuantity2());
            pst.setTimestamp(5, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al ingresar la cantidad requerida:  " + e.getMessage());
            return false;
        }
    }
    
    public List listQuantityTotalQuery() {
        List<Registro> list_registro = new ArrayList();
        String query = "select * from registro_cantidad";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setName1(rs.getString("mercadoL"));
                registro.setQuantity(rs.getInt("cantidad1"));
                registro.setName2(rs.getString("pickit"));
                registro.setQuantity2(rs.getInt("cantidad2"));
                registro.setCreated(rs.getString("created"));
                list_registro.add(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "no se puede enlistar5 : " + e.toString());
        }
        return list_registro;
    }
}
