import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AudioComponentWidget extends Pane {

  HBox node;
  private final AnchorPane parent_;
  private AudioComponent audioComponent_;
  Slider frequencySlider = new Slider();
  Text nodeText;
  static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
  float sliderValue = 0;
  Circle outputCircle;
  Circle inputCircle;
  Line line = new Line();
  Line inputLine = new Line();
  boolean isConnected = false;
  boolean isConnectedToSpeaker = false;
  boolean isInputConnected = false;
  boolean isConnectedToInput = false;
  AudioComponentWidget connectedComponent;

  public AudioComponentWidget(AnchorPane parent, AudioComponent audioComponent) {
    node = new HBox();
    parent_ = parent;
    audioComponent_ = audioComponent;

    VBox leftColumn = new VBox();
    VBox centerColumn = new VBox();
    VBox rightColumn = new VBox();

    node.setStyle(
        "-fx-background-color: white; -fx-border-width: 1px; -fx-border-color: darkgrey; "
            + "-fx-border-radius: 5px; -fx-background-radius: 5px");

    // Left Column
    if (audioComponent_.hasInput()) {
      inputCircle = new Circle(5);
      inputCircle.setFill(Color.BLUE);
      leftColumn.setAlignment(Pos.CENTER_LEFT);
      leftColumn.setSpacing(5);
      leftColumn.setPadding(new Insets(5, 5, 5, 5));
      leftColumn.getChildren().add(inputCircle);
      node.getChildren().add(leftColumn);
    }

    // Center Column

    if (audioComponent_.hasFrequency() || audioComponent_.hasVolume()) {
      nodeText = new Text(audioComponent_.getLabel() + " - " + sliderValue);
      if (audioComponent_.hasVolume()) {
        frequencySlider.setMin(0.0);
        frequencySlider.setMax(1.0);
      } else {
        frequencySlider.setMin(20.0);
        frequencySlider.setMax(20000.0);
      }
      centerColumn.getChildren().add(nodeText);
      centerColumn.getChildren().add(frequencySlider);

    } else {
      nodeText = new Text(audioComponent_.getLabel());
      centerColumn.getChildren().add(nodeText);
    }
    centerColumn.setAlignment(Pos.CENTER);
    centerColumn.setSpacing(5);
    centerColumn.setPadding(new Insets(5, 5, 5, 5));
    node.getChildren().add(centerColumn);

    // Right Column
    Button deleteNodeButton = new Button("x");
    deleteNodeButton.setMaxSize(Double.MIN_VALUE, Double.MIN_VALUE);
    outputCircle = new Circle(5);
    outputCircle.setFill(Color.GREEN);
    rightColumn.setAlignment(Pos.CENTER_RIGHT);
    rightColumn.setSpacing(5);
    rightColumn.setPadding(new Insets(5, 5, 5, 5));
    rightColumn.getChildren().add(deleteNodeButton);
    rightColumn.getChildren().add(outputCircle);
    node.getChildren().add(rightColumn);

    // Close Button
    deleteNodeButton.setOnAction(e -> close());

    // Node
    nodeText.setOnMouseDragged(this::moveWidget);
    outputCircle.setOnMouseDragged(this::drawLine);
    outputCircle.setOnMouseReleased(this::finalizeLine);
    frequencySlider.setOnMouseReleased(this::updateFrequency);

    // Parent
    AnchorPane.setTopAnchor(node, 50.0);
    AnchorPane.setLeftAnchor(node, 50.0);
    this.getChildren().add(node);
    parent.getChildren().add(line);
    line.setVisible(false);
    parent_.getChildren().add(this);
    widgets_.add(this);
  }

  private void updateFrequency(MouseEvent e) {
    sliderValue = (float) frequencySlider.getValue();
    nodeText.setText(audioComponent_.getLabel() + " - " + sliderValue);
    Player.clips_.remove(audioComponent_);
    if (audioComponent_ instanceof volume) {
      audioComponent_ = new volume(sliderValue);
      audioComponent_.connectInput(connectedComponent.audioComponent_);
    } else if (audioComponent_ instanceof SineWave) {
      audioComponent_ = new SineWave((int) sliderValue);
    } else if (audioComponent_ instanceof SquareWave) {
      audioComponent_ = new SquareWave((int) sliderValue);
    }

    line.setVisible(false);
  }

  private void finalizeLine(MouseEvent e) {

    Bounds speakerBounds = SynthGUI.speaker_.localToScene(SynthGUI.speaker_.getBoundsInLocal());
    Bounds bounds = parent_.getBoundsInParent();
    System.out.println("Mouse Released");
    System.out.println(widgets_);

    double speakerDistance =
        Math.sqrt(
            Math.pow(e.getSceneX() - speakerBounds.getCenterX(), 2)
                + Math.pow(e.getSceneY() - speakerBounds.getCenterY(), 2));
    if (speakerDistance < 20.0) {
      line.setEndX(e.getSceneX() - bounds.getMinX());
      line.setEndY(e.getSceneY() - bounds.getMinY());
      if (audioComponent_.hasInput()) {
        if (isInputConnected) {
          System.out.println("Input is connected!");
          Player.clips_.add(this.audioComponent_);
          isConnected = true;
          isConnectedToSpeaker = true;
        }
      } else {
        Player.clips_.add(this.audioComponent_);
        isConnected = true;
        isConnectedToSpeaker = true;
      }

    } else {
      for (AudioComponentWidget widget : widgets_) {
        if (widget != this && widget.inputCircle != null) {
          System.out.println("hit");
          Bounds inputCircleBounds =
              widget.inputCircle.localToScene(widget.inputCircle.getBoundsInLocal());
          double distance =
              Math.sqrt(
                  Math.pow(e.getSceneX() - inputCircleBounds.getCenterX(), 2)
                      + Math.pow(e.getSceneY() - inputCircleBounds.getCenterY(), 2));
          System.out.println(distance);
          if (distance < 5.0) {
            System.out.println("distance is less than 5");
            line.setEndX(e.getSceneX() - bounds.getMinX());
            line.setEndY(e.getSceneY() - bounds.getMinY());
            widget.inputLine = line;
            Player.clips_.remove(this.audioComponent_);
            widget.audioComponent_.connectInput(this.audioComponent_);
            widget.connectedComponent = this;
            isConnectedToInput = true;
            if (isConnectedToInput) {
              widget.isInputConnected = true;
            } else {
              widget.isInputConnected = false;
              widget.connectedComponent = null;
            }
            isConnected = true;
          } else {
            isConnected = false;
          }
        }
      }
    }

    if (isConnected) {
      System.out.println("connected");
      line.setVisible(true);
    } else {
      System.out.println("not connected");
      line.setVisible(false);
    }

    if (!isConnectedToSpeaker) {
      Player.clips_.remove(this.audioComponent_);
      System.out.println("Disconnected from the speaker");
    }
  }

  private void drawLine(MouseEvent e) {
    Player.clips_.remove(this.audioComponent_);
    isConnectedToSpeaker = false;
    isConnectedToInput = false;
    isConnected = false;
    Bounds outputCircleBounds = outputCircle.localToScene(outputCircle.getBoundsInLocal());
    Bounds bounds = parent_.getBoundsInParent();
    line.setVisible(true);
    line.setFill(Color.BLACK);
    line.setStartX(outputCircleBounds.getMinX() - bounds.getMinX());
    line.setStartY(outputCircleBounds.getMinY() - bounds.getMinY());
    line.setEndX(e.getSceneX() - bounds.getMinX());
    line.setEndY(e.getSceneY() - bounds.getMinY());
  }

  private void moveWidget(MouseEvent e) {
    Bounds bound = parent_.getBoundsInParent();
    Bounds outputCircleBounds = outputCircle.localToScene(outputCircle.getBoundsInLocal());
    AnchorPane.setTopAnchor(this, e.getSceneY() - bound.getMinY());
    AnchorPane.setLeftAnchor(this, e.getSceneX() - bound.getMinX());
    line.setStartX(outputCircleBounds.getMinX() - bound.getMinX());
    line.setStartY(outputCircleBounds.getMinY() - bound.getMinY());
    if (inputCircle != null) {
      Bounds inputCircleBounds = inputCircle.localToScene(inputCircle.getBoundsInLocal());
      inputLine.setEndX(inputCircleBounds.getMinX() - bound.getMinX());
      inputLine.setEndY(inputCircleBounds.getMinY() - bound.getMinY());
    }
  }

  private void close() {
    System.out.println(this);
    parent_.getChildren().remove(this);
    parent_.getChildren().remove(line);
    Player.clips_.remove(this.audioComponent_);
  }
}
