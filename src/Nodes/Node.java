package Nodes;


import Physics.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Node {

    private Point nodePosition;
    private Point nodeCenterPosition;
    private PhysicsBody physicsBody;
    private Rectangle hitBox;
    private Boolean drawHitbox;
    private Boolean isDragging;

    private String name = "";

    public Color color = Color.red;


    public Node(int x, int y) {
        this.hitBox = new Rectangle(x, y, 0, 0);
        this.nodePosition = new Point(x, y);
        this.nodeCenterPosition = new Point(this.nodePosition.x + hitBox.width / 2, this.nodePosition.y + this.hitBox.height / 2);
        this.physicsBody = new PhysicsBody(10, this.nodeCenterPosition);
        this.isDragging = false;
        this.drawHitbox = true;

    }

    public Boolean containsPoint(Point point) {
        return this.hitBox.contains(point);
    }


    public void update(int ticks) {
        this.physicsBody.update(ticks);
        this.physicsBody.updateVelocity(this, ticks);
        this.nodePosition.y = (int)this.physicsBody.gravityForce(this.nodePosition);
        this.hitBox.x = this.nodePosition.x;
        this.hitBox.y = this.nodePosition.y;
        this.nodeCenterPosition.x = this.nodePosition.x + hitBox.width / 2;
        this.nodeCenterPosition.y = this.nodePosition.y + this.hitBox.height / 2;

    }

    public void render(Graphics g) {
        g.setColor(color);
        //g.fillRect(this.nodePosition.x - 10, this.nodePosition.y - 10,hitBox.width + 20, hitBox.height + 20);
        if (this.drawHitbox) {
            g.fillRect(this.nodePosition.x, this.nodePosition.y,hitBox.width, hitBox.height);
        }
    }

    public Point getNodePosition() {
        return nodePosition;
    }

    public void setNodePosition(Point nodePosition) {
        this.nodePosition = nodePosition;
    }

    public Point getNodeCenterPosition() {
        return nodeCenterPosition;
    }

    public void setNodeCenterPosition(Point nodeCenterPosition) {
        this.nodeCenterPosition = nodeCenterPosition;
    }

    public PhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void setPhysicsBody(PhysicsBody physicsBody) {
        this.physicsBody = physicsBody;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public Boolean getDrawHitbox() {
        return drawHitbox;
    }

    public void setDrawHitbox(Boolean drawHitbox) {
        this.drawHitbox = drawHitbox;
    }

    public Boolean getDragging() {
        return isDragging;
    }

    public void setDragging(Boolean dragging) {
        isDragging = dragging;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
