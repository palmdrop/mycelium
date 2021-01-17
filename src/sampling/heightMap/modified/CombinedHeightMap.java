package sampling.heightMap.modified;

import sampling.heightMap.HeightMap;
import util.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class CombinedHeightMap implements HeightMap {
    private final HeightMap controller;
    private final List<HeightMap> layers;

    public CombinedHeightMap(HeightMap controller, HeightMap first, HeightMap... rest) {
        this.controller = controller;
        this.layers = new ArrayList<>();
        layers.add(first);
        layers.addAll(List.of(rest));
    }

    @Override
    public Double get(double x, double y) {
        HeightMap hm = getLayer(x, y);
        return hm.get(x, y);
    }

    private HeightMap getLayer(double x, double y) {
        double n = controller.get(x, y);
        int index = (int) (n * layers.size());
        index = MathUtils.limit(index, 0, layers.size() - 1);
        return layers.get(index);
    }

    public void addHeightMap(HeightMap layer) {
        layers.add(layer);
    }
}
