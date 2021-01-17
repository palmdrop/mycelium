package util.noise;

import sampling.heightMap.HeightMap;
import util.noise.type.ImprovedPerlinNoise;
import util.noise.type.OpenSimplexNoise;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class FractalHeightMap implements HeightMap {
    public enum Type {
        PERLIN,
        SIMPLEX
    }

    private final double frequency;
    private final double amplitude;
    private final double lacunarity;
    private final double persistence;

    private final List<? extends sampling.heightMap.HeightMap> octaves;

    private boolean normalize = false;
    private double divider = 1.0;

    private static sampling.heightMap.HeightMap[] toOctaveArray(Supplier<? extends sampling.heightMap.HeightMap> noiseSupplier, int numberOfOctaves) {
        if(numberOfOctaves <= 0) throw new IllegalArgumentException();

        sampling.heightMap.HeightMap[] octaves = new Noise[numberOfOctaves];
        for(int i = 0; i< numberOfOctaves; i++) {
            octaves[i] = noiseSupplier.get();
        }
        return octaves;
    }

    private static sampling.heightMap.HeightMap[] toOctaveArray(Type type, int numberOfOctaves, long seed) {
        Random random = new Random(seed);
        Supplier<sampling.heightMap.HeightMap> noiseSupplier = () -> {
            long s = random.nextLong();
            switch(type) {
                case PERLIN: return new ImprovedPerlinNoise(s);
                case SIMPLEX:
                default:     return new OpenSimplexNoise(s);
            }
        };
        return toOctaveArray(noiseSupplier, numberOfOctaves);
    }

    public FractalHeightMap(double frequency, double amplitude, Type type, int numberOfOctaves) {
        this(frequency, amplitude, 2.0, 0.5, type, numberOfOctaves, System.currentTimeMillis());
    }

    public FractalHeightMap(double frequency, double amplitude, double lacunarity, double persistence, Type type, int numberOfOctaves, long seed) {
        this(frequency, amplitude, lacunarity, persistence, toOctaveArray(type, numberOfOctaves, seed));
    }

    public FractalHeightMap(double frequency, double amplitude, double lacunarity, double persistence, Supplier<? extends sampling.heightMap.HeightMap> noiseSupplier, int numberOfOctaves) {
        this(frequency, amplitude, lacunarity, persistence, toOctaveArray(noiseSupplier, numberOfOctaves));
    }

    public FractalHeightMap(double frequency, double amplitude, double lacunarity, double persistence, sampling.heightMap.HeightMap... octaves) {
        if(octaves == null || octaves.length == 0) throw new IllegalArgumentException();

        this.frequency = frequency;
        this.amplitude = amplitude;
        this.lacunarity = lacunarity;
        this.persistence = persistence;

        this.octaves = List.of(octaves);

        this.divider = get((n, f, a) -> a);
    }



    @Override
    public Double get(double x, double y) {
        return get((n, f, a) -> a * n.get(f * x, f * y));
    }

    private Double get(NoiseGet noiseGet) {
        double n = 0;
        for(int i = 0; i < octaves.size(); i++) {
            double freq = frequency * Math.pow(lacunarity, i);
            double amp = amplitude * Math.pow(persistence, i);
            n += amp * noiseGet.get(octaves.get(i), freq, amp);
        }

        if(normalize) n /= divider;

        return n;
    }

    private interface NoiseGet {
        double get(HeightMap noise, double frequency, double amplitude);
    }

    public void setNormalize(boolean normalize) {
        this.normalize = normalize;
    }
}
