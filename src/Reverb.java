public class Reverb implements AudioComponent{

    AudioClip inputClip = null;
    double scale;
    int delay;
    public static String label_ = "Reverb";

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        if (inputClip == null) {
            throw new NullPointerException("Please connect the input to the source and rerun the program.");
        } else {
            for (int i = 0; i < inputClip.samplesArray.length - delay; i++) {
                result.samplesArray[i] = inputClip.samplesArray[i];
                result.samplesArray[i + delay] += (scale * result.samplesArray[i]) % Short.MIN_VALUE;
                result.convertSampletoByte(i);
                if (result.samplesArray[i + delay] + (scale * result.samplesArray[i]) < Short.MAX_VALUE && result.samplesArray[i + delay] + (scale * result.samplesArray[i]) < Short.MIN_VALUE) {

                    System.out.println("reverbed " + i + " times");
                }
            }
           // result.convertSamplestoBytes();
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

    public Reverb(double s, int d) {
        delay = d;
        scale = Math.max(0, Math.min(1, s));
    }
}
