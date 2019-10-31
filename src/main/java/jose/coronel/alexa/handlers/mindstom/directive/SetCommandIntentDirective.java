package jose.coronel.alexa.handlers.mindstom.directive;

public class SetCommandIntentDirective {

    private String type;
    private String command;
    private int speed;

    public SetCommandIntentDirective() {
    }

    public SetCommandIntentDirective(String type, String command, int speed) {
        this.type = type;
        this.command = command;
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
