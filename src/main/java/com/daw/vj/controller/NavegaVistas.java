/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daw.vj.controller;

import com.daw.vj.dao.ClienteDAO;
import com.daw.vj.dao.ClientesDAOList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan Béjar
 */
@WebServlet(name = "NavegaVistas", urlPatterns = {"/app/*"})
public class NavegaVistas extends HttpServlet {

    private final String srvViewPath = "/WEB-INF/app";
    private String srvUrl;
    private static final Logger Log = Logger.getLogger(NavegaVistas.class.getName());
        private ClienteDAO clientes;  //Lista con los clientes
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        Log.info("Iniciando NavegaVistasController");
        srvUrl = servletConfig.getServletContext().getContextPath() + "/app";
        clientes = new ClientesDAOList();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("srvUrl", srvUrl);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        RequestDispatcher rd;
        String action = (request.getPathInfo() != null ? request.getPathInfo() : "");
        switch (action) {
            case "/biblioteca": {
                rd = request.getRequestDispatcher(srvViewPath + "/biblioteca.jsp");
                break;
            }
            case "/comunidad": {
                rd = request.getRequestDispatcher(srvViewPath + "/comunidad.jsp");
                break;
            }
            case "/tienda": {
                rd = request.getRequestDispatcher(srvViewPath + "/tienda.jsp");
                break;
            }
            case "/index": {
                rd = request.getRequestDispatcher(srvViewPath + "/index.jsp");
                break;
            }
            case "/busqueda": {
                rd = request.getRequestDispatcher("/WEB-INF/app/busqueda.jsp");
                break;
            }
            default: {
                rd = request.getRequestDispatcher(srvViewPath + "/comunidad.jsp");
                break;
            }
        }

        rd.forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String busca_nombre = request.getParameter("busca_nombre"); //Valor introducido en la barra de busqueda
        request.getSession().setAttribute("busca_nombre", busca_nombre);
        request.getSession().setAttribute("usuarios", clientes.buscaNombre(busca_nombre));
        
        response.sendRedirect("busqueda");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
