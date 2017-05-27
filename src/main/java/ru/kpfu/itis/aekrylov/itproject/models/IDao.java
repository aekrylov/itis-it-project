package ru.kpfu.itis.aekrylov.itproject.models;

import ru.kpfu.itis.aekrylov.itproject.entities.Entity;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;
import ru.kpfu.itis.aekrylov.itproject.models.misc.SimpleFilter;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:30 PM
 */
public interface IDao<T extends Entity> {

    List<T> findAll() throws SQLException;
    List<T> findAll(SimpleFilter filter) throws SQLException;
    T findOne(int id) throws SQLException, NotFoundException;
    Map<String, String> getMap(int id) throws SQLException;

    int count() throws SQLException;
    int count(SimpleFilter filter) throws SQLException;

    List<Object[]> getTable() throws SQLException;
    List<Object[]> getTable(SimpleFilter filter) throws SQLException;

    boolean create(Map<String, String> map) throws SQLException;
    boolean create(T instance) throws SQLException;

    boolean update(T instance) throws SQLException;
    boolean update(ParameterMap map) throws SQLException;

    boolean delete(T instance) throws SQLException;
    boolean delete(int id) throws SQLException;
}
