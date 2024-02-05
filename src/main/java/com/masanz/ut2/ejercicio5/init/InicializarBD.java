package com.masanz.ut2.ejercicio5.init;

import com.masanz.ut2.ejercicio5.dto.Usuario;
import com.masanz.ut2.ejercicio5.dto.Objeto;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class InicializarBD {

    private Connection conexion;
    private String bd;
    private String usuario;
    private String contrasena;
    public static String BASE_PATH = "resources/";

    public InicializarBD(String bd, String usuario, String contrasena) {
        this.bd = bd;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public boolean conectar() {
        boolean exito = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + bd, usuario, contrasena);
            conexion.setAutoCommit(false);
            System.out.println("CONEXIÓN EXITOSA");
            exito = true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error en la conexión con la BBDD. " + e);
        }
        return exito;
    }

    public boolean desconectar() {
        boolean exito = false;
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                conexion.close();
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error desconectando con la BBDD. " + e);
        }
        return exito;
    }

    public boolean testSchema() {
        boolean testValidation = conectar();
        if (testValidation) {
            desconectar();
        }
        return testValidation;
    }

    public boolean testUsuarios() {
        boolean testValidation = false;
        if (conectar()) {
            String sql = "SELECT * FROM usuarios LIMIT 1";
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                // Si tenemos al menos un resultado, TENEMOS USUARIOS
                testValidation = rs.next();
                pst.close();
            } catch (SQLException e) {
                System.out.println("ERROR EJECUTANDO LA CONSULTA: " + sql + "\n" + e);
            }
            desconectar();
        }
        return testValidation;
    }

    public int crearUsuarios() {
        String sqlCreate = leerArchivo("create-usuarios.sql");
        System.out.println(ejecutarSQL(sqlCreate));
        String sqlInserts = leerArchivo("insert-usuarios.sql");
        System.out.println(ejecutarSQL(sqlInserts));
        return -1;
    }

    private int ejecutarSQL(String sql) {
        int registrosAfectados = -1;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                registrosAfectados = pst.executeUpdate();
                conexion.commit();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            desconectar();
        }
        return registrosAfectados;
    }

    private String leerArchivo(String nombreArchivo) {
        File file = new File(BASE_PATH + "/" + nombreArchivo);
        if (file.isDirectory()) {
            return null;
        } else {
            String content = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(BASE_PATH + "/" + nombreArchivo));
                String line = br.readLine();
                while (line != null) {
                    content += line + "\n";
                    line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                return content;
            }
        }
    }

    public boolean autenticar(String usuario, String contrasena) {
        String sql = "SELECT * FROM USUARIOS WHERE user = '" + usuario + "' and password = '" + contrasena + "'";
        boolean autenticado = false;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return autenticado;
    }

    public Usuario getDatosUsuario(String usuario, String contrasena) {
        String sql = "SELECT * FROM USUARIOS WHERE user = '" + usuario + "' AND password = '" + contrasena + "' LIMIT 1";
        Usuario u = null;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setEmail(rs.getString("email"));
                    u.setUser(rs.getString("user"));
                    u.setPassword(rs.getString("password"));
                    u.setSaldo(rs.getFloat("saldo"));
                    u.setCreationDate(rs.getDate("creation_date"));
                    u.setModificationDate(rs.getDate("modification_date"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return u;
    }

    public ArrayList<Objeto> obtenerObjetosUsuario(int userId) {
        String sql = "SELECT * FROM OBJETOS WHERE userId  = " + userId;
        ArrayList<Objeto> objetoList = null;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                objetoList = new ArrayList<>();

                while (rs.next()) {
                    Objeto o = new Objeto(rs.getInt("id"), rs.getString("nombre"), rs.getFloat("valor"), rs.getInt("userId"));
                    objetoList.add(o);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return objetoList;
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        String sql = "SELECT * FROM usuarios";
        ArrayList<Usuario> usuarios = null;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                usuarios = new ArrayList<>();

                while (rs.next()) {
                    Usuario u = new Usuario(rs.getString("full_name"), rs.getString("user"), rs.getString("email"), rs.getString("password"), rs.getFloat("saldo"));
                    u.setId(rs.getInt("id"));
                    usuarios.add(u);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return usuarios;
    }

    public float comprarObjeto(int idArticulo, int idComprador, float saldoComprador) {
        float cantidadGastada = -1;
        boolean puedeComprar = false;
        float precioArticulo = 0;
        String sqlObjeto = "SELECT valor FROM objetos WHERE id=" + idArticulo;
        if (conectar()) {
            try {
                PreparedStatement pst = conexion.prepareStatement(sqlObjeto);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    precioArticulo = rs.getFloat("valor");
                    puedeComprar = precioArticulo < saldoComprador;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (puedeComprar) {
            String sql = "UPDATE objetos SET userId = ? WHERE id = ?";
            if (conectar()) {
                try {
                    PreparedStatement pst = conexion.prepareStatement(sql);
                    pst.setInt(1,idComprador);
                    pst.setInt(2,idArticulo);
                    System.out.println(pst);
                    pst.executeUpdate();
                    pst.close();
                    cantidadGastada = precioArticulo;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return cantidadGastada;
    }
}