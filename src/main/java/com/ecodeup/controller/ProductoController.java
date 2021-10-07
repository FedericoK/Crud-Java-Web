package com.ecodeup.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecodeup.dao.ProductoDAO;
import com.ecodeup.model.producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "Controlador de productos", urlPatterns = { "/producto" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String opcion = request.getParameter("opcion");
		
		if(opcion.equals("crear")) {
			String registerForm = "/views/crear.jsp";
			RequestDispatcher requestDispatcher; 
			requestDispatcher = request.getRequestDispatcher(registerForm);
			requestDispatcher.forward(request, response);
			System.out.println("A presionado Crear");
			
		}else if(opcion.equals("listar")){
			ProductoDAO productoDAO = new ProductoDAO();
			List<producto> lista = new ArrayList<>();
			try {
				lista=productoDAO.obtenerProductos();
				for(producto producto : lista) {
					System.out.println(producto);
				}
				request.setAttribute("lista", lista);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
				requestDispatcher.forward(request, response);
			}catch (Exception e) {
				// TODO: handle exception
			}
			request.setAttribute("lista", lista);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
			requestDispatcher.forward(request, response);
		  	System.out.println("A presionado Listar");
		  	
		}else if(opcion.equals("editar")) {
			int id = Integer.parseInt(request.getParameter("id"));
			System.out.println("Editar id: " + id);
			ProductoDAO productoDAO = new ProductoDAO();
			producto p = new producto();
			
			try {
				p = productoDAO.obtenerProducto(id);
				System.out.println(p);
				request.setAttribute("producto", p);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(opcion.equals("eliminar")) {
			ProductoDAO productoDAO= new ProductoDAO();
			int id=Integer.parseInt(request.getParameter("id"));
			System.out.println("Registro Eliminado Satisfactoriamente...");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
			requestDispatcher.forward(request, response);
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcion = request.getParameter("opcion");
		Date fechaActual=new Date();
		
		if(opcion.equals("guardar")) {
			ProductoDAO productoDAO= new ProductoDAO();
			producto producto=new producto();
			producto.setNombre(request.getParameter("nombre"));
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
			
			try {
				productoDAO.guardar(producto);
				System.out.println("Registro guardado satisfactoriamente...");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}else if(opcion.equals("Actualizar")) {
			producto p = new producto();
			ProductoDAO productoDAO= new ProductoDAO();
			
			p.setId(Integer.parseInt(request.getParameter("id")));
			p.setNombre(request.getParameter("nombre"));
			p.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			p.setPrecio(Double.parseDouble(request.getParameter("precio")));
			p.setFechaActualizar(new java.sql.Date(fechaActual.getTime()));
			
			try {
				productoDAO.editar(p);
				System.out.println("Registro Editado Guardado Satisfactoriamente...");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//doGet(request, response);
	}

}
