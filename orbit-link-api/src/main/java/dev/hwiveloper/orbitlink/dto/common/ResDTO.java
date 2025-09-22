package dev.hwiveloper.orbitlink.dto.common;

import dev.hwiveloper.orbitlink.common.constants.APIConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResDTO {
    public ResDTO() {
        resCode = APIConst.SUCCESS.getResCode();
        resMsg = APIConst.SUCCESS.getResMsg();
    }

    public ResDTO(APIConst apiConst) {
        resCode = apiConst.getResCode();
        resMsg = apiConst.getResMsg();
    }

    public ResDTO(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    private int resCode;
    private String resMsg;
    private Object resData;

    public void setRes(APIConst apiConst) {
        resCode = apiConst.getResCode();
        resMsg = apiConst.getResMsg();
    }

    public void setRes(int resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }
}