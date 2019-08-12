package ua.storoman.model.service;

import org.apache.log4j.Logger;

import ua.storoman.model.entity.Rout;
import ua.storoman.model.service.util.AbstractService;
import ua.storoman.model.service.util.FactoryService;
import ua.storoman.model.util.db.DBExeption;
import ua.storoman.model.util.db.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ua.storoman.model.util.Constants.*;

public class RoutService extends AbstractService<Rout> {
    final private static Logger logger = Logger.getLogger(RoutService.class);
    private FactoryService factoryService =  FactoryService.getInstance();

    @Override
    protected String getCreateQuery() {
        return DBQuery.ROUT_INSERT;
    }
    @Override
    protected String getSelectAllQuery() {
        return DBQuery.ROUT_GET_ALL;
    }

    @Override
    protected String getSelectDeleteQuery() {
        return DBQuery.ROUT_DELETE;
    }

    @Override
    protected String getUpdateSQL() {
        return DBQuery.ROUT_UPDATE;
    }

    @Override
    public Rout getById(int id) throws DBExeption {
        Rout rout = super.getById(id);
        factoryService.putRoutInMap(rout);
        return rout;
    }

    @Override
    public ArrayList<Rout> parseResultSet(ResultSet rs) throws DBExeption {
        ArrayList<Rout> routs = new ArrayList<>();
        try {
            while (rs.next()) {
                Rout rout = new Rout();
                rout.setId(rs.getInt(ROUT_ID));
                rout.setName(rs.getString(ROUT_NAME));

                routs.add(rout);
            }

        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
        return routs;
    }

    @Override
    protected boolean isInDb(Rout rout) throws DBExeption {
        return getByFields(new String[]{ROUT_NAME}, new Object[]{rout.getName()}) != null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement preparedStatement, Rout rout) throws DBExeption {
        try {
            preparedStatement.setString(1, rout.getName());

            logger.info("SQL create rout: " + rout);
        } catch (SQLException e) {
            logger.info("SQL error create rout: " + rout + "\n" + e.getMessage());
            throw new DBExeption("SQL error create rout: " + rout + "\n" + e.getMessage());
        }
    }


    @Override
    protected void prepareStatementForUpdate(PreparedStatement preparedStatement, Rout rout) throws DBExeption {
        try {
            preparedStatement.setString(1, rout.getName());
            preparedStatement.setInt(2, rout.getId());

            logger.info("SQL update rout: " + rout);
        } catch (SQLException e) {
            logger.info("SQL error update rout: " + rout + "\n" + e.getMessage());
            throw new DBExeption("SQL error update rout: " + rout + "\n" + e.getMessage());
        }
    }


    @Override
    protected void prepareStatementForDelete(PreparedStatement preparedStatement, Rout rout) throws DBExeption {
        try {
            preparedStatement.setInt(1, rout.getId());
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
    }
}
