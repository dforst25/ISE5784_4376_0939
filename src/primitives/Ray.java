package primitives;


public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor
     * @param head
     * @param direction
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray ray) && this.head.equals( ray.head) && this.direction.equals( ray.direction);
    }



    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
