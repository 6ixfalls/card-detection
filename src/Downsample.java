public class Downsample {


    private static short[][] sampleChannel(short[][] channel, int factor) {
        short[][] downsampled = new short[channel.length / factor][channel[0].length / factor];

        // Do stuff with color channels here
        for (int row = 0; row < downsampled.length; row++) {
            for (int col = 0; col < downsampled[0].length; col++) {
                int colorSum = 0;
                int added = 0;
                for (int j = 0; j < factor; j++) {
                    for (int k = 0; k < factor; k++) {
                        int neighborRow = Math.clamp((long) row * factor + j, 0, channel.length - 1);
                        int neighborCol = Math.clamp((long) col * factor + k, 0, channel[0].length - 1);
                        colorSum += channel[neighborRow][neighborCol];
                        added++;
                    }
                }
                downsampled[row][col] = (short)(colorSum / added);
            }
        }

        return downsampled;
    }

    public static DImage processImage(DImage img, int radius) {
        short[][] c = sampleChannel(img.getBWPixelGrid(), radius);

        DImage downsampled = new DImage(c[0].length, c.length);
        downsampled.setPixels(c);
        return downsampled;
    }
}

