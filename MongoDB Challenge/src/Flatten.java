import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import org.json.JSONObject;



public class Flatten
{
    public static JSONObject flatten(JSONObject input) throws JSONException {
         JSONObject result = new JSONObject();
        @SuppressWarnings("unchecked")
		Iterator<String> keys = (Iterator<String>)input.keys();
        while (keys.hasNext()) 
        {
            String k = keys.next();
            if (input.get(k) instanceof JSONObject) 
            {
                JSONObject subObject = flatten(input.getJSONObject(k));
                @SuppressWarnings("unchecked")
				Iterator<String> subKeys = (Iterator<String>)subObject.keys();
                while (subKeys.hasNext()) 
                {
                    String subk = subKeys.next();
                    result.put(k + "." + subk, subObject.get(subk));
                }
            }
            else 
            {
                result.put(k, input.get(k));
            }
        }
        return result;
    }
    
    public static void main(final String[] args) throws JSONException {
        String file = args[0];
        String json;
        try {
            json = new String(Files.readAllBytes(Paths.get(file, new String[0])));
        }
        catch (Exception e) {
            System.out.println("Unable to read the file : " + e.getMessage() + "!");
            return;
        }
        try {
             JSONObject output = new JSONObject(json);
        }
        catch (JSONException e2) {
            System.out.println("This file is not in valid JSON format: " + e2.getMessage());
            return;
        }
        JSONObject output = flatten(new JSONObject(json));
        System.out.println(output);
    }
}