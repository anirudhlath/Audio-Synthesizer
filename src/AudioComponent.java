public interface AudioComponent {
    AudioClip getClip(); // return the current sound produced by this component

    boolean hasInput(); // can you connect something to this as an input?

    void connectInput(AudioComponent input); // connect another device to this input. For most classes implementing
    // this interface, this method will just store a reference to the AudioComponent parameter. If the component
    // doesn't accept inputs, you can assert(false) in here.]

    String getLabel(); // get the label for this audio component

    boolean hasFrequency(); // does the audio component take frequency

    boolean hasVolume();

}
