package tw.com.cube.demo.cube_demo.exception;

public class NoDataException extends Exception {
  public NoDataException(String noDataFound) {
    super(noDataFound);
  }
}
