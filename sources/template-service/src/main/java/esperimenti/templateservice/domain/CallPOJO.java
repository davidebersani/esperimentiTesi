package esperimenti.templateservice.domain;

import java.util.List;

public class CallPOJO {

    private String service_to_call;

    private IPCType ipc_type;

    private boolean going_to_fail;

    private List<CallPOJO> next_calls;

    public CallPOJO() {
        
    }

    public CallPOJO(String service_to_call, IPCType ipc_type, boolean going_to_fail, List<CallPOJO> next_calls) {
        this.service_to_call = service_to_call;
        this.ipc_type = ipc_type;
        this.going_to_fail = going_to_fail;
        this.next_calls = next_calls;
    }

    public String getService_to_call() {
        return service_to_call;
    }

    public void setService_to_call(String service_to_call) {
        this.service_to_call = service_to_call;
    }

    public IPCType getIpc_type() {
        return ipc_type;
    }

    public void setIpc_type(IPCType ipc_type) {
        this.ipc_type = ipc_type;
    }

    public boolean isGoing_to_fail() {
        return going_to_fail;
    }

    public void setGoing_to_fail(boolean going_to_fail) {
        this.going_to_fail = going_to_fail;
    }

    public List<CallPOJO> getNext_calls() {
        return next_calls;
    }

    public void setNext_calls(List<CallPOJO> next_calls) {
        this.next_calls = next_calls;
    }

    @Override
    public String toString() {
        return "CallPOJO{" +
                "service_to_call='" + service_to_call + '\'' +
                ", ipc_type=" + ipc_type +
                ", going_to_fail=" + going_to_fail +
                ", next_calls=" + next_calls +
                '}';
    }
}
