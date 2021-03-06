package Arbol;

import Arbol.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PruebaArbol {
    public static void main(String[] arg) throws SQLException{
        
     PruebaArbol pA = new PruebaArbol();
     Conexion con = new Conexion();
     con.conector();
     int id=1;//identificador para la bd 
  
     String valor;
     Node arbol=null;
     boolean conti = true, r;
     int resp;
     
     //-------Registrar los alumnos---------------
     while ( conti )
     {
     if (arbol==null)
     {
     valor = JOptionPane.showInputDialog(null, "Digite el nombre del primer"
             + " alumno a inscribir(raíz):");;
     System.out.println(valor);
     arbol = new Node(valor);
     }
     else
     {
     valor = JOptionPane.showInputDialog(null, "Digite el nombre del alumno"
             + " a inscribir:");

     System.out.println(valor);
     arbol.Adicionar(valor);
     }
     resp =Integer.parseInt(JOptionPane.showInputDialog(null, "¿Desea inscribir"
             + " otro alumno? 1: Si, 2: No"));
     conti = resp == 1;
     }
     //------------------------------------------
     
     //Guardar los datos en la bd-------------
     arbol.inOrden(id);
     arbol.preOrder(id);
     arbol.posOrden(id);
     //---------------------------------------
     
     //Hacer la impresion dependiendo de la opción seleccionada ---------------
     int ord;//almacena el tipo de orden a imprimir 
     do{
         ord=Integer.parseInt(JOptionPane.showInputDialog(null,"¿En que orden"
                 + " desea ver el árbol?"
             + "\n1:EnOrden \n2: PreOrden \n3: PosOrden"));
         if(ord==1){
         System.out.println("\n\nImpresión del árbol en orden:\n");
         pA.consultaTabla("inOrden");
         }else{
             if(ord==2){
             System.out.println("\n\nImpresión del árbol en preorden:\n");
             pA.consultaTabla("preOrden");
             }else{
             if(ord==3){
                 System.out.println("\n\nImpresión del árbol en postorden:\n");
                 pA.consultaTabla("posOrden");
             }else{
                 JOptionPane.showMessageDialog(null,"Seleccione una opción"
                         + " valida");
             }
         }
         }
     }while(ord!=1 && ord!=2 && ord!=3);//mientras no seleccione correctamente
     //-------------------------------------------------------------------------
     
     //------------------Busca un alumno denntro del árbol---------------------
     valor = JOptionPane.showInputDialog(null, "Digite el nombre del alumno que"
             + " desea buscar en el árbol:");
     r=arbol.buscar(valor);
     if ( r ){
         System.out.println("\nEl alumno "+valor+" está en el curso(arbol)\n");
     }else{
        System.out.println("El alumnno "+valor+" no está en el curso(arbol)\n");
     }
     //--------------------------------------------------------------------------
 }
  
  
  //MÉTODO PARA REALIZAR UNA CONSULTA A LA TABLA MYSQL
 private void consultaTabla(String orden) {
    Conexion con = new Conexion();
    con.conector();
    ResultSet r = buscar("SELECT * FROM alumnos ");//sentencia sql
    try {
        while (r.next()) {
            //Se muestra los datos que queremos sacar por consola
            System.out.println(r.getString(orden));
        }
    } catch (SQLException ex) {
       Logger.getLogger(PruebaArbol.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
   
    //método para mostrar los datos de una tabla: (executeQuery)
 ResultSet buscar(String sql) {
    try {
        Conexion con = new Conexion();
        con.conector();
        PreparedStatement stm =  con.prepareStatement(sql);

        return stm.executeQuery(sql);

    } catch (SQLException ex) {
        Logger.getLogger(PruebaArbol.class.getName()).log(Level.SEVERE,null,ex);
    }
    return null;
}
}
