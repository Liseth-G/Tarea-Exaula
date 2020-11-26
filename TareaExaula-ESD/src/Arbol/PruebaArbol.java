/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import Arbol.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Amanda
 */
public class PruebaArbol {
    public static void main(String[] arg) throws SQLException{
        
        PruebaArbol pA = new PruebaArbol();
  Conexion con = new Conexion();
  con.conector();
  int id=1;//-------------- 
  
 String valor;
 Node arbol=null;
 boolean conti = true, r;
 int resp;
 
 while ( conti )
 {
 if (arbol==null)
 {
 valor = JOptionPane.showInputDialog(null, "Digite un nombre para la raíz:");;
 System.out.println(valor);
 arbol = new Node(valor);
 }
 else
 {
 valor = JOptionPane.showInputDialog(null, "Digite el nombre a introducir:");
 
 System.out.println(valor);
 arbol.Adicionar(valor);
 }
 resp =Integer.parseInt(JOptionPane.showInputDialog(null, "¿Desea introducir otro nombre? 1: Si, 2: No"));
 conti = resp == 1;
 }

 
 System.out.println("Impresión del árbol en orden\n");
 arbol.printInOrden(id);
 //pA.consultaTabla();//----------------------------------
 
 System.out.println("Impresión del árbol en preorden\n");
  arbol.printPreOrder(id);
  //pA.consultaTabla();//----------------------------------

 System.out.println("Impresión del árbol en postorden\n");
 arbol.printPosOrden(id);
 //pA.consultaTabla();//----------------------------------
 
 valor = JOptionPane.showInputDialog(null, "Digite el nombre que desea buscar en el arbol:");
 r=arbol.buscar(valor);
 if ( r )
 System.out.println("El nombre "+valor+" está en el arbol\n");
 else
 System.out.println("El nombre "+valor+" no está en el arbol\n");
 
 }
  
  
  /*MÉTODO PARA REALIZAR UNA CONSULTA A UNA TABLA MYSQL*/
        private void consultaTabla() {
            Conexion con = new Conexion();
            con.conector();
            
        //Realizamos la consulta sql para mostrar todos los datos de la tabla estudiante
        ResultSet r = buscar("SELECT * FROM tabla ");
        try {
            
           
            /*
            Hacemos un While para recorrer toda la tabla estudiantes
            y así poder sacar todos los registros de la tabla
            */
            while (r.next()) {
                /*Se muestra los datos que queremos sacar por consola indicandole:
                        El tipo de dato (int,String...) de cada campo
                        El nombre de los campos de la tabla entre comillas doble " "
                */
                System.out.println(r.getString("preOrden"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PruebaArbol.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//mostrarTablaPropietarios
   
       
        //Este método lo uso para mostrar los datos de una tabla: (executeQuery)
    ResultSet buscar(String sql) {
        try {
            Conexion con = new Conexion();
            con.conector();
            PreparedStatement stm =  con.prepareStatement(sql);
            
            return stm.executeQuery(sql);

        } catch (SQLException ex) {
            Logger.getLogger(PruebaArbol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }//buscar
}
