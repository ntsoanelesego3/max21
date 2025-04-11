import cv2
import mediapipe as mp
import numpy as np
from collections import defaultdict

class HandProcessor:
    def __init__(self, screen_width, screen_height):
        """
        Initializes the Hand Processor.
        """
        self.mp_hands = mp.solutions.hands
        self.hands = self.mp_hands.Hands(max_num_hands=200, min_detection_confidence=0.7)
        self.mp_drawing = mp.solutions.drawing_utils
        self.display_width = screen_width // 2
        self.display_height = screen_height - int(2 * 37.8)  # Adjusted for 2 cm top cut
        self.next_hand_id = 0
        self.hand_positions = defaultdict(lambda: {'pos': None, 'body_id': None})

    def process_hands(self, rgb_frame):
        """Processes a frame for hand detection."""
        return self.hands.process(rgb_frame)

    def handle_hand_detection(self, frame, hand_results, body_boxes, database_manager):
        """Handles hand detection and landmark drawing."""
        if hand_results and hand_results.multi_hand_landmarks:
            for hand_landmarks in hand_results.multi_hand_landmarks:
                self.mp_drawing.draw_landmarks(frame, hand_landmarks, self.mp_hands.HAND_CONNECTIONS)
                wrist = hand_landmarks.landmark[0]
                x_pos, y_pos = int(wrist.x * self.display_width), int(wrist.y * self.display_height)
                current_pos = (x_pos, y_pos)

                hand_id, body_id = self.assign_hand_id(x_pos, y_pos, body_boxes)
                if hand_id is not None:
                    database_manager.store_hand_data(hand_id, body_id, x_pos, y_pos)
                    cv2.putText(frame, f"ID: {hand_id}", (x_pos + 15, y_pos - 10),
                                cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
                    cv2.circle(frame, (x_pos, y_pos), 10, (0, 255, 0), -1)
        return self.hand_positions

    def assign_hand_id(self, x_pos, y_pos, body_boxes):
        """Assigns a hand ID, reusing existing IDs if close to an existing hand within the same body."""
        hand_id = None
        body_id = None
        for body_id, box in body_boxes.items():
            if box and box[0] <= x_pos <= box[1] and box[2] <= y_pos <= box[3]:
                for hid, data in self.hand_positions.items():
                    if data['body_id'] == body_id and data['pos'] and \
                            np.sqrt((x_pos - data['pos'][0]) ** 2 + (y_pos - data['pos'][1]) ** 2) < 50:
                        hand_id = hid
                        break
                if hand_id is None:
                    hand_id = self.next_hand_id
                    self.next_hand_id += 1
                self.hand_positions[hand_id] = {'pos': (x_pos, y_pos), 'body_id': body_id}
                break
        return hand_id, body_id

    def close(self):
        """Closes MediaPipe resources."""
        self.hands.close()