package com.ecodeup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecodeup.conexion.Conexion;
import com.ecodeup.model.producto;

public class ProductoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	//GUARDA PRODUCTO
	public boolean guardar(producto producto) throws Exception {
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear, fecha_actualizar) VALUES(?,?,?,?,?,?)";
			statement=connection.prepareStatement(sql);
			//SE COLOCA LOS ATRIBUTOS DE LOS DATOS POR CADA ESPACIO CON "?"
			statement.setString(1, null);
			statement.setString(2, producto.getNombre());
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFechaCrear());
			statement.setDate(6, producto.getFechaActualizar());
			
			estadoOperacion=statement.executeUpdate() > 0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
	}
	
	//EDITAR PRODUCTO
	public boolean editar(producto producto) throws Exception {
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="UPDATE productos SET nombre=?, cantidad=? ,precio=?, fecha_actualizar=?) WHERE id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFechaActualizar());
			statement.setInt(5, producto.getId());
			
			estadoOperacion=statement.executeUpdate() > 0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
	}
	
	//ELIMINAR PRODUCTO
	public boolean eliminar(int idProducto) throws Exception{
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql="DELETE FROM productos WHERE id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setInt(1,idProducto);
			
			estadoOperacion=statement.executeUpdate() > 0;
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
	}
	
	//OBTIENE TODA LA LISTA DE PRODUCTOS
	public List<producto> obtenerProductos() throws Exception {
		//RESULTSET OBTIENE LOS VALORES DE CADA FILA DE LA TABLA
		ResultSet resultSet=null;
		//UNA LISTA DONDE PONER LOS VALORES DE LA TABLA
		List<producto> listaProductos = new ArrayList<>();
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();
		
		try {
			sql="SELECT * FROM productos";
			statement=connection.prepareStatement(sql);
			resultSet=statement.executeQuery(sql);
			//COLOCA TODOS LOS DATOS DE LA TABLA MIENTRAS TENGA FILAS
			while(resultSet.next()) {
				producto p=new producto();
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFechaCrear(resultSet.getDate(5));
				p.setFechaActualizar(resultSet.getDate(6));
				listaProductos.add(p);
			}
			
			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listaProductos;
	}
	
	//OBTIENE UNA LISTA DE PRODUCTOS POR ID
	public producto obtenerProducto(int idproducto) throws Exception {
		//RESULTSET OBTIENE LOS VALORES DE CADA FILA DE LA TABLA
				ResultSet resultSet=null;
				producto p=new producto();
				String sql=null;
				estadoOperacion=false;
				connection=obtenerConexion();
				
				try {
					connection.setAutoCommit(false);
					sql="SELECT * FROM productos WHERE id=?";
					statement=connection.prepareStatement(sql);
					statement.setInt(1, idproducto);
					
					resultSet=statement.executeQuery();
					
					if(resultSet.next()) {
						p.setId(resultSet.getInt(1));
						p.setNombre(resultSet.getString(2));
						p.setCantidad(resultSet.getDouble(3));
						p.setPrecio(resultSet.getDouble(4));
						p.setFechaCrear(resultSet.getDate(5));
						p.setFechaActualizar(resultSet.getDate(6));
					}
					
					connection.commit();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return p;
			}
	
	//OBTENER CONEXION POOL
	private Connection obtenerConexion() throws Exception{
		return Conexion.getConnection();
	}
}
