/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Amanda
 */
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
         
         String SQL = "INSERT INTO tabla (guardados)VALUES (?)";
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
    
    public void printInOrden(int indice) throws SQLException{
        Conexion con = new Conexion();
        con.conector();
        
        String SQL = "UPDATE tabla SET inOrden= ? WHERE id = ?"; 
        PreparedStatement pst ;
        
        if(izdo != null ){
            pst= con.prepareStatement(SQL);
            pst.setString(1,valor);
            pst.setInt(2,indice);
            pst.execute();
            izdo.printInOrden(indice++);
        }
        System.out.println(valor);
        
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
            dcho.printInOrden(indice++);
        }
    }
    
    public void printPreOrder(int indice) throws SQLException {
        Conexion con = new Conexion();
        con.conector();
        
        String SQL = "UPDATE tabla SET preOrden= ? WHERE id = ?";
        PreparedStatement pst= con.prepareStatement(SQL);
        pst.setString(1,valor);
        pst.setInt(2, indice);
        pst.execute();
        System.out.println(valor);
        if (izdo != null) {
            indice++;
            izdo.printPreOrder(indice++);
        }
        if (dcho != null) {
            indice++;
            dcho.printPreOrder(indice++);
        }
    }
    
    public void printPosOrder() {
        if (izdo != null) {
            izdo.printPosOrder();
        }
        if (dcho != null) {
            dcho.printPosOrder();
        }
        System.out.println(valor);
    } 
    
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
