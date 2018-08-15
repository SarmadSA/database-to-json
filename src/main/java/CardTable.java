import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class CardTable {
    private final String tableName = "cards";
    private final String[] tableColumns = {"id", "title", "release_data", "price", "amazon_link", "image"};
    private DbConnect connection;
    //private HashMap<String, JSONObject> list;
    private GamesTable games;
    private JSONArray list;

    public CardTable() {
        this.connection = new DbConnect();
        //this.list = new HashMap<>();
        this.games = new GamesTable();
        this.list = new JSONArray();
    }

    public void Initialize() {
        this.importData();
        this.addGames();
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

    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("cards", this.list);
        return data;
    }
}    