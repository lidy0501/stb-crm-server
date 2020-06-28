package cn.stb.stbcrmserver.base;

import cn.stb.stbcrmserver.context.AcContext;
import cn.stb.stbcrmserver.service.RightService;
import cn.stb.stbcrmserver.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RightService rightService;
    @Autowired
    private StaffService staffService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("112233");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.getDeclaringClass() == BasicErrorController.class) return true;

        LoginIgnore loginIgnore = method.getAnnotation(LoginIgnore.class);
        if (loginIgnore != null) return true;

        Right right = method.getAnnotation(Right.class); // todo
        if (right == null) return false;

        RightType[] rightTypes = right.value();
        List<String> rightCodes = Arrays.stream(rightTypes).map(RightType::getCode).collect(Collectors.toList());

        System.out.println("rightCodes-----------"  + rightCodes.get(0));

        if (rightTypes.length == 0) {
            throw new RuntimeException("Annotated @Right annotation need right codes");
        }

        List<String> needRightIds = rightService.queryRightIdsByRightCodes(rightCodes);
        log.info("needRightIds------      {}", needRightIds);
        List<String> staffRightIds = staffService.queryStaffRightIds(AcContext.getStaffId());
        // 求交集
        boolean pass = false; // fale不让通过，true让通过
        for (String staffRightId : staffRightIds) {
            if (needRightIds.contains(staffRightId)) {
                pass = true;
            }
        }
        if (!pass) {
            throw new RuntimeException("无权访问");
        }
        return true;
    }
}
