package ua.storoman.model.service.util;


import org.apache.log4j.Logger;
import ua.storoman.model.util.db.DBExeption;
import ua.storoman.model.util.db.connection.ConnectionPool;

import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService<T> implements SQLOperation<T> {
    final private static Logger logger = Logger.getLogger(AbstractService.class);
    protected Connection connection;

    protected abstract String getCreateQuery();

    protected abstract void prepareStatementForInsert(PreparedStatement preparedStatement, T object) throws DBExeption;

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectDeleteQuery();

    protected abstract void prepareStatementForDelete(PreparedStatement preparedStatement, T t) throws DBExeption;

    protected abstract ArrayList<T> parseResultSet(ResultSet rs) throws DBExeption;

    protected abstract String getUpdateSQL();

    protected abstract void prepareStatementForUpdate(PreparedStatement preparedStatement, T t) throws DBExeption;

    protected boolean isInDb(T object) throws DBExeption {
        return false;
    }

    @Override
    public void add(T object) throws DBExeption {

        boolean isIn = isInDb(object);
        if (isIn == true) {
            logger.info("It is in DB:" + object.toString());
            return;
        }

        try {
            checkConnection();
            String sql = getCreateQuery();
            PreparedStatement statement = connection.prepareStatement(sql);
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DBExeption("On persist modify more then 1 record: " + count);
            }
            logger.info("Created new :" + object);
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }
    }


    @Override
    public List<T> getAll() throws DBExeption {
        String sql = getSelectAllQuery();

        ArrayList<T> tList = new ArrayList<>();
        try {
            checkConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            tList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }
        return tList;
    }

    @Override
    public T getById(int id) throws DBExeption {
        String sql = getSelectAllQuery() + " WHERE ID = ?";

        ArrayList<T> tList;
        try {
            checkConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            tList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }
        if (tList.iterator().hasNext()) {
            return tList.iterator().next();
        }
        return null;
    }

    @Override
    public List<T>  getByFields(@NotNull String[] fieldName, @NotNull Object[] condition) throws DBExeption {
        if (fieldName == null || fieldName.length == 0 ||
                condition == null || condition.length == 0) {
            return null;
        }

        StringBuilder sql = new StringBuilder(getSelectAllQuery() + " WHERE ");
        for (int i = 0; i < fieldName.length; i++) {
            sql.append(fieldName[i]).append(" = ? ").append(i < fieldName.length - 1 ? " AND " : "");
        }

        ArrayList<T> tList;
        try {
            checkConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < condition.length; i++) {
                preparedStatement.setObject(i + 1, condition[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();
            tList = parseResultSet(rs);
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }
        return tList;
    }

    @Override
    public void update(T t) throws DBExeption {
        String sql = getUpdateSQL();


        try {
            checkConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            prepareStatementForUpdate(preparedStatement, t);
            int count = preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }

    }


    @Override
    public void remove(T t) throws DBExeption {
        String sql = getSelectDeleteQuery();

        try {

            checkConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            prepareStatementForDelete(preparedStatement, t);
            int count = preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        } finally {
            closeConnection();
        }
    }


    public void closeConnection() throws DBExeption {
        try {
            if (connection != null && !connection.isClosed()) {

                ConnectionPool.closeConnection(connection);
                setConnection(null);

            }
        } catch (Exception e) {
            throw new DBExeption("Close con from abs:" + e.getMessage());
        }
    }

    public void checkConnection() throws DBExeption {
        try {
            if (connection == null || connection.isClosed()) {
                connection = getConnection();
                setConnection(connection);
            }
        }catch (Exception e) {
            throw new DBExeption("Check con from abs:" + e.getMessage());
        }
    }

    public Connection getConnection() throws DBExeption {
        try {
            return ConnectionPool.getConnection();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBExeption(e.getMessage());
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public void setTransactionLevel(int level) throws DBExeption {
        checkConnection();
        try {
            if (connection.getMetaData().supportsTransactionIsolationLevel(level)) {
                connection.setTransactionIsolation(level);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DBExeption(e.getMessage());
        }
    }

    public void setAutoCommit(boolean autoCommit) throws DBExeption {
        checkConnection();
        try {
            connection.setAutoCommit(autoCommit);
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
    }

    public void commit() throws DBExeption {
        checkConnection();
        try {
            connection.commit();
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }

    }

    public void rollback() throws DBExeption {
        checkConnection();
        try {
            connection.rollback();
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
    }
}
