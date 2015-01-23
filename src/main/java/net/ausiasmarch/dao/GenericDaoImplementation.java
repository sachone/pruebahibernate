/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ausiasmarch.helper.FilterBean;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author rafa
 */
public class GenericDaoImplementation<TYPE extends Serializable> implements GenericDao<TYPE> {

    private Session sesion;
    private Transaction tx;

    @Override
    public int create(TYPE oBean) throws Exception {
        int id = 0;
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = (Transaction) sesion.beginTransaction();
            id = (int) sesion.save(oBean);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            //Logger.getLogger(GenericDaoImplementation.class.getName()).log(Level.SEVERE, null, e);
            throw new HibernateException("GenericDaoImplementation.create: Error: ", e);
        } finally {
            sesion.close();
        }
        return id;
    }

    @Override
    public void set(TYPE oBean) throws Exception {
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = (Transaction) sesion.beginTransaction();
            sesion.update(oBean);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            //Logger.getLogger(GenericDaoImplementation.class.getName()).log(Level.SEVERE, null, e);
            throw new HibernateException("GenericDaoImplementation.set: Error: ", e);
        } finally {
            sesion.close();
        }
    }

    @Override
    public void remove(TYPE oBean) throws Exception {
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            tx = (Transaction) sesion.beginTransaction();
            sesion.delete(oBean);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            //Logger.getLogger(GenericDaoImplementation.class.getName()).log(Level.SEVERE, null, e);
            throw new HibernateException("GenericDaoImplementation.remove: Error: ", e);
        } finally {
            sesion.close();
        }
    }

    @Override
    public TYPE get(int id) throws Exception { //public TYPE read(TYPE entity) {
        TYPE oBean;
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            oBean = (TYPE) sesion.get(tipo, id);
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.get: Error: ", e);
        } finally {
            sesion.close();
        }
        return oBean;
    }

    @Override
    public List<TYPE> getAll() throws Exception {
        List<TYPE> loBean;
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = sesion.createCriteria(tipo);
            loBean = (List<TYPE>) criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.getAll: Error: ", e);
        } finally {
            sesion.close();
        }
        return loBean;
    }

    @Override
    public int getCount(ArrayList<FilterBean> alFilter) throws Exception {
        int cantidad;
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Criteria oCriteria = sesion.createCriteria(tipo);

            if (alFilter != null) {
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBean oFilterBean = (FilterBean) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notlike":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.not(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%")));
                            break;
                        case "equals":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.eq(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notequalto":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.not(Restrictions.eq(oFilterBean.getFilter(), oFilterBean.getFilterValue())));
                            break;
                        case "less":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.lt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "lessorequal":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.le(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greater":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.gt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greaterorequal":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.ge(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                    }

                }

            }

            oCriteria.setProjection(Projections.rowCount());
            cantidad = (int) (long) oCriteria.list().get(0);
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.getCount: Error: ", e);
        } finally {
            sesion.close();
        }
        return cantidad;
    }

    @Override
    public int getPages(int intRegsPerPag, ArrayList<FilterBean> alFilter) throws Exception {
        int cantidad, pages;
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            Criteria oCriteria = sesion.createCriteria(tipo);

            if (alFilter != null) {
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBean oFilterBean = (FilterBean) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notlike":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.not(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%")));
                            break;
                        case "equals":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.eq(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notequalto":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.not(Restrictions.eq(oFilterBean.getFilter(), oFilterBean.getFilterValue())));
                            break;
                        case "less":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.lt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "lessorequal":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.le(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greater":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.gt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greaterorequal":
                            oCriteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.ge(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                    }

                }

            }

            cantidad = (int) (long) oCriteria.setProjection(Projections.rowCount()).uniqueResult();
            pages = (int) Math.ceil(cantidad / intRegsPerPag);
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.getPages: Error: ", e);
        } finally {
            sesion.close();
        }

        return pages;
    }

    @Override
    public List<TYPE> getPage(int pageSize, int pageNumber, ArrayList<FilterBean> alFilter, HashMap<String, String> hmOrder) throws Exception {
        List<TYPE> loBean;
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        sesion = HibernateUtil.getSessionFactory().openSession();
        try {
            Criteria criteria = sesion.createCriteria(tipo);

            if (alFilter != null) {
                Iterator iterator = alFilter.iterator();
                while (iterator.hasNext()) {
                    FilterBean oFilterBean = (FilterBean) iterator.next();
                    switch (oFilterBean.getFilterOperator()) {
                        case "like":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notlike":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " NOT LIKE '%" + oFilterBean.getFilterValue() + "%'"));
                            //criteria.add(Restrictions.not(Restrictions.like(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%")));
                            break;
                        case "equals":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " = '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.eq(oFilterBean.getFilter(), "%" + oFilterBean.getFilterValue() + "%"));
                            break;
                        case "notequalto":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <> '" + oFilterBean.getFilterValue() + "'"));
                            //criteria.add(Restrictions.not(Restrictions.eq(oFilterBean.getFilter(), oFilterBean.getFilterValue())));
                            break;
                        case "less":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " < " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.lt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "lessorequal":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " <= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.le(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greater":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " > " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.gt(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                        case "greaterorequal":
                            criteria.add(Restrictions.sqlRestriction(oFilterBean.getFilter() + " >= " + oFilterBean.getFilterValue()));
                            //criteria.add(Restrictions.ge(oFilterBean.getFilter(), oFilterBean.getFilterValue()));
                            break;
                    }
                }
            }
            if (hmOrder != null) {
                for (Map.Entry oPar : hmOrder.entrySet()) {
                    if ("asc".equalsIgnoreCase((String) oPar.getValue())) {
                        criteria.addOrder(Order.asc((String) oPar.getKey())); // (Restrictions.sqlRestriction(
                    } else {
                        criteria.addOrder(Order.desc((String) oPar.getKey()));
                    }
                }
            }
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
            loBean = (List<TYPE>) criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.getPage: Error: ", e);
        } finally {
            sesion.close();
        }
        return loBean;
    }

    @Override
    public ArrayList<String> getColumnsNames() throws Exception {
        ArrayList<String> vector = new ArrayList<>();
        Class<TYPE> tipo = (Class<TYPE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        //---------------------------------------------------------------------
//        //Dejo comentado el esquema para obtener los nombres de los campos de la tabla via introspección en el mapeo de hibernate
//        sesion = HibernateUtil.getSessionFactory().openSession();
//        Configuration oConfiguration = HibernateUtil.getConfiguration();
//        PersistentClass oPersistentClass = oConfiguration.getClassMapping(tipo.getName());
//        for (Method method : tipo.getMethods()) {
//            Property oProperty = oPersistentClass.getProperty(method.getName());
//            Iterator<Column> oIterator = oProperty.getColumnIterator();
//            Column oColumn = oIterator.next();
//            vector.add(oColumn.getName());
//        }

        //---------------------------------------------------------------------
        //obtener los nombre de los campos via consulta SQL
        //problema: error: cannot open connection
        try {
            sesion = HibernateUtil.getSessionFactory().openSession();
            SQLQuery query = sesion.createSQLQuery("DESCRIBE " + tipo.getSimpleName());
            List result = query.list();
            Iterator<Object[]> oIterador = result.listIterator();
            while (oIterador.hasNext()) {
                Object[] aCampos = oIterador.next();
                vector.add((String) aCampos[0]);
            }
        } catch (HibernateException e) {
            throw new HibernateException("GenericDaoImplementation.getColumnsNames: Error: ", e);
        } finally {
            try {
                sesion.close();
            } catch (Exception e) {
                throw new Exception("GenericDaoImplementation.getColumnsNames: Error: ", e);
            }
        }
        return vector;

    }

}
