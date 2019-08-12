package ua.storoman.model.service;

import org.apache.log4j.Logger;

import ua.storoman.model.entity.BusStop;
import ua.storoman.model.service.util.AbstractService;
import ua.storoman.model.service.util.FactoryService;
import ua.storoman.model.util.db.DBExeption;
import ua.storoman.model.util.db.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ua.storoman.model.util.Constants.*;

public class BusStopService extends AbstractService<BusStop> {

    final private static Logger logger = Logger.getLogger(BusStopService.class);
    private FactoryService factoryService =  FactoryService.getInstance();


    @Override
    protected String getCreateQuery() {
        return DBQuery.BUS_STOP_INSERT;
    }
    @Override
    protected String getSelectAllQuery() {
        return DBQuery.BUS_STOP_GET_ALL;
    }

    @Override
    protected String getSelectDeleteQuery() {
        return null;
    }

    @Override
    protected String getUpdateSQL() {
        return DBQuery.BUS_STOP_UPDATE;
    }


    @Override
    public BusStop getById(int id) throws DBExeption {
        BusStop busStop = super.getById(id);
        factoryService.putBusStopInMap(busStop);
        return busStop;
    }


    @Override
    public ArrayList<BusStop> parseResultSet(ResultSet rs) throws DBExeption {
        ArrayList<BusStop> busStops = new ArrayList<>();
        try {
            while (rs.next()) {
                BusStop busStop = new BusStop();
                busStop.setId(rs.getInt(BUS_STOP_ID));
                busStop.setName(rs.getString(BUS_STOP_NAME));
                busStop.setStreetName(rs.getString(BUS_STOP_STREET_NAME));
                busStop.setCovered(rs.getBoolean(BUS_STOP_IS_COVERED));
                factoryService.putBusStopInMap(busStop);
                busStops.add(busStop);
            }

        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
        return busStops;
    }

    @Override
    protected boolean isInDb(BusStop busStop) throws DBExeption {
            return getByFields(new String[]{BUS_STOP_NAME}, new Object[]{busStop.getName()}) != null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement preparedStatement, BusStop busStop) throws DBExeption {
        try {
            preparedStatement.setString(1, busStop.getName());
            preparedStatement.setString(2, busStop.getStreetName());
            preparedStatement.setBoolean(3, busStop.isCovered());

            logger.info("SQL create busStop: " + preparedStatement);
        } catch (SQLException e) {
            logger.info("SQL error create busStop: " + preparedStatement + "\n" + e.getMessage());
            throw new DBExeption("SQL error create busStop: " + preparedStatement + "\n" + e.getMessage());
        }
    }


    @Override
    protected void prepareStatementForUpdate(PreparedStatement preparedStatement, BusStop busStop) throws DBExeption {
        try {
            preparedStatement.setString(1, busStop.getName());
            preparedStatement.setString(2, busStop.getStreetName());
            preparedStatement.setBoolean(3, busStop.isCovered());
            preparedStatement.setInt(4, busStop.getId());

            logger.info("SQL create busStop: " + preparedStatement);
        } catch (SQLException e) {
            logger.info("SQL error create busStop: " + preparedStatement + "\n" + e.getMessage());
            throw new DBExeption("SQL error create busStop: " + preparedStatement + "\n" + e.getMessage());
        }
    }

    @Override
    protected void prepareStatementForDelete(PreparedStatement preparedStatement, BusStop busStop) throws DBExeption {
        try {
            preparedStatement.setInt(1, busStop.getId());
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
    }

}


