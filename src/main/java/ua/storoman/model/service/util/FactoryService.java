package ua.storoman.model.service.util;

import ua.storoman.model.entity.BusStop;
import ua.storoman.model.entity.Rout;
import ua.storoman.model.service.BusStopService;
import ua.storoman.model.service.RoutService;
import ua.storoman.model.service.RoutStopService;
import ua.storoman.model.util.db.FillingDB;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

public class FactoryService  implements Factory {

    private static volatile FactoryService instance;

    private HashMap<Integer, BusStop> busStopHashMap = new HashMap<>();
    private HashMap<Integer, Rout> routHashMap = new HashMap<>();

    private FactoryService(){};

    public static FactoryService getInstance(){
        if(instance == null){
            synchronized (FactoryService.class){
                if(instance == null) {
                    instance = new FactoryService();
                }
            }
        }
        return instance;
    }

    @Override
    public BusStopService getBusStopService() {
        return new BusStopService();
    }

    @Override
    public RoutService getRoutService() {
        return new RoutService();
    }
    @Override
    public RoutStopService getRoutStopService() {
        return new RoutStopService();
    }

    @Override
    public FillingDB getFillingDB() {
        return new FillingDB();
    }

    public BusStop getBusStopFromMap(@NotNull int id){
        if (busStopHashMap.containsKey(id)){
            return busStopHashMap.get(id);
        };
        return null;
    }

    public void putBusStopInMap(BusStop busStop){
        if(!busStopHashMap.containsKey(busStop.getId())){
          busStopHashMap.put(busStop.getId(), busStop);
        }
    }

    public Rout getRoutFromMap(@NotNull int id){
        if (routHashMap.containsKey(id)){
            return routHashMap.get(id);
        };
        return null;
    }

    public void putRoutInMap(Rout rout){
        if(!routHashMap.containsKey(rout.getId())){
            routHashMap.put(rout.getId(), rout);
        }
    }
}

