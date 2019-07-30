package kiri.nstp.web.controller;

import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import kiri.nstp.exception.DuplicatedInsertException;
import kiri.nstp.exception.GoToLoginException;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.exception.OperationFailedException;
import kiri.nstp.exception.PageNotFoundException;
import kiri.nstp.exception.RegistFailException;
import kiri.nstp.exception.UnregistedPgpException;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.exception.WrongPgpFileException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler({WrongInputException.class,
		MissingServletRequestParameterException.class,
		MissingServletRequestPartException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String wrongInput() {
		return "Wrong Input!";
	}
	
	//Here shows normal response to fake!
	@ExceptionHandler(NoResourcePermissionException.class)
	public List<?> noResourcePermission() {
		return new LinkedList<>();
	}
	
	@ExceptionHandler(RegistFailException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String registWrong() {
		return "Regist Failed with duplicated user message!";
	}
	
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public String noPermission() {
		return "No permission~~";
	}
	
	@ExceptionHandler(WrongPgpFileException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String badFile() {
		return "Bad pgp File~~";
	}	

	@ExceptionHandler(UnregistedPgpException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String badPgpUser() {
		return "Please regist and confirm your pgp public key first~~";
	}	

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String wrongLogin() {
		return "Please check your username or password~~";
	}	

	@ExceptionHandler(GoToLoginException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public String goToLogin() {
		return "OK~Go to Login~";
	}

	@ExceptionHandler(PageNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String notFound() {
		return "~~~Not~~~Found~~~";
	}
	
	@ExceptionHandler(DuplicatedInsertException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String duped() {
		return "Already Exist~~>>";
	}
	
	@ExceptionHandler(OperationFailedException.class)
	@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE)
	public String failed() {
		return "Sorry~~~Operation failed";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String globalHandler(Exception e) {
		e.printStackTrace();
		return "Web Server explodes!";
	}

}
