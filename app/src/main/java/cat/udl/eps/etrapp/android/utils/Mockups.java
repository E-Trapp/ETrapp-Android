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
            tmp.add(new Event(i,"event " + (i+1), "Event "+ (i+1) + " description", System.currentTimeMillis()));
        }

        return tmp;
    }


}
