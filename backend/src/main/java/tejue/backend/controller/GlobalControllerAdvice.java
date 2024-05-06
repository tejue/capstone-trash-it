package tejue.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tejue.backend.exception.GameNotFoundException;
import tejue.backend.exception.PlayerNotFoundException;
import tejue.backend.model.ErrorMessage;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePlayerNotFoundException(PlayerNotFoundException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST + ": An Error occurred - Player could not be found!");
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleGameNotFoundException(GameNotFoundException exception) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST + ": An Error occurred - Game could not be found!");
    }
}
