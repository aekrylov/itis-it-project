package ru.kpfu.itis.group501.krylov.db1_it_project.models;

import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Entity;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:30 PM
 */
public interface IDao<T extends Entity> {

    List<T> get() throws SQLException;
    List<T> get(SimpleFilter filter) throws SQLException;
    T get(int id) throws SQLException, NotFoundException;
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
