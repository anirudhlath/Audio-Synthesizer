import java.util.ArrayList;

public class mixer implements AudioComponent {

    ArrayList<AudioClip> inputClips = new ArrayList<>();
    public static String label_ = "Mixer";

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        int[] samples = new int[result.samplesArray.length];
        for (int i = 0; i < inputClips.size(); i++) {
            if (inputClips.get(i) == null) {
                throw new NullPointerException("Please connect the input to the source and rerun the program.");
            } else {
                System.out.println("Processing track " + (i + 1) + "...");
                for (int j = 0; j < result.samplesArray.length; j++) {
                    if (i < 1) {
                        samples[j] = inputClips.get(i).getSample(j);
                    } else {
                        samples[j] += inputClips.get(i).getSample(j);
                    }
                }
            }
        }
        for (int i = 0; i < result.samplesArray.length; i++) {
            if (i < 100) {
                //System.out.println(result.getSample(i) + " / " + inputClips.size() + " = " + (result.getSample(i) / inputClips.size()) );
                //System.out.println(result.getSample(i));
            }
            result.setSample(i, ((short) (samples[i] / inputClips.size())));
        }

        System.out.println("Final track ready!");
        return result;
    }

    @Override
    public boolean hasInput() {
        return true;
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
