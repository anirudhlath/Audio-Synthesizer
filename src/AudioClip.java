import java.util.Arrays;
import java.util.Random;

public class AudioClip {
    static double duration = 2.0;
    static int sampleRate = 44100;
    static int TOTAL_SAMPLES = (int) (duration * sampleRate);
    public byte[] bytesArray = new byte[TOTAL_SAMPLES * 2];
    public short[] samplesArray = new short[TOTAL_SAMPLES];

    public short getSample(int index) {
        return samplesArray[index];
    }

    public void setSample(int index, short value) {
        samplesArray[index] = value;
        convertSampletoByte(index);
    }

    public byte[] getData() {
        return Arrays.copyOf(bytesArray, bytesArray.length);
    }

    public short[] getSamplesData() {
        return Arrays.copyOf(samplesArray, samplesArray.length);
    }

    public void convertBytestoSamples() {
        for (int i = 0; i < bytesArray.length / 2; i++) {
            byte high = bytesArray[(i * 2) + 1];
            byte low = bytesArray[i * 2];
            short sample = (short) (high << 8 | low & 0xFF);
            samplesArray[i] = sample;
        }
    }

    public void convertSamplestoBytes() {
        for (int i = 0; i < samplesArray.length; i++) {
            byte low = (byte) (samplesArray[i] & 0xff);
            byte high = (byte) ((samplesArray[i] >>> 8) & 0xff);
            bytesArray[i * 2] = low;
            bytesArray[(i * 2) + 1] = high;
        }
    }

    public void convertSampletoByte(int i) {
        byte low = (byte) (samplesArray[i] & 0xff);
        byte high = (byte) ((samplesArray[i] >>> 8) & 0xff);
        bytesArray[i * 2] = low;
        bytesArray[(i * 2) + 1] = high;
    }

    public AudioClip() {
        Random random = new Random();
        for (int i = 0; i < samplesArray.length; i++) {
            samplesArray[i] = (short) (-32768 + random.nextInt(32768 + 32768));
        }
    }

    public AudioClip(double d, int sRate) {
        Random random = new Random();

        duration = d;
        sampleRate = sRate;
        TOTAL_SAMPLES = (int) duration * sampleRate;
        bytesArray = new byte[TOTAL_SAMPLES * 2];
        samplesArray = new short[TOTAL_SAMPLES];

//        for (int i = 0; i < samplesArray.length; i++) {
//            samplesArray[i] = (short) (Short.MIN_VALUE + random.nextInt(Short.MAX_VALUE * 2));
//        }
    }
}
