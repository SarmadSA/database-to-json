import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.smartcardio.Card;

public class CardTable {
    private final String tableName = "cards";
    private final String[] tableColumns = {"id", "title", "short_title", "release_date", "price", "amazon_link", "image"};
    private DbConnect connection;
    //private HashMap<String, JSONObject> list;
    private GamesTable games;
    private CardTechnicalInfoTable technicalInfo;
    private JSONArray list;

    public CardTable() {
        this.connection = new DbConnect();
        //this.list = new HashMap<>();
        this.games = new GamesTable();
        this.technicalInfo = new CardTechnicalInfoTable();
        this.list = new JSONArray();
    }

    public void Initialize() {
        this.importData();
        this.addGames();
        this.addTechnicalInfo();
    }

    private void importData() {
        try {
            String query = "SELECT * FROM " + tableName;
            ResultSet resultSet = this.connection.getStatement().executeQuery(query);

            while (resultSet.next()) {
                JSONObject card = new JSONObject();

                for(String column: this.tableColumns){
                    card.put(column, resultSet.getString(column));
                }

                this.list.put(card);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void addGames() {
        for (Object obj : this.list) {
            JSONObject object = (JSONObject) obj;
            String id = (String) object.get("id");
            object.put("games", games.getGames(id));
        }
    }

    private void addTechnicalInfo(){
        for (Object obj : this.list) {
            JSONObject object = (JSONObject) obj;
            String id = (String) object.get("id");
            object.put("technical_info", technicalInfo.getTechnicalInfo(id));
        }
    }

    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("cards", this.list);
        return data;
    }
}    
