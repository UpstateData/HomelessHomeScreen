package colintheshots.homelesshomescreen;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import colintheshots.homelesshomescreen.model.serializers.Category;
import colintheshots.homelesshomescreen.model.serializers.MultimapSerializer;

/**
 * Created by colin on 6/30/2014.
 */
public class SerializeJson {

    public static void main (String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Multimap.class, new MultimapSerializer()).create();
        Multimap<String, Category> multiMap = LinkedListMultimap.create();

        Category ssBenefits = new Category();
        ssBenefits.setName("Check Social Security Application");
        ssBenefits.setImage_url("http://southjerseylawfirm.com/blog/wp-content/uploads/2012/05/social-security-logo.png");
        ssBenefits.setUrl("https://secure.ssa.gov/apps6z/IAPS/applicationStatus");
        multiMap.put("Benefits", ssBenefits);

        Category snapBenefits = new Category();
        snapBenefits.setName("Find If You Qualify For SNAP");
        snapBenefits.setImage_url("http://www.ct.gov/dss/lib/dss/images/snap.gif");
        snapBenefits.setUrl("http://mn.bridgetobenefits.org/ScreeningTool");
        multiMap.put("Benefits", snapBenefits);

        Category englishEducation = new Category();
        englishEducation.setName("Call for English Classes");
        englishEducation.setImage_url("https://openclipart.org/image/300px/svg_to_png/185269/phone-icon.png");
        englishEducation.setUrl("18002221990");
        multiMap.put("Education", englishEducation);

        Category gedEducation = new Category();
        gedEducation.setName("Call for GED & Computer Classes");
        gedEducation.setImage_url("https://openclipart.org/image/300px/svg_to_png/185269/phone-icon.png");
        gedEducation.setUrl("16126671908");
        multiMap.put("Education", gedEducation);

        String json = gson.toJson(multiMap.asMap());
        System.out.print(json);
    }
}