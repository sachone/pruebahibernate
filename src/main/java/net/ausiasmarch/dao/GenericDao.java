package net.ausiasmarch.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ausiasmarch.helper.FilterBean;

public interface GenericDao<TIPO_ENTIDAD extends Serializable> {

    int create(TIPO_ENTIDAD entity) throws Exception;

    void set(TIPO_ENTIDAD entity) throws Exception;

    void remove(TIPO_ENTIDAD entity) throws Exception;

    TIPO_ENTIDAD get(int id) throws Exception;

    List<TIPO_ENTIDAD> getAll() throws Exception;

    int getCount(ArrayList<FilterBean> alFilter) throws Exception;

    int getPages(int pageSize, ArrayList<FilterBean> alFilter) throws Exception;

    List<TIPO_ENTIDAD> getPage(int pageSize, int pageNumber, ArrayList<FilterBean> alFilter, HashMap<String, String> hmOrder) throws Exception;

    ArrayList<String> getColumnsNames() throws Exception;
}
