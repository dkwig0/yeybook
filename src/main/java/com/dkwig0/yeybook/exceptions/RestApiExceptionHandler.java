package com.dkwig0.yeybook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView onUserNotFound(UserNotFoundException e) {
        return new ModelAndView("index");
    }
    @ResponseBody
    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView onUserNotFound(MessageNotFoundException e) {
        return new ModelAndView("index");
    }

    @ResponseBody
    @ExceptionHandler(ChatRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView onChatRoomNotFound(ChatRoomNotFoundException e) {
        return new ModelAndView("index");
    }

}
