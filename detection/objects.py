import cv2
import mediapipe as mp
import numpy as np

class ObjectDetector:
    def __init__(self, model_type='coco'):
        """
        Initializes the ObjectDetector class.

        Args:
            model_type (str): The type of model to use ('coco' or 'yolo').
                'coco': Uses the COCO SSD model from MediaPipe.
                'yolo': Uses a YOLO model (currently not fully implemented).
        """
        self.model_type = model_type
        self.mp_object = mp.solutions.object_detection
        self.mp_drawing = mp.solutions.drawing_utils

        if model_type == 'coco':
            self.detection = self.mp_object.ObjectDetection(
                static_image_mode=False,
                max_num_objects=5,  # Adjust as needed
                min_confidence=0.5,  # Adjust as needed
                model_selection=0  # 0 or 1.  0 is faster, 1 is more accurate.
            )
        elif model_type == 'yolo':
            raise NotImplementedError("YOLO detection is not yet implemented.")
            # Future implementation would involve loading a pre-trained YOLO model here.
        else:
            raise ValueError(f"Invalid model type: {model_type}.  Must be 'coco' or 'yolo'.")

        self.label_map_coco = {  # COCO object labels
            1: 'person', 2: 'bicycle', 3: 'car', 4: 'motorcycle', 5: 'airplane',
            6: 'bus', 7: 'train', 8: 'truck', 9: 'boat', 10: 'traffic light',
            11: 'fire hydrant', 12: 'stop sign', 13: 'parking meter', 14: 'bench',
            15: 'bird', 16: 'cat', 17: 'dog', 18: 'horse', 19: 'sheep', 20: 'cow',
            21: 'elephant', 22: 'bear', 23: 'zebra', 24: 'giraffe', 25: 'backpack',
            26: 'umbrella', 27: 'handbag', 28: 'tie', 29: 'suitcase', 30: 'frisbee',
            31: 'skis', 32: 'snowboard', 33: 'sports ball', 34: 'kite', 35: 'baseball bat',
            36: 'baseball glove', 37: 'skateboard', 38: 'surfboard', 39: 'tennis racket',
            40: 'bottle', 41: 'wine glass', 42: 'cup', 43: 'fork', 44: 'knife', 45: 'spoon',
            46: 'bowl', 47: 'banana', 48: 'apple', 49: 'sandwich', 50: 'orange',
            51: 'broccoli', 52: 'carrot', 53: 'hot dog', 54: 'pizza', 55: 'donut',
            56: 'cake', 57: 'chair', 58: 'couch', 59: 'potted plant', 60: 'bed',
            61: 'dining table', 62: 'toilet', 63: 'tv', 64: 'laptop', 65: 'mouse',
            66: 'remote', 67: 'keyboard', 68: 'cell phone', 69: 'microwave', 70: 'oven',
            71: 'toaster', 72: 'sink', 73: 'refrigerator', 74: 'book', 75: 'clock',
            76: 'vase', 77: 'scissors', 78: 'teddy bear', 79: 'hair dryer', 80: 'toothbrush'
        }

    def detect_objects(self, image):
        """
        Detects objects in the given image.

        Args:
            image (numpy.ndarray): The input image as a NumPy array (e.g., from cv2.imread()).

        Returns:
            list: A list of detection results. Each result is a dictionary containing:
                - 'box':  Bounding box coordinates (xmin, ymin, width, height) as integers.
                - 'label': The object label (string).
                - 'confidence': The detection confidence (float).
            None: If no objects are detected.
        """
        if self.model_type == 'coco':
            results = self.detection.process(image)
            if results.detections:
                detections = []
                for det in results.detections:
                    bbox = det.location_data.relative_bounding_box
                    image_height, image_width, _ = image.shape
                    xmin = int(bbox.xmin * image_width)
                    ymin = int(bbox.ymin * image_height)
                    width = int(bbox.width * image_width)
                    height = int(bbox.height * image_height)
                    label_id = det.label_id[0]  # Get the first label
                    label = self.label_map_coco.get(label_id, 'unknown')
                    confidence = det.score[0]
                    detections.append({
                        'box': (xmin, ymin, width, height),
                        'label': label,
                        'confidence': confidence
                    })
                return detections
            else:
                return None
        elif self.model_type == 'yolo':
            raise NotImplementedError("YOLO detection is not yet implemented.")
            # Future implementation would involve:
            # 1. Preprocessing the image for YOLO.
            # 2. Running the image through the YOLO model.
            # 3. Post-processing the YOLO output to get bounding boxes, labels, and confidences.
            # 4. Returning the results in the specified format.
        else:
            return None

    def draw_detections(self, image, detections):
        """
        Draws bounding boxes and labels on the image.

        Args:
            image (numpy.ndarray): The image to draw on.
            detections (list): A list of detection results (as returned by detect_objects()).
        """
        if detections is None:
            return  # No detections to draw

        for det in detections:
            xmin, ymin, width, height = det['box']
            label = det['label']
            confidence = det['confidence']
            color = (0, 255, 0)  # Green for bounding boxes
            cv2.rectangle(image, (xmin, ymin), (xmin + width, ymin + height), color, 2)
            label_text = f"{label}: {confidence:.2f}"
            cv2.putText(image, label_text, (xmin, ymin - 10),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)

    def process_frame(self, frame):
        """
        Processes a single frame to detect and draw objects.  This is the main
        method you'd call in a video processing loop.

        Args:
            frame (numpy.ndarray):  The input frame (image) from a video stream.

        Returns:
            numpy.ndarray:  The processed frame with detections drawn on it.
        """
        # Convert the frame to RGB (MediaPipe requires RGB input)
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        # Detect objects in the frame
        detections = self.detect_objects(rgb_frame)

        # Draw the detections on the original frame
        self.draw_detections(frame, detections)
        return frame

    def run_detection(self, source=0):
        """
        Opens a video capture stream (default: webcam) and continuously processes
        frames to detect objects.  Displays the resulting video with detections.

        Args:
            source (int or str): The video source. 0 for default camera, or a video file path.
        """
        cap = cv2.VideoCapture(source)
        if not cap.isOpened():
            print(f"Error: Could not open video source: {source}")
            return

        while True:
            ret, frame = cap.read()
            if not ret:
                print("Error: Could not read frame. Exiting.")
                break

            processed_frame = self.process_frame(frame)

            cv2.imshow("Object Detection", processed_frame)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

        cap.release()
        cv2.destroyAllWindows()

    def get_objects_on_hand(self, detections, hand_landmarks, image_width, image_height):
        """
        Filters detected objects to find those that are likely to be located on a hand.
        This is a heuristic approach and may not be perfect.  It assumes that if an
        object's bounding box is close to the hand's keypoints, it might be on the hand.

        Args:
            detections (list):  A list of object detections from self.detect_objects().
            hand_landmarks (list): A list of hand landmarks.
            image_width (int): The width of the image.
            image_height (int): The height of the image.

        Returns:
            list: A filtered list of detections that are likely on the hand.  Returns
                  an empty list if no objects are found on the hand.
        """
        if not detections or not hand_landmarks:
            return []

        objects_on_hand = []
        for det in detections:
            xmin, ymin, width, height = det['box']
            xmax = xmin + width
            ymax = ymin + height

            # Calculate the center of the object's bounding box
            obj_center_x = (xmin + xmax) / 2
            obj_center_y = (ymin + ymax) / 2

            # Check if any hand landmark is close to the object's center
            for landmark in hand_landmarks:
                # Convert landmark coordinates to pixel coordinates
                hand_x = int(landmark.x * image_width)
                hand_y = int(landmark.y * image_height)

                # Define a radius for proximity (adjust as needed)
                proximity_radius = 50  # pixels

                # Check if the distance between the hand landmark and the object's center
                # is within the proximity radius
                if (hand_x - obj_center_x) ** 2 + (hand_y - obj_center_y) ** 2 <= proximity_radius ** 2:
                    objects_on_hand.append(det)
                    break  # No need to check other landmarks for this object

        return objects_on_hand

