import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class AudioClipTest {

    AudioClip clip = new AudioClip();
    byte[] bytesInstance = clip.getData();
    short[] samplesInstance = clip.getSamplesData();
    Random random = new Random();

    @Test
    void testGetSample() {
        for (int i = 0; i < clip.samplesArray.length; i++) {
            Assertions.assertEquals(samplesInstance[i], clip.getSample(i));
        }
    }

    @Test
    void testSetSample() {
        for (int i = 0; i < 10; i++) {
            short sample = (short) (-32768 + random.nextInt(32768 + 32768));
            clip.setSample(i, sample);
            Assertions.assertNotEquals(sample, samplesInstance[i]);
        }
    }

}