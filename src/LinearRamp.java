import java.util.ArrayList;

public class LinearRamp implements AudioComponent {
    ArrayList<AudioClip> inputClips = new ArrayList<>();
    float start_;
    float stop_;
    public static String label_ = "Linear Ramp";

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        float numSamples = result.samplesArray.length;
        for (int i = 0; i < numSamples; i++) {
            result.setSample(i, (short) ((start_ * (numSamples - i) + stop_ * i)/numSamples));
        }
        return result;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        if (hasInput()) {
            inputClips.add(input.getClip());
            System.out.println("Added track " + inputClips.size() + " to the linear ramp!");
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
        return false;
    }

    @Override
    public boolean hasVolume() {
        return false;
    }

    public LinearRamp(int start, int stop) {
        start_ = (float) start;
        stop_ = (float) stop;
    }
}
