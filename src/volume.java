public class volume implements AudioComponent {

    AudioClip inputClip = null;
    double volume;
    public static String label_ = "Volume";

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        if (inputClip == null) {
            throw new NullPointerException("Please connect the input to the source and rerun the program.");
        } else {
            for (int i = 0; i < inputClip.samplesArray.length; i++) {
                result.setSample(i, (short) (volume * inputClip.samplesArray[i]));
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

    public boolean hasVolume() {
        return true;
    }

    public volume(double amp) {
        volume = Math.max(0, Math.min(1, amp));
    }
}
