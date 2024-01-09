package tw.com.cube.demo.cube_demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractBasicController {

  protected static ResponseEntity<String> returnResponse(String reqData) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json; charset=utf-8");
    return new ResponseEntity<String>(reqData, responseHeaders, HttpStatus.OK);
  }
}
