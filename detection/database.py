import firebase_admin
from firebase_admin import credentials, db
import time

class DatabaseManager:
    def __init__(self, credential_path, database_url):
        """
        Initializes the Firebase connection.
        """
        cred = credentials.Certificate(credential_path)
        firebase_admin.initialize_app(cred, {
            'databaseURL': database_url
        })
        self.root_ref = db.reference()
        self.hand_data_ref = self.root_ref.child('hand_data')
        self.body_data_ref = self.root_ref.child('body_data')

    def store_hand_data(self, hand_id, body_id, x, y):
        """Stores hand data in Firebase Realtime Database."""
        timestamp = int(time.time())
        try:
            self.hand_data_ref.child(str(hand_id)).set({
                'body_id': body_id,
                'x': x,
                'y': y,
                'timestamp': timestamp
            })
        except Exception as e:
            print(f"Error storing hand data: {e}")

    def store_body_data(self, body_id, box):
        """Stores body data in Firebase Realtime Database."""
        timestamp = int(time.time())
        try:
            self.body_data_ref.child(str(body_id)).set({
                'x_min': box[0],
                'x_max': box[1],
                'y_min': box[2],
                'y_max': box[3],
                'timestamp': timestamp
            })
        except Exception as e:
            print(f"Error storing body data: {e}")

    def cleanup(self):
        """Cleans up Firebase resources."""
        firebase_admin.delete_app(firebase_admin.get_app())

    def clear_data(self):
        """Clear hand and body data"""
        self.hand_data_ref.delete()
        self.body_data_ref.delete()