package Engine;

public class Key {
    private boolean pressed = false;

    public void toggle(boolean isPressed) {
        this.pressed = isPressed;
    }

    public boolean isPressed() {
        return this.pressed;
    }

}
