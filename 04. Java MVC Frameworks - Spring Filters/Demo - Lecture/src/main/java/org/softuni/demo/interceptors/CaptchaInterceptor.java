package org.softuni.demo.interceptors;

import org.softuni.demo.annotations.NoCaptcha;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Component
public class CaptchaInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getMethod().equals("POST")) {
            if (handler instanceof HandlerMethod) {
                HandlerMethod methodH = (HandlerMethod) handler;
                if (methodH.getMethod().isAnnotationPresent(NoCaptcha.class)) {
                    return true;
                }
            }
            String sumCandidate = request.getParameter("sum");
            if (sumCandidate == null || sumCandidate.isEmpty()) {
                response.setStatus(403);
                return false;
            }
            long sum = Long.parseLong(sumCandidate);

            Long num1 = (Long) request.getSession().getAttribute("num1");
            Long num2 = (Long) request.getSession().getAttribute("num2");

            if (num1 == null || num2 == null) {
                return false;
            }

            if (sum != (num1 + num2)) {
                return false;
            }

            request.getSession().removeAttribute("num1");
            request.getSession().removeAttribute("num2");
        }

        if (request.getSession().getAttribute("num1") == null
                && request.getSession().getAttribute("num2") == null) {
            Long num1 = (long) new Random().nextInt(10);
            Long num2 = (long) new Random().nextInt(10);

            request.getSession().setAttribute("num1", num1);
            request.getSession().setAttribute("num2", num2);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (request.getMethod().equals("GET") && modelAndView != null) {
            modelAndView.addObject("num1", request.getSession().getAttribute("num1"));
            modelAndView.addObject("num2", request.getSession().getAttribute("num2"));
        }
    }
}
