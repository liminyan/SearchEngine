import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetQuery {

    private static String filename ;
    private static GetQuery cur;
    private static ArrayList<SQuery> qList = new ArrayList<SQuery>();
    public static GetQuery getInstance(){
        if (cur == null){
            cur = new GetQuery();
        }

        return cur;
    }

    public static void setFilename(String filename) throws IOException{
        GetQuery.filename = filename;
        loadqList();
    }

    private static SQuery getQuery(String jason) {
        SQuery temp = new SQuery();

        String id;
        String intent_seg;
        String intent;
        String query;
        JSONObject jsonObject = JSONObject.fromObject(jason);

        id = jsonObject.getString("qid");
        intent_seg = jsonObject.getString("intent_seg");
        intent = jsonObject.getString("intent");
        query = jsonObject.getString("query");

        temp.setId(id);
        temp.setIntent(intent);
        temp.setIntent_seg(intent_seg);
        temp.setQuery(query);

        return temp;
    }

    public static void loadqList() throws IOException {

        File file = new File(filename);
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;

        try {

            while ((tempString = reader.readLine()) != null) {

               SQuery a = getQuery(tempString);
               qList.add(a);
            }
        }catch (JSONException e) {
            System.out.println(e);
        }

        reader.close();

    }

    public static ArrayList<SQuery> getqList() {
        return qList;
    }
}
