import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class GamesTable {
    private final String tableName = "games";
    private final String[] tableColumns = {"id", "card_id", "title", "resolution", "settings", "fps", "image"};
    private final DbConnect connection;

    public GamesTable() {
        this.connection = new DbConnect();
    }

    /**
     * Returns a JSON array of game with the given ID
     *
     * @param id the id of the games to return
     * @return JSON array of game with the given ID
     */
    public JSONArray getGames(String id) {
        JSONArray gamesList = new JSONArray();
        try {
            String query = "SELECT * FROM " + tableName + " WHERE card_id = " + id;
            ResultSet resultSet = this.connection.getStatement().executeQuery(query);

            while (resultSet.next()) {
                JSONObject game = new JSONObject();

                for(String column: this.tableColumns){
                    game.put(column, resultSet.getString(column));
                }

                gamesList.put(game);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return gamesList;
    }
}

