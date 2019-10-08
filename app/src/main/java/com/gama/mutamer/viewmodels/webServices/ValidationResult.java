package com.gama.mutamer.viewModels.webServices;


public class ValidationResult {
    private boolean Success;
    private int Message;

    public ValidationResult(boolean _valid, int _messageResourceId) {
        setSuccess(_valid);
        setMessage(_messageResourceId);
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getMessage() {
        return Message;
    }

    public void setMessage(int message) {
        Message = message;
    }
}
