package colintheshots.homelesshomescreen.model.serializers;

import android.util.Log;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by colin on 6/30/2014.
 *
 * Custom type adapter specific for handling our Category Multimap. This does NOT work in the general case.
 */
public class MultimapSerializer implements JsonSerializer<ListMultimap>,
            JsonDeserializer<ListMultimap> {

    @Override
    public JsonElement serialize(ListMultimap src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.asMap());
    }

    @Override
    public ListMultimap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ListMultimap<String, Object> result = LinkedListMultimap.create();
        JsonObject root = json.getAsJsonObject();
        final Set<Map.Entry<String, JsonElement>> entries = root.entrySet();
        for(Map.Entry<String,JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonArray topValues = entry.getValue().getAsJsonArray();
            Set<Object> currentValues = new HashSet<Object>(topValues.size());
            for(JsonElement topValue : topValues) {
                if (topValue.isJsonArray()) {
                    Log.e("TEST", "Array contains array.");
                } else if (topValue.isJsonNull()) {
                    Log.e("TEST", "Array contains null.");
                } else if (topValue.isJsonPrimitive()) {
                    Log.e("TEST", "Array contains top-level primitives.");
                } else if (topValue.isJsonObject()) {
                    JsonObject obj = topValue.getAsJsonObject();
                    Category myCategory = new Category();
                    myCategory.setName(obj.get("name").getAsString());
                    myCategory.setImage_url(obj.get("image_url").getAsString());
                    myCategory.setUrl(obj.get("url").getAsString());
                    currentValues.add(myCategory);
                }
            }
            result.putAll(key, currentValues);
        }
        return result;
    }
}