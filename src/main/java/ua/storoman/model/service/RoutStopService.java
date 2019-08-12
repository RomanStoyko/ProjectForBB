package ua.storoman.model.service;

import org.apache.log4j.Logger;
import ua.storoman.model.entity.BusStop;
import ua.storoman.model.entity.Rout;
import ua.storoman.model.entity.RoutStop;
import ua.storoman.model.service.util.AbstractService;
import ua.storoman.model.service.util.FactoryService;
import ua.storoman.model.util.db.DBExeption;
import ua.storoman.model.util.db.DBQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.storoman.model.util.Constants.*;

public class RoutStopService  extends AbstractService<RoutStop> {
    final private static Logger logger = Logger.getLogger(RoutStopService.class);
    private FactoryService factoryService =  FactoryService.getInstance();

    @Override
    protected String getCreateQuery() {
        return DBQuery.ROUT_STOPS_INSERT;
    }

    @Override
    protected String getSelectAllQuery() {
        return DBQuery.ROUT_STOPS_GET_ALL;
    }

    @Override
    protected String getSelectDeleteQuery() {
        return DBQuery.ROUT_STOPS_DELETE;
    }

    @Override
    protected String getUpdateSQL() {
        return DBQuery.ROUT_STOPS_UPDATE;
    }

    @Override
    public ArrayList<RoutStop> parseResultSet(ResultSet rs) throws DBExeption {
        ArrayList<RoutStop> routStops = new ArrayList<>();
        try {

            while (rs.next()) {
                routStops.add(parseData(rs));
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new DBExeption(e.getMessage());
        }
        return routStops;
    }

    @Override
    protected boolean isInDb(RoutStop routStop) throws DBExeption {
        int routId = routStop.getRout().getId();
        if (routId == 0) {
            List<Rout> rout = factoryService.getRoutService().getByFields(new String[]{ROUT_NAME}, new Object[]{routStop.getRout().getName()});
            if (rout != null && rout.size()>0) {
                routId = rout.get(0).getId();
            }
        }
        int busStopId = routStop.getBusStop().getId();
        if (busStopId == 0) {
            List<BusStop> busStop = factoryService.getBusStopService().getByFields(new String[]{BUS_STOP_NAME}, new Object[]{
                    routStop.getBusStop().getName()
            });
            if (busStop != null  && busStop.size()>0) {
                busStopId = busStop.get(0).getId();
            }
        }
        List<RoutStop> routStopDB = factoryService.getRoutStopService().getByFields(new String[]{ROUT_STOP_BUS_STOP_ID, ROUT_STOP_ROUT_ID}, new Object[]{busStopId, routId});
        logger.info(routStopDB);
        return  routStopDB.isEmpty();
    }

    public List<BusStop> getBusStopsByRoutId(int routId) throws DBExeption {
        List<RoutStop> routStops = factoryService.getRoutStopService().getByFields(new String[]{ROUT_STOP_ROUT_ID}, new Object[]{routId});
        List<BusStop> busStops = new ArrayList<>();
        for (RoutStop routStop:routStops) {
            busStops.add(routStop.getBusStop());
        }
        return  busStops;
    }


    @Override
    protected void prepareStatementForInsert(PreparedStatement preparedStatement, RoutStop routStop) throws DBExeption {

        try {

            int routId = routStop.getRout().getId();
            if (routId == 0) {
                List<Rout> rout = factoryService.getRoutService().getByFields(new String[]{ROUT_NAME}, new Object[]{routStop.getRout().getName()});
                if (rout != null && rout.size()>0) {
                    routId = rout.get(0).getId();
                }
            }
            int busStopId = routStop.getBusStop().getId();
            if (busStopId == 0) {
                List<BusStop> busStop = factoryService.getBusStopService().getByFields(new String[]{BUS_STOP_NAME}, new Object[]{
                        routStop.getBusStop().getName()
                });
                if (busStop != null  && busStop.size()>0) {
                    busStopId = busStop.get(0).getId();
                }
            }

            preparedStatement.setInt(1, routId);
            preparedStatement.setInt(2, busStopId);

            logger.info("SQL create RoutStop: " + routStop);
        } catch (SQLException e) {
            logger.info("SQL error create RoutStop: " + routStop + "\n" + e.getMessage());
            throw new DBExeption("SQL error create RoutStop: " + routStop + "\n" + e.getMessage());
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement preparedStatement, RoutStop routStop) throws DBExeption {
        try {
            preparedStatement.setInt(1, routStop.getRout().getId());
            preparedStatement.setInt(2, routStop.getBusStop().getId());
            preparedStatement.setInt(3, routStop.getId());

            logger.info("SQL update RoutStop: " + routStop);
        } catch (SQLException e) {
            logger.info("SQL error update RoutStop: " + routStop + "\n" + e.getMessage());
            throw new DBExeption("SQL error update RoutStop: " + routStop + "\n" + e.getMessage());
        }
    }


    @Override
    protected void prepareStatementForDelete(PreparedStatement preparedStatement, RoutStop routStop) throws DBExeption {
        try {
            preparedStatement.setInt(1, routStop.getId());
        } catch (Exception e) {
            throw new DBExeption(e.getMessage());
        }
    }

    private RoutStop parseData(ResultSet rs) throws SQLException, DBExeption{
        BusStopService busStopService = factoryService.getBusStopService();
        RoutService routService = factoryService.getRoutService();
        BusStop busStop = null;
        Rout rout = null;
        int busStopId  = 0;
        int routId = 0;

        RoutStop routStop = new RoutStop();
        routStop.setId(rs.getInt(ROUT_STOP_ID));
        busStopId = rs.getInt(ROUT_STOP_BUS_STOP_ID);

        busStop = factoryService.getBusStopFromMap(busStopId);
        if(busStop == null) {
            routStop.setBusStop(busStopService.getById(busStopId));
        }else{
            factoryService.putBusStopInMap(busStop);
            routStop.setBusStop(busStop);
        }

        routId = rs.getInt(ROUT_STOP_ROUT_ID);
        rout = factoryService.getRoutFromMap(routId);
        if(rout == null) {
            routStop.setRout(routService.getById(routId));
        }else{
            factoryService.putRoutInMap(rout);
            routStop.setRout(rout);
        }

        return routStop;
    }
}
