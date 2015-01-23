/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.operaciones;

/**
 *
 * @author rafa
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import net.ausiasmarch.bean.Coche;
import net.ausiasmarch.dao.CocheDao;

public class CocheGet implements GenericOperation {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String data;         
        try {            
            if (request.getParameter("id") == null) {
                data = "{\"error\":\"id is mandatory\"}";
            } else {
                CocheDao oCocheDAO = new CocheDao();                             
                Coche oCoche = oCocheDAO.get(Integer.parseInt(request.getParameter("id")));
                data = new Gson().toJson(oCoche);
            }
            return data;
        } catch (Exception e) {
            throw new ServletException("CocheGetJson: View Error: " + e.getMessage());
        }
    }
}