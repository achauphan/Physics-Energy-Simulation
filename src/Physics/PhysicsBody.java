package Physics;
import java.awt.Point;
import java.util.ArrayList;
import Nodes.*;


public class PhysicsBody extends PhysicsWorld {

    public double mass;
    public Boolean affectedByGravity;
    public Boolean allowRotation;
    public Boolean isPinned;
    public Boolean isDynamic; //Dynamic, can be moved by other objects during collisions
    public Boolean isStatic; //Static, cannot be moved by the player with mouse

    public double velocity;
    public double xVelocity;
    public double yVelocity;


    public int categoryID; //Numerical identifier for a node
    public ArrayList<Integer> contactIDs;
    public ArrayList<Integer> collisionIDs;

    public Point referencePosition;

    public PhysicsBody (double mass, Point refPos) {
        super();
        //System.out.println("ass");
        this.mass = mass;
        this.referencePosition = refPos;

        this.affectedByGravity = false;
        this.allowRotation = true;
        this.isPinned = false;
        this.isDynamic = true;
        this.isStatic = true;

        this.velocity = 80;
        this.xVelocity = 0;
        this.yVelocity = 0;

        this.categoryID = (int)Math.random() * 1000;
        this.contactIDs = new ArrayList<>();
        this.collisionIDs = new ArrayList<>();




    }

    public void update(int ticks) {

    }

    public void updatePosWithVelocity(Node node) {
        node.getNodePosition().x += this.xVelocity;
        //node.nodePosition.y -= this.yVelocity;
    }

    public double gravityForce(Point initialPoint) {
        double finalY = initialPoint.y;
        if (this.affectedByGravity && isDynamic) {
            this.yVelocity += this.gravity;
            finalY = finalY + this.yVelocity;
            return finalY;
        } else {
            return initialPoint.y;
        }
    }

    public double getSpringEnergy(SpringNode node) {
        double springDisplacement;
        //System.out.println(node.calcDistanceFromEquilibrium());
        if (node.calcDistanceFromEquilibrium() > node.maxSpringDisplacement) {
            springDisplacement = node.maxSpringDisplacement;
        } else {
            springDisplacement = node.calcDistanceFromEquilibrium();
        }

        springDisplacement /= 10; //Displacement is in pixels. 10 pixels = 1 meter
        //System.out.println(springDisplacement);
        return .5 * node.SPRING_CONSTANT * springDisplacement * springDisplacement;

    }


    public double vFromPEsToKEPEg(Node node, Node groundNode) {
        if (node instanceof SpringNode) {
            double springDisplacement = 1;
            if (((SpringNode) node).calcDistanceFromEquilibrium() > ((SpringNode) node).maxSpringDisplacement) {
                springDisplacement = ((SpringNode) node).maxSpringDisplacement;
            } else {
                springDisplacement = (((SpringNode) node).calcDistanceFromEquilibrium());
            }


            double springConstant = ((SpringNode) node).SPRING_CONSTANT;
            double projectileMass = ((SpringNode) node).projectileMass;
            double height = groundNode.getNodePosition().y - node.getNodeCenterPosition().y;

            System.out.println("k: " + springConstant + " x: " + springDisplacement + " mass: " + projectileMass + " height: " + height);


            //double velocity = Math.sqrt((springConstant * springDisplacement * springDisplacement - 2 * projectileMass * 98 * height) / projectileMass);
            double velocity = Math.sqrt((springConstant * springDisplacement * springDisplacement) / projectileMass);
            System.out.println("velcoity: " + velocity);
            return velocity;
        } else {
            return 0;
        }
    }

    //Called Specifically from the node and not this update method
    public void updateVelocity(Node node, int ticks) {
        if (node.getName() == "flower") {
            //System.out.println(referencePosition.toString());
        }

//        if (node.name == "flower") {
//            Point currentPosition = node.nodeCenterPosition;
//            System.out.println("ref: " + this.referencePosition.toString() + " current: " + currentPosition.toString());
//
//            if (currentPosition.x != referencePosition.x || currentPosition.y != referencePosition.y) {
//                System.out.println(node.name + " moved");
//                referencePosition = currentPosition;
//            }
//        }



    }



}
