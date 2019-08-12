package ua.storoman.model.util.db;

import static ua.storoman.model.util.Constants.*;

public interface DBQuery {

    String BUS_STOP_INSERT = "INSERT INTO "+ BUS_STOP_TABLE +"(" + BUS_STOP_NAME + ","+ BUS_STOP_STREET_NAME + ","+ BUS_STOP_IS_COVERED +") VALUES(?,?,?)";
    String BUS_STOP_UPDATE = "UPDATE "+ BUS_STOP_TABLE +" SET " + BUS_STOP_NAME + " = ? " + BUS_STOP_STREET_NAME + " = ? " + BUS_STOP_IS_COVERED + " = ? WHERE ID = ?";
    String BUS_STOP_DELETE = "DELETE FROM "+ BUS_STOP_TABLE +" WHERE ID = ?";
    String BUS_STOP_GET_ALL =   "SELECT  * FROM "+ BUS_STOP_TABLE;


    String ROUT_INSERT = "INSERT INTO "+ROUT_TABLE+"(" + ROUT_NAME + ") VALUES(?)";
    String ROUT_UPDATE = "UPDATE "+ROUT_TABLE+" SET " + ROUT_NAME + " = ? WHERE ID = ?";
    String ROUT_DELETE = "DELETE FROM "+ROUT_TABLE+" WHERE ID = ?";
    String ROUT_GET_ALL =   "SELECT  * FROM "+ ROUT_TABLE;

    String ROUT_STOPS_INSERT = "INSERT INTO "+ ROUT_STOP_TABLE +"(" + ROUT_STOP_ROUT_ID + ","+ ROUT_STOP_BUS_STOP_ID  +") VALUES(?,?)";
    String ROUT_STOPS_UPDATE = "UPDATE "+ ROUT_STOP_TABLE +" SET " + ROUT_STOP_ROUT_ID + " = ? " + ROUT_STOP_BUS_STOP_ID + " = ?  WHERE ID = ?";
    String ROUT_STOPS_DELETE = "DELETE FROM "+ ROUT_STOP_TABLE +" WHERE ID = ?";
    String ROUT_STOPS_GET_ALL =   "SELECT  * FROM "+ ROUT_STOP_TABLE;



}
