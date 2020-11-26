package com.jieyou.blog;

/**
 * @author jieyou
 * @date 2020/7/26 - 14:56
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{
    public NotFoundException()
    {
    }

    public NotFoundException(String message)
    {
        super(message);
    }
    public NotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