def main():
    """
    Main function to run the object detection.  Allows selecting COCO or YOLO.
    """
    # Select model type.  Currently, only 'coco' is fully supported.
    model_type = 'coco' #  or 'yolo'
    detector = ObjectDetector(model_type=model_type)

    # Example of running the default object detection on the webcam.
    # detector.run_detection(source=0)  # 0 for webcam, or a video file path

    # Example of using the object detector and getting objects on hand.
    cap = cv2.VideoCapture(0)
    if not cap.isOpened():
        print("Error opening video capture")
        return

    # Initialize MediaPipe Hands
    mp_hands = mp.solutions.hands
    hands = mp_hands.Hands(static_image_mode=False, max_num_hands=2, min_detection_confidence=0.7)

    while True:
        ret, frame = cap.read()
        if not ret:
            print("Error reading frame")
            break

        image_height, image_width, _ = frame.shape
        # Convert the frame to RGB
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        # Detect objects.
        detections = detector.detect_objects(rgb_frame)
        if detections:
            detector.draw_detections(frame, detections) #draws all detected objects

        # Process the frame with MediaPipe Hands.
        hand_results = hands.process(rgb_frame)

        if hand_results.multi_hand_landmarks:
            for hand_landmarks in hand_results.multi_hand_landmarks:
                # Get the landmarks for the hand.
                hand_landmark_list = hand_landmarks.landmark
                # Filter objects on hand
                objects_on_hand = detector.get_objects_on_hand(detections, hand_landmark_list, image_width, image_height)
                if objects_on_hand:
                    print("Objects on hand:", objects_on_hand)
                    detector.draw_detections(frame, objects_on_hand) #draws the objects detected on the hand.

                # Draw hand landmarks.
                mp.solutions.drawing_utils.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)

        cv2.imshow("Objects and Hands Detection", frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    cap.release()
    cv2.destroyAllWindows()
    hands.close()


if __name__ == "__main__":
    main()
