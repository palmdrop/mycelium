package text;

import processing.core.PApplet;

public class SymbolSorter extends PApplet {
    private final static int size = 800;
    private final static int width = size;
    private final static int height = size;

    private final static int textSize = size / 2;

    private char[] chars;

    public void settings() {
        size(width, height);
    }

    public void setup() {
        rectMode(CORNER);

        long time = System.currentTimeMillis();
        chars = SymbolAnalysis.sortBy(TextTools.renderableAscii,
                100,
                SymbolAnalysis::orientation,
                this);
        System.out.println(System.currentTimeMillis() - time);
    }

    // Current character


    public void draw() {
        background(0, 5, 20);
        textSize(textSize * 2);

        //int index = constrain((int)map(mouseY, 0, height, 0, chars.length), 0, chars.length - 1);
        int index = constrain((int)map(mouseY, 0, height, 0, TextTools.unicode.length), 0, TextTools.unicode.length - 1);
        //char c = chars[index];

        //TextTools.renderCentered(this.g, c + "", width/2, height/2);
        TextTools.renderCentered(this.g, TextTools.unicode[index], width/2, height/2);

    }

    public static void main(String[] args) {
        PApplet.main("ascii.SymbolSorter");
    }
}
