import java.util.ArrayList;

public class SineWave implements AudioComponent {

    int frequency;
    ArrayList<AudioClip> inputClips = new ArrayList<>();
    public static String label_ = "Sine Wave";


    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < clip.samplesArray.length; i++) {
            clip.setSample(i,
                    (short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * frequency * i / AudioClip.sampleRate))); //
            // Configured for a sine wave of int frequency, the highest possible amplitude.

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

    public SineWave(int f) {
        frequency = f;
    }
}
