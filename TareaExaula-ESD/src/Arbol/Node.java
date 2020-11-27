package Arbol;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Node {
    private String valor;
    private Node izdo;
    private Node dcho;

    //constructor
    public Node(String valor) throws SQLException {
        this.valor = valor;
        izdo = null;
        dcho = null;
        //--------Guardar en la bd en orden de ingreso---------
         Conexion con = new Conexion();
         con.conector(); 
         
         String SQL = "INSERT INTO alumnos (inscripcion)VALUES (?)";
         PreparedStatement pst = con.prepareStatement(SQL);
         pst.setString(1,valor);
         pst.execute();
         //-----------------------------------------------------
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Node getIzdo() {
        return izdo;
    }

    public void setIzdo(Node izdo) {
        this.izdo = izdo;
    }

    public Node getDcho() {
        return dcho;
    }

    public void setDcho(Node dcho) {
        this.dcho = dcho;
    }
    
    //metodo para agregar un nuevo nodo al arbol
    public void Adicionar(String valor) throws SQLException{
        if(valor.compareToIgnoreCase(this.valor)<0){
            if(izdo != null){
                izdo.Adicionar(valor);
            }else
                izdo = new Node(valor);
        }else{
            if(dcho != null){
                dcho.Adicionar(valor);
            }else{
                dcho = new Node(valor);
            }
        }
    }
    
    //metodo para recorrer y guarddar el árbol EnOrden
    public void inOrden(int indice) throws SQLException{
        Conexion con = new Conexion();//establecer conexión 
        con.conector();               //con la base de datos
        
        String SQL = "UPDATE alumnos SET inOrden= ? WHERE id = ?"; 
        PreparedStatement pst ;
        
        if(izdo != null ){
            pst= con.prepareStatement(SQL);
            pst.setString(1,valor);
            pst.setInt(2,indice);
            pst.execute();
            izdo.inOrden(indice++);
        }
        
        pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2,indice);
        pst.execute();
        indice++;
        if(dcho != null){
            pst= con.prepareStatement(SQL);
            pst.setString(1,valor);
            pst.setInt(2,indice);
            pst.execute();
            dcho.inOrden(indice++);
        }
    }
    
    //metodo para recorrer y guardar el árbol en preOrden
    public void preOrder(int indice) throws SQLException {
        Conexion con = new Conexion();
        con.conector();
        
        String SQL = "UPDATE alumnos SET preOrden= ? WHERE id = ?";
        PreparedStatement pst;
        pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
        indice++;
        
        if (izdo != null) {
            pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
            izdo.preOrder(indice++);
        }
        if (dcho != null) {
            pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
            dcho.preOrder(indice++);
        }
    }
    
    //metodo para recorrer y guardar el árbol en porOrden
    public void posOrden(int indice) throws SQLException {
        Conexion con = new Conexion();
        con.conector();
        
        String SQL = "UPDATE alumnos SET posOrden= ? WHERE id = ?";
        PreparedStatement pst;
        pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
        
        if (izdo != null) {
            pst= con.prepareStatement(SQL);
            pst.setString(1,valor);
            pst.setInt(2, indice);
            pst.execute();
            izdo.posOrden(indice++);
        }
        if (dcho != null) {
            pst= con.prepareStatement(SQL);
            pst.setString(1,valor);
            pst.setInt(2, indice);
            pst.execute();
        
            dcho.posOrden(indice++);
        }
        
        pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
        indice++;
    } 
    
    //metodo para buscar un dato en el árbol
    public boolean buscar(String v){
        boolean resp = false;
        if(!(v.equalsIgnoreCase(this.valor))){
            if(v.compareToIgnoreCase(this.valor)>0){
                if(dcho != null){
                    resp = dcho.buscar(v);
                }
            }else{
                if(izdo != null){
                    resp = izdo.buscar(v);
                }
            }
        }else{
            resp = true;
        }
        return resp;
    }

    @Override
    public String toString() {
        return "Node[valor= "+valor+", izdo= "+izdo+", dcho= "+dcho+"]";
    }
}
