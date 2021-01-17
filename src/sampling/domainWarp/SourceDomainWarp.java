package sampling.domainWarp;

import sampling.Sampler;
import sampling.heightMap.HeightMap;
import util.math.MathUtils;

public class SourceDomainWarp<V> extends SimpleDomainWarp<V> {
    public interface SourceToValue<T> {
        double convert(double x, double y, T source);
    }

    public SourceDomainWarp(Sampler<V> sampler) {
        super(sampler);
    }

    public <T> SourceDomainWarp(Sampler<V> sampler, Sampler<T> source, SourceToValue<T> toRotation, SourceToValue<T> toAmount, double min, double max) {
        super(sampler);
        sourceDomainWarp(source, toRotation, toAmount, min, max);
    }

    public <T> SourceDomainWarp<V> sourceDomainWarp(Sampler<T> source, SourceToValue<T> toRotation, SourceToValue<T> toAmount, double min, double max) {
        HeightMap rotation = (x, y) -> MathUtils.limit(toRotation.convert(x, y, source.get(x, y)), 0, 1);
        HeightMap amount   = (x, y) -> MathUtils.limit(toAmount.convert(x, y, source.get(x, y)), 0, 1);
        domainWarp(rotation, amount, min, max);
        return this;
    }
}
