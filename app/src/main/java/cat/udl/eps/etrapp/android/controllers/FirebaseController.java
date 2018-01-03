package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import cat.udl.eps.etrapp.android.models.EventComment;
import cat.udl.eps.etrapp.android.models.EventMessage;
import cat.udl.eps.etrapp.android.ui.adapters.EventCommentsAdapter;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;

public class FirebaseController {

    private static final String EVENT_MESSAGES_REF = "eventMessages";
    private static final String EVENT_COMMENTS_REF = "eventComments";
    private static final String EVENT_REF = "event%d";
    private static final int MESSAGE_LOAD_LIMIT = 30;

    private static FirebaseController instance;

    private FirebaseController() {
        //Empty Constructor
    }

    public static synchronized FirebaseController getInstance() {
        if (instance == null)
            instance = new FirebaseController();
        return instance;
    }

    public DatabaseReference getDatabaseReference(String reference) {
        return FirebaseDatabase.getInstance().getReference(reference);
    }

    public DatabaseReference getDatabaseReference(String... references) {
        return getDatabaseReference(uriGenerator(references));
    }

    private String uriGenerator(String... data) {
        String temp = "";

        for (String s : data) {
            if (s == null) return "";
            temp = temp.concat(s).concat("/");
        }

        return temp.substring(0, temp.length() - 1);
    }

    public Task<Void> getMessages(String lastKey, long eventKey, EventStreamAdapter eventStreamAdapter) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Query query;
        String refPath = uriGenerator(EVENT_MESSAGES_REF, String.format(EVENT_REF, eventKey));

        if (lastKey == null) {
            query = getDatabaseReference(refPath)
                    .orderByKey()
                    .limitToLast(MESSAGE_LOAD_LIMIT);
        } else {
            query = getDatabaseReference(refPath)
                    .orderByKey()
                    .limitToLast(MESSAGE_LOAD_LIMIT)
                    .endAt(lastKey);
        }


        ChildEventListener eventListener = new ChildEventListener() {

            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventMessage eventMessage = dataSnapshot.getValue(EventMessage.class);
                eventMessage.setKey(dataSnapshot.getKey());
                eventStreamAdapter.addItem(eventMessage);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addChildEventListener(eventListener);

        return tcs.getTask();
    }




    public Task<Void> getComments(String lastKey, long eventKey, EventCommentsAdapter eventCommentsAdapter) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Query query;
        String refPath = uriGenerator(EVENT_COMMENTS_REF, String.format(EVENT_REF, eventKey));

        if (lastKey == null) {
            query = getDatabaseReference(refPath)
                    .orderByKey()
                    .limitToLast(MESSAGE_LOAD_LIMIT);
        } else {
            query = getDatabaseReference(refPath)
                    .orderByKey()
                    .limitToLast(MESSAGE_LOAD_LIMIT)
                    .endAt(lastKey);
        }


        ChildEventListener eventListener = new ChildEventListener() {

            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventComment eventMessage = dataSnapshot.getValue(EventComment.class);
                eventMessage.setKey(dataSnapshot.getKey());
                eventCommentsAdapter.addItem(eventMessage);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        };

        query.addChildEventListener(eventListener);

        return tcs.getTask();
    }

}
