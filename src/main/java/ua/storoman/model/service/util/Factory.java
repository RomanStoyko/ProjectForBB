package ua.storoman.model.service.util;


import ua.storoman.model.service.BusStopService;
import ua.storoman.model.service.RoutService;
import ua.storoman.model.service.RoutStopService;
import ua.storoman.model.util.db.FillingDB;

public interface Factory {

        public abstract FillingDB getFillingDB();

        public abstract BusStopService getBusStopService();

        public abstract RoutService getRoutService() ;

        public abstract RoutStopService getRoutStopService();


    }

