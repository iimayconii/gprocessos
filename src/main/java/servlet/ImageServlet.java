/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Util;

/**
 *
 * @author maycon
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {                        
            byte[] data;
            if (request.getPathInfo().equalsIgnoreCase("/user")) {
                InputStream resourceAsStream = getClass().getResourceAsStream("user.png");
                data = Util.fileToByte(resourceAsStream);             
            } else if (new File(System.getProperty("java.io.tmpdir"), request.getPathInfo()).exists()) {
                System.out.println("Teste existe");
                File file = new File(System.getProperty("java.io.tmpdir"), request.getPathInfo());
                data = Util.fileToByte(file);
            } else {
                System.out.println("Entramos aqui");
                data = Util.fileToByte(new File(request.getPathInfo()));                
            }            
            OutputStream output = response.getOutputStream();
            output.write(data);
            output.close();
        } catch (Exception ex) {
            Logger.getLogger(ImageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}