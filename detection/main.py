import cv2
import numpy as np
import mediapipe as mp
from body import BodyProcessor
from hands import HandProcessor
from database import DatabaseManager
from objects import ObjectDetector 

class HandBodyTrackingApp:
    def __init__(self, credential_path, database_url, screen_width=1366, screen_height=768):
        """
        Initializes the Hand and Body Tracking application.
        """
        self.database_manager = DatabaseManager(credential_path, database_url)
        self.body_processor = BodyProcessor(screen_width, screen_height)
        self.hand_processor = HandProcessor(screen_width, screen_height)
        self.object_detector = ObjectDetector(model_type='coco')  # Initialize ObjectDetector

        self.cap = cv2.VideoCapture(0)
        if not self.cap.isOpened():
            raise IOError("Couldn’t open webcam.")

        self.screen_width = screen_width
        self.screen_height = screen_height
        self.display_width = screen_width // 2
        self.top_offset = int(2 * 37.8)
        self.display_height = screen_height - self.top_offset
        self.canvas_width = screen_width
        self.canvas_height = self.display_height
        self.canvas = np.zeros((self.canvas_height, self.canvas_width, 3), dtype=np.uint8)
        self.window_name = "Hand and Body Tracking (Left Half)"

        self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, self.display_width)
        self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, self.display_height)
        cv2.namedWindow(self.window_name, self.canvas_width, self.canvas_height)
        cv2.resizeWindow(self.window_name, self.canvas_width, self.canvas_height)
        cv2.moveWindow(self.window_name, 0, self.top_offset)

        # Initialize MediaPipe Hands
        self.mp_hands = mp.solutions.hands
        self.hands = self.mp_hands.Hands(static_image_mode=False, max_num_hands=2, min_detection_confidence=0.7)

    def run(self):
        """Runs the main application loop."""
        try:
            while True:
                self.process_frame()
                if cv2.waitKey(1) & 0xFF == ord('q'):
                    break
        finally:
            self.cleanup()

    def process_frame(self):
        """Processes a single frame from the webcam."""
        ret, frame = self.cap.read()
        if not ret:
            print("Error: Couldn’t read frame.")
            return

        frame = cv2.flip(frame, 1)
        frame = cv2.resize(frame, (self.display_width, self.display_height))
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        pose_results = self.body_processor.process_pose(rgb_frame)
        hand_results = self.hands.process(rgb_frame) #changed from self.hand_processor.process_hands(rgb_frame)

        body_boxes = self.body_processor.handle_pose_detection(frame, pose_results, self.database_manager)
        hand_positions = self.hand_processor.handle_hand_detection(frame, hand_results, body_boxes, self.database_manager)

        # Detect objects.
        detections = self.object_detector.detect_objects(rgb_frame)
        if detections:
            self.object_detector.draw_detections(frame, detections)  # Draw all detected objects

             # Get objects on hand
            if hand_results.multi_hand_landmarks:
                for hand_landmarks in hand_results.multi_hand_landmarks:
                    hand_landmark_list = hand_landmarks.landmark
                    objects_on_hand = self.object_detector.get_objects_on_hand(detections, hand_landmark_list, self.display_width, self.display_height)
                    if objects_on_hand:
                        print("Objects on hand:", objects_on_hand)
                        self.object_detector.draw_detections(frame, objects_on_hand)  # Draw objects on hand

                    # Draw hand landmarks.
                    mp.solutions.drawing_utils.draw_landmarks(frame, hand_landmarks, self.mp_hands.HAND_CONNECTIONS) #changed from self.hand_processor.mp_hands

        self.canvas[:, :self.display_width] = frame
        cv2.imshow(self.window_name, self.canvas)

    def cleanup(self):
        """Cleans up resources."""
        self.cap.release()
        cv2.destroyAllWindows()
        self.body_processor.close()
        self.hand_processor.close()
        self.hands.close() # added hands close
        self.database_manager.cleanup()
        self.database_manager.clear_data()
        print("Exiting...")

if __name__ == "__main__":
    # Replace with the actual path to your serviceAccountKey.json file
    credential_path = r"C:\Users\Lesego\Desktop\my_project\realtime_db\config\serviceAccountKey.json"
    database_url = 'https://face-detection-4af6e-default-rtdb.firebaseio.com/'
    app = HandBodyTrackingApp(credential_path, database_url)
    app.run()
