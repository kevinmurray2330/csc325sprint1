package murray.csc325sprint1;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;

public class FirestoreContext {
    // Singleton instance
    private static FirestoreContext instance;

    // The Firestore instance
    private Firestore firestore;

    // Private constructor to prevent instantiation
    private FirestoreContext() {
        initializeFirebase();
    }

    // Get the singleton instance
    public static synchronized FirestoreContext getInstance() {
        if (instance == null) {
            instance = new FirestoreContext();
        }
        return instance;
    }

    // Initialize Firebase
    private void initializeFirebase() {
        try {
            // Check if Firebase is already initialized to avoid multiple instances
            if (FirebaseApp.getApps().isEmpty()) {
                // Get Firebase credentials from environment variable instead of file
                InputStream serviceAccount = SimpleConfig.getInstance().getFirebaseKeyAsStream();

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }

            // Initialize the Firestore instance
            firestore = FirestoreClient.getFirestore();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Failed to initialize Firebase: " + ex.getMessage());
            System.exit(1);
        }
    }

    // Get the Firestore instance
    public Firestore getFirestore() {
        return firestore;
    }
}