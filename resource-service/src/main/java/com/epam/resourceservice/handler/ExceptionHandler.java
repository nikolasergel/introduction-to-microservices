package com.epam.resourceservice.handler;

import com.epam.common.dto.ErrorResponse;
import com.epam.resourceservice.exception.Mp3ParsingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        return new ResponseEntity<>(new ErrorResponse(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(
            HttpMediaTypeNotSupportedException exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid file format: " + exc.getContentType() + ". Only MP3 files are allowed", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Mp3ParsingException.class)
    public ResponseEntity<ErrorResponse> handleMp3ParserException(
            Mp3ParsingException ex,
            HttpServletRequest request,
            HttpServletResponse response){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
