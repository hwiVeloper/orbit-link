package dev.hwiveloper.orbitlink.common.exception;

import dev.hwiveloper.orbitlink.common.constants.APIConst;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    int code;

    String message;

    public ApplicationException() {
        this.code = APIConst.SYSTEM_ERROR.getResCode();
        this.message = APIConst.SYSTEM_ERROR.getResMsg();
    }

    public ApplicationException(APIConst apiConst) {
        this.code = apiConst.getResCode();
        this.message = apiConst.getResMsg();
    }

}
