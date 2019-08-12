package ua.storoman.model.util;

public interface Constants{
    String BUS_STOP_TABLE = "busstops";
    String BUS_STOP_ID = "ID";
    String BUS_STOP_NAME = "NAME";
    String BUS_STOP_STREET_NAME = "STREETNAME";
    String BUS_STOP_IS_COVERED = "ISCOVERED";

    //----------------------------------

    String  ROUT_TABLE = "routs";
    String ROUT_ID= "ID";
   String ROUT_NAME= "NAME";

    //----------------------------------
    String ROUT_STOP_TABLE = "routstops";
    String ROUT_STOP_ID = "ID";
    String ROUT_STOP_BUS_STOP_ID = "BUSSTOP_ID";
    String ROUT_STOP_ROUT_ID = "ROUT_ID";

    String API_KEY = "api_key=37a15f0256a4e8510bbfd0acdf164a06";
    String BASE_URL = "https://apidata.mos.ru/v1/datasets/";
    String BASE_ID = "752";
    String COUNT = "count";
    String TOP = "$top";
    String SKIP = "$skip";
}
