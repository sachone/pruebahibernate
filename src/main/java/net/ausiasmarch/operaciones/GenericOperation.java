package net.ausiasmarch.operaciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GenericOperation {

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}