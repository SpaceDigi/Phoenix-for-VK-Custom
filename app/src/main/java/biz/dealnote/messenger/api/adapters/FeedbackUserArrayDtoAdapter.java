package biz.dealnote.messenger.api.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import biz.dealnote.messenger.api.model.feedback.UserArray;

/**
 * Created by ruslan.kolbasa on 28.12.2016.
 * phoenix
 */
public class FeedbackUserArrayDtoAdapter extends AbsAdapter implements JsonDeserializer<UserArray> {

    @Override
    public UserArray deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();
        UserArray dto = new UserArray();

        dto.count = optInt(root, "count");

        if(root.has("items")){
            JsonArray array = root.getAsJsonArray("items");
            dto.ids = new int[array.size()];

            for(int i = 0; i < array.size(); i++){
                dto.ids[i] = array.get(i).getAsJsonObject().get("from_id").getAsInt();
            }
        } else {
            dto.ids = new int[0];
        }

        return dto;
    }
}
