package com.megaIT.book.springboot.config.auth;

import com.megaIT.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor // final 필드나 @NotNull 필드의 생성자를 자동 생성해준다
@Component // 직접 작성한 Class 를 Bean 으로 등록 (class에 어노테이션을 붙일 때는 @Component 를 보통 붙인다)
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    // HandlerMethodArgumentResolver: 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있습니다.
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter){ // 컨트롤 메소드의 특정 파라미터를 지원하는 지 "판단"
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override // 파라미터에 전달할 객체 "생성"
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
        return httpSession.getAttribute("user");
    }
}
