import java.util.ArrayList;
import java.util.Random;

public class WhiteNoise implements AudioComponent{
    ArrayList<AudioClip> inputClips = new ArrayList<>();
    Random random = new Random();
    public static String label_ = "White Noise";


    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < clip.samplesArray.length; i++) {
            clip.setSample(i, (short) (Short.MIN_VALUE + random.nextInt(Short.MAX_VALUE * 2)));
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
        return false;
    }

    @Override
    public boolean hasVolume() {
        return false;
    }

}
