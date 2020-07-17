package org.lilly.core.validate;

import java.util.Map;
import org.lilly.core.enums.ValidateCodeType;
import org.lilly.core.validate.code.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateCodeProcessorHolder {


    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    /**
     * 根据类型找到校验码的校验处理类
     */
    public ValidateCodeProcessor findValidateCodeProcessorByType(ValidateCodeType validateCodeType) {
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorMap
                .get(validateCodeType.getValue().toLowerCase() + "ValidateProcessor");
        return validateCodeProcessor;
    }


}
