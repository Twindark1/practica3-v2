import org.h2.tools.Server;

import java.sql.SQLException;

import Ruta.Web;

/**
 * Created by cesarjose on 6/05/17.
 */

public class main {

    public static void main(String[] args) {

        try {
            Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Web.StartProject();
    }

}
