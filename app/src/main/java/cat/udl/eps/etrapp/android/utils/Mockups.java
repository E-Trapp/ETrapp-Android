package cat.udl.eps.etrapp.android.utils;

import java.util.ArrayList;
import java.util.List;

import cat.udl.eps.etrapp.android.models.Event;

public class Mockups {

    private static boolean loggedIn = false;

    public static boolean isUserLoggedIn(){
        return loggedIn;
    }

    public static void changeLoginStatus() {
        loggedIn = !loggedIn;
    }

    private static final int maxEvents = 25;

    public static List<Event> mockEventList = generateMockupList();

    private static List<Event> generateMockupList() {
        List<Event> tmp = new ArrayList<>();

        for (int i = 0; i < maxEvents; i++) {
            tmp.add(new Event(i,"event " + (i+1), "Event "+ (i+1) + " description", System.currentTimeMillis(), "no_image"));
        }

        return tmp;
    }

    public static List<Event> mockFeaturedEventList = generateFeaturedMockupList();

    private static List<Event> generateFeaturedMockupList() {
        List<Event> tmp = new ArrayList<>();

        String[] images = {
                "https://www.androidcentral.com/sites/androidcentral.com/files/styles/w700/public/postimages/%5Buid%5D/google-io-2015-1-39.jpg",
                "http://www.salesfish.com/wp-content/uploads/2011/11/Experiential-Event-Marketing.jpg",
                "http://www.teachinghumanrights.org/sites/default/files/El_Clasico_590.jpg"
        };

        for (int i = 0; i < 3; i++) {
            tmp.add(new Event(288 + i, "Featured Event " + (i+1), "Featured event description.", 1510704000, images[i]));
        }

        return tmp;
    }


}
