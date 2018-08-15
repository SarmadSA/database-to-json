import org.json.JSONObject;
import java.sql.ResultSet;

public class CardTechnicalInfoTable {
    private final String tableName = "card_technical_info";
    private final String[] tableColumns = {
            "id",
            "card_id",
            "cuda_cores",
            "base_clock",
            "boost_clock",
            "standard_memory_config",
            "memory_speed",
            "memory_interface_width",
            "memory_bandwidth"
    };
    private final DbConnect connection;

    public CardTechnicalInfoTable() {
        this.connection = new DbConnect();
    }

    public JSONObject getTechnicalInfo(String id) {
        JSONObject technicalInfo = new JSONObject();
        try {
            String query = "SELECT * FROM " + tableName + " WHERE card_id = " + id;
            ResultSet resultSet = this.connection.getStatement().executeQuery(query);

            // As long there is a new row do the following
            while (resultSet.next()) {
                for(String column: this.tableColumns){
                    technicalInfo.put(column, resultSet.getString(column));
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return technicalInfo;
    }
}