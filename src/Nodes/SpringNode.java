package Nodes;

import java.awt.*;
import Engine.Engine;
import java.util.ArrayList;

public class SpringNode extends SpriteNode {

    public Engine e;

    public final int SPRING_CONSTANT = 30;
    public int maxSpringDisplacement = 100;
    public Point initialPosition;
    //public Boolean isDragging;
    public double angle;
    public final ArrayList<Node> projectileList;
    public double projectileMass;


    public SpringNode(String imagePath, int x, int y, Engine e) {
        super(imagePath, x, y);
        this.initialPosition = new Point(this.getNodePosition().x + this.getHitBox().width / 2, this.getNodePosition().y + this.getHitBox().height / 2);
        //this.isDragging = false;
        this.getPhysicsBody().isStatic = false;
        this.e = e;
        this.angle = 0;

        projectileList = new ArrayList<Node>();
        projectileMass = 50;

    }

    public void launchProjectile(double velMagnitude) {
       double launchAngle = -this.angle;
       SpriteNode projectile = new SpriteNode("resources/rock.png",this.initialPosition.x - 15 , this.initialPosition.y + 15);
       projectile.getHitBox().width = 30;
       projectile.getHitBox().height = 30;
       projectile.color = null;
       projectile.setDrawHitbox(false);
       projectile.getPhysicsBody().categoryID = 1;
       projectile.getPhysicsBody().velocity = velMagnitude;
       System.out.println("xVel: " + projectile.getPhysicsBody().velocity * Math.cos(3.14159 - this.angle));
       System.out.println("yVel: " + -projectile.getPhysicsBody().velocity * Math.sin(3.14159 - this.angle));
       projectile.getPhysicsBody().xVelocity = projectile.getPhysicsBody().velocity * Math.cos(3.14159 - this.angle);
       projectile.getPhysicsBody().yVelocity = -projectile.getPhysicsBody().velocity * Math.sin(3.14159 - this.angle);
       projectile.getPhysicsBody().mass = projectileMass;

       projectile.getPhysicsBody().affectedByGravity = true;

       projectileList.add(projectile);

//       Node copy = projectile;
//       copy.physicsBody.affectedByGravity = false;
//       copy.physicsBody.isStatic = false;
//       copy.nodePosition.x = 100;
//        copy.nodePosition.y = 100;
//        copy.physicsBody.xVelocity = 0;
//        projectileList.add(copy);
//        e.nodeList.add(copy);

    }


    public void update(int ticks) {
        super.update(ticks);
        this.updateAngle();

        for (Node node : projectileList) {
            node.update(ticks);
            node.getPhysicsBody().updatePosWithVelocity(node);
            if (node.getNodeCenterPosition().y > e.spring.getNodeCenterPosition().y) {
                projectileList.remove(node);

                break;
            }
        }
    }

    //@Override
    public void render(Graphics g) {
        super.render(g);
        g.setColor(Color.gray);
        g.drawLine(this.initialPosition.x, this.initialPosition.y, this.getNodeCenterPosition().x, this.getNodeCenterPosition().y);
        g.setColor(Color.black);
        g.drawString("Launch Angle: "+ Math.toDegrees(3.14 - this.angle), 22, 100);
        if (this.calcDistanceFromEquilibrium() > maxSpringDisplacement) {
            g.drawString("Displacement: "+ maxSpringDisplacement / 10 + "m", 22, 125);
        } else {
            g.drawString("Displacement: "+ this.calcDistanceFromEquilibrium() / 10 + "m", 22, 125);
        }

        for (Node node : projectileList) {
            node.render(g);
        }
    }

    public double calcDistanceFromEquilibrium() {
        if (e.inputHandler.getSelectedNode() == this) {
            double xDifference = e.inputHandler.getMouseClickPos().x - this.initialPosition.x;
            double yDifference = e.inputHandler.getMouseClickPos().y - this.initialPosition.y;
            double distance = Math.sqrt(xDifference * xDifference + yDifference * yDifference);

            return distance;
        } else {
            return 0;
        }
    }

    public void updateAngle() {
        if (this.getDragging()) {
            this.angle = Math.atan2(this.getNodeCenterPosition().y - this.initialPosition.y, this.getNodeCenterPosition().x - this.initialPosition.x);
        }
    }
}
