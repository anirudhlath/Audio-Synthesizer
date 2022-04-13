public class VFSineWave implements AudioComponent{

    AudioClip inputClip = null;
    public static String label_ = "VF Sine Wave";

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        double phase = 0.0;
        for (int i = 0; i < clip.samplesArray.length; i++) {
            phase += (2 * Math.PI * inputClip.getSample(i)) / inputClip.samplesArray.length;
            clip.setSample(i, (short) (Short.MAX_VALUE * Math.sin(phase)));
        }
        return clip;
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
}
