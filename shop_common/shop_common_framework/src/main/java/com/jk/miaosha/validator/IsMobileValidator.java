package com.jk.miaosha.validator;

import com.jk.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName : IsMobileValidator
 * @Author : xiaoqiang
 * @Date : 2018/11/2 15:55:55
 * @Description :
 * @Version ： 1.0
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    /**
     * 在IsMobile注解中添加了一个属性required，所以初始化需要知道这个值
     * 之前我们就知道reqired属性的意思为这个属性是否必须不能为空
     * @param constraintAnnotation
     */
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    /**
     * 验证属性值是否满足手机的格式，调用上一次编写的ValidatorUtil.java中提供的验证手机格式的方法
     * @param s @IsMobile注解标注的属性值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }else{
            if(StringUtils.isEmpty(s)){
                return true;
            }else{
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
