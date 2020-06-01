package esperimenti.stubservice._oldStuff;

import java.util.List;

public class CommandMessage {

    private boolean goingToFail;

    private List<CallPOJO> calls;

    public CommandMessage(){}
    public CommandMessage(boolean goingToFail, List<CallPOJO> calls) {
        this.goingToFail = goingToFail;
        this.calls = calls;
    }

    public boolean isGoingToFail() {
        return goingToFail;
    }

    public void setGoingToFail(boolean goingToFail) {
        this.goingToFail = goingToFail;
    }

    public List<CallPOJO> getCalls() {
        return calls;
    }

    public void setCalls(List<CallPOJO> calls) {
        this.calls = calls;
    }

    @Override
    public String toString() {
        return "CommandMessage{" +
                "goingToFail=" + goingToFail +
                ", calls=" + calls +
                '}';
    }
}
