package com.example.todolist.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j              // validator  인터페이스를 구현한 추상 클래스
public abstract class AbstractValidator<T> implements Validator {

    @Override //  supports 메서드는 검증할 객체의 클래스를 지원하는지 확인하는 메서드
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")  // 컴파일러 경고 무시
    @Override   // validate 메서드는 실제 검증 로직을 처리하는 메서드
    public void validate(Object target, Errors errors) {    // target은 검증 대상 객체, errors는 검증 오류 결과
        try{
            // doValidate 메서드는 서브클래스에서 구현되어 실제 검증 로직을 처리
            doValidate((T)target, errors);  // 검증 대상 객체를 T 타입으로 캐스팅하여 전달
        } catch (RuntimeException e){
            log.error("중복 검증 에러() : " + e.getMessage());
            throw e;    // 예외를 다시 던져 호출한 곳으로 처리할 수 있도록 함.
        }
    }
    // 서브 클래스에서 구현해야 하는 추상 메서드로, 실제 검증 로직을 작성하는 곳.
    protected abstract void doValidate(final T dto,final Errors errors);
}

// validate() 메서드가 doValidate() 메서드와 별도로 존재하는 이유
// doValidate() 에서 발생할 수 있는 예외를 catch하여 로그를 기록하고, 다시 던지는 로직을 validate()에서 처리하고 있다.
// 이는 모든 서브클래스가 doValidate()를 구현할 때마다 동일한 예외 처리 로직을 중복해서 작성하는 것을 방지한다.
// 결론적으로 검증에 필요한 공통 동작을 한 곳에서 처리할 수 있게 해준다.