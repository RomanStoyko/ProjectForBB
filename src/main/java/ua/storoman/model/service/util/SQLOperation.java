package ua.storoman.model.service.util;


import ua.storoman.model.util.db.DBExeption;

import java.util.List;

public interface SQLOperation<T> {


    void add(T t) throws DBExeption;

    List<T> getAll()throws DBExeption;

    T getById(int id)throws DBExeption;

    List<T> getByFields(String[] fieldName, Object[] condition)throws DBExeption;

    void update(T t)throws DBExeption;

    void remove(T t)throws DBExeption;
}
