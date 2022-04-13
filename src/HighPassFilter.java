public class HighPassFilter implements AudioComponent{
 // <<<<<<<<<<< UNDER CONSTRUCTION >>>>>>>>>> //
    AudioClip inputClip = null;
    double frequency;
    public static String label_ = "High Pass Filter";

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        if (inputClip == null) {
            throw new NullPointerException("Please connect the input to the source and rerun the program.");
        } else {
            for (int i = 0; i < inputClip.samplesArray.length; i++) {
                if (inputClip.samplesArray[i] >= frequency)
                result.setSample(i, inputClip.samplesArray[i]);
            }
        }
        return result;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        if (hasInput()) {
            inputClip = input.getClip();
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

    public HighPassFilter(int cutoff) {
        frequency = cutoff;
    }
}
