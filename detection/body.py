import cv2
import mediapipe as mp
import numpy as np
from collections import defaultdict

class BodyProcessor:
    def __init__(self, screen_width, screen_height):
        """
        Initializes the Body Processor.
        """
        self.mp_pose = mp.solutions.pose
        self.pose = self.mp_pose.Pose(min_detection_confidence=0.5, min_tracking_confidence=0.5)
        self.mp_drawing = mp.solutions.drawing_utils
        self.display_width = screen_width // 2
        self.display_height = screen_height - int(2 * 37.8)  # Adjusted for 2 cm top cut
        self.next_body_id = 0
        self.body_boxes = defaultdict(lambda: None)

    def process_pose(self, rgb_frame):
        """Processes a frame for pose detection."""
        return self.pose.process(rgb_frame)

    def handle_pose_detection(self, frame, pose_results, database_manager):
        """Handles pose detection and bounding box drawing."""
        if pose_results and pose_results.pose_landmarks:
            landmarks = pose_results.pose_landmarks.landmark
            min_x, max_x, min_y, max_y = self.calculate_bounding_box(landmarks)
            padding = 50
            min_x, max_x = max(0, min_x - padding), min(self.display_width, max_x + padding)
            min_y, max_y = max(0, min_y - padding), min(self.display_height, max_y + padding)

            body_id = self.assign_body_id(min_x, min_y)
            self.body_boxes[body_id] = (min_x, max_x, min_y, max_y)
            database_manager.store_body_data(body_id, (min_x, max_x, min_y, max_y))

            cv2.rectangle(frame, (min_x, min_y), (max_x, max_y), (255, 0, 0), 2)
            cv2.putText(frame, f"Body ID: {body_id}", (min_x, min_y - 10),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 0), 2)
        return self.body_boxes

    def calculate_bounding_box(self, landmarks):
        """Calculates the bounding box from pose landmarks."""
        min_x, max_x, min_y, max_y = float('inf'), float('-inf'), float('inf'), float('-inf')
        for idx, landmark in enumerate(landmarks):
            x = int(landmark.x * self.display_width)
            y = int(landmark.y * self.display_height)
            if idx == 0:  # Nose as starting point.
                min_x, max_x = x, x
                min_y, max_y = y, y
            else:
                min_x, max_x = min(min_x, x), max(max_x, x)
                min_y, max_y = min(min_y, y), max(max_y, y)
        return min_x, max_x, min_y, max_y

    def assign_body_id(self, min_x, min_y):
        """Assigns a body ID, reusing existing IDs if close to an existing body."""
        body_id = self.next_body_id
        for bid, box in self.body_boxes.items():
            if box and abs(box[0] - min_x) < 100 and abs(box[2] - min_y) < 100:
                body_id = bid
                break
        else:
            self.next_body_id += 1
        return body_id

    def close(self):
        """Closes MediaPipe resources."""
        self.pose.close()

