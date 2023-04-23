package sk.stuba.fei.uim.oop.blocks;

import java.awt.*;

public class CurvedPipe extends Pipe {
    private OrientationCurved orientation;

    public CurvedPipe(int x, int y, int size, OrientationCurved orientation, boolean waterFlows, boolean isHighlighted) {
        super(x,y,size,waterFlows,isHighlighted);
        this.orientation = orientation;
    }

    @Override
    public void setWaterFlows(boolean waterFlows) {
        this.waterFlows = waterFlows;
    }

    @Override
    public boolean isFacingUp() {
        return this.orientation == OrientationCurved.UP_RIGHT || this.orientation == OrientationCurved.UP_LEFT;
    }

    @Override
    public boolean isFacingDown() {
        return this.orientation == OrientationCurved.DOWN_RIGHT || this.orientation == OrientationCurved.DOWN_LEFT;
    }

    @Override
    public boolean isFacingLeft() {
        return this.orientation == OrientationCurved.UP_LEFT || this.orientation == OrientationCurved.DOWN_LEFT;
    }

    @Override
    public boolean isFacingRight() {
        return this.orientation == OrientationCurved.UP_RIGHT || this.orientation == OrientationCurved.DOWN_RIGHT;
    }

    @Override
    public void rotate() {
        this.orientation = OrientationCurved.values()[(this.orientation.ordinal()+1)%4];
    }

    @Override
    public void draw(Graphics g) {
        if(isHighlighted)
            drawHighlight(g);
        else
            drawBackground(g);
        drawBorder(g);
        if(this.waterFlows){
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.GRAY);
        }
        if (orientation == OrientationCurved.UP_RIGHT) {
            g.fillRect(x + size / 3, y, size / 3, size/3);
            g.fillRect(x+size/3, y+size/3, size-size/3, size/3);
        } else if (orientation == OrientationCurved.DOWN_RIGHT) {
            g.fillRect(x + size / 3, y+size/3, size / 3, size-size/3);
            g.fillRect(x+size/3, y+size/3, size-size/3, size/3);
        } else if (orientation == OrientationCurved.DOWN_LEFT) {
            g.fillRect(x + size / 3, y+size/3, size / 3, size-size/3);
            g.fillRect(x, y+size/3, size-size/3-2, size/3);
        } else if (orientation == OrientationCurved.UP_LEFT) {
            g.fillRect(x, y+size/3, size-size/3-2, size/3);
            g.fillRect(x + size / 3, y, size / 3, size/3+size/3);
        }
    }
}
