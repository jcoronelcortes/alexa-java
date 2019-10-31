package jose.coronel.alexa.handlers.mindstom.directive;

public class MoveIntentDirective {

    private String type;
    private String direction;
    private int duration;
    private int speed;

    public MoveIntentDirective() {
    }

    public MoveIntentDirective(String type, String direction, int duration, int speed) {
        this.type = type;
        this.direction = direction;
        this.duration = duration;
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
