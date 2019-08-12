package ua.storoman.model.util.db;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ua.storoman.model.entity.BusStop;
import ua.storoman.model.entity.Rout;
import ua.storoman.model.entity.RoutStop;
import ua.storoman.model.service.BusStopService;
import ua.storoman.model.service.util.FactoryService;
import ua.storoman.model.service.RoutService;
import ua.storoman.model.service.RoutStopService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;

public class FillingDB {

    final private static Logger logger = Logger.getLogger(FillingDB.class);

    private ObjectMapper objectMapper = new ObjectMapper();
    private FactoryService factoryService = FactoryService.getInstance();

    public void getResponse() {
        try {

            HashSet<BusStop> busStops = new HashSet<>();
            HashSet<RoutStop> routStops = new HashSet<>();
            HashSet<Rout> routs = new HashSet<>();

//            URL url = new URL(BASE_URL + BASE_ID + "/"+ COUNT + "?" + API_KEY);
            URL url = new URL("https://apidata.mos.ru/v1/datasets/752/rows?$top=500&$skip=0&api_key=37a15f0256a4e8510bbfd0acdf164a06");


            logger.debug(url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);
            String output;
            String jsonS = "";
            while ((output = br.readLine()) != null) {
                jsonS = output;
            }
            conn.disconnect();

            if(jsonS != null || jsonS.isEmpty()) {
                logger.debug(jsonS);
                JsonNode rootNode = objectMapper.readTree(jsonS);
                for (JsonNode childe: rootNode) {
                    JsonNode CellNode = childe.get("Cells");
                    BusStop busStop = new BusStop(
                            CellNode.get("Name").toString().replace("\"",""),
                            CellNode.get("Street").toString().replace("\"",""),
                            CellNode.get("Pavilion").toString().equals("да")
                    );

                    busStops.add(busStop);

                    String[] routNames = CellNode.get("RouteNumbers").toString().replace("\"","").split("; ");
                    for (String name: routNames) {
                        Rout rout = new Rout(name);
                        routs.add(rout);
                        RoutStop routStop = new RoutStop(rout, busStop);
                        routStops.add(routStop);

                    }


                }

                logger.debug(busStops.size());
                logger.debug(routs.size());
                logger.debug(routStops.size());

                BusStopService busStopService = factoryService.getBusStopService();
                for (BusStop iStop : busStops) {
                    busStopService.add(iStop);
                }
                logger.debug("----------------- finish bus stops ----------------------");

                RoutService routService = factoryService.getRoutService();
                for (Rout iRout : routs) {
                    routService.add(iRout);
                }
                logger.debug("----------------- finish routs ----------------------");

                RoutStopService routStopService = factoryService.getRoutStopService();
                for (RoutStop iRoutStop : routStops) {
                   try {
                       routStopService.add(iRoutStop);
                   }catch (Exception e){
                       logger.debug("istop " + e);
                       logger.debug(e.getMessage());
                   }
                }
               logger.debug("----------------- finish routs stops ----------------------");


            }else{
                logger.debug("empty json");
            }

        } catch (Exception e) {
            logger.debug("Exception in NetClientGet:- " + e.getMessage());
        }
    }
}
