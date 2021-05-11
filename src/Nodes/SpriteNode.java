package Nodes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SpriteNode extends Node {

    public BufferedImage sprite;

    public SpriteNode(String imagePath, int x, int y) {
        super(x, y);
        this.sprite = null;
        try {
            this.sprite = ImageIO.read(new FileInputStream(imagePath));
            getHitBox().width = this.sprite.getWidth();
            getHitBox().height = this.sprite.getHeight();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(int ticks) {
        super.update(ticks);
    }


    public void render(Graphics g) {
        super.render(g);
        g.drawImage(sprite, (int) this.getNodePosition().getX(), (int) this.getNodePosition().getY(), null);
        //g.setColor(Color.white);
        //g.drawString("angle: ", 700, 100);
        //g.dispose();
    }


}
