import java.util.ArrayList;

public class SquareWave implements AudioComponent {

    int frequency;
    ArrayList<AudioClip> inputClips = new ArrayList<>();
    public static String label_ = "Square Wave";

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < clip.samplesArray.length; i++) { // Configured for a square wave of int frequency, the
            // highest possible amplitude.
            if (((float) (frequency * i * 2 / AudioClip.sampleRate)) % 2 > 0.5) { // ((float)(frequency * i * 2 /
                // AudioClip.sampleRate)) % 2 was always equal to zero, therefore I used 2 to make the algorithm work
                // and multiplied 2 to the frequency to compensate for the %2.
                clip.setSample(i, Short.MAX_VALUE);
            } else {
                clip.setSample(i, Short.MIN_VALUE);
            }
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        if (hasInput()) {
            inputClips.add(input.getClip());
            System.out.println("Added track " + inputClips.size() + " to the mixer!");
        } else {
            System.out.println("This class does not accept inputs.");
            System.exit(-1);
        }
    }

    @Override
    public String getLabel() {
        return label_;
    }

    @Override
    public boolean hasFrequency() {
        return true;
    }

    @Override
    public boolean hasVolume() {
        return false;
    }

    public SquareWave(int freq) {
        frequency = freq;
    }
}
