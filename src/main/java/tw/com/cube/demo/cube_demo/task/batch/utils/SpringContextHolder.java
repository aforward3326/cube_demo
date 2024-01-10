package tw.com.cube.demo.cube_demo.task.batch.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {
  private static ApplicationContext context;

  public void setApplicationContext(ApplicationContext context) throws BeansException {
    SpringContextHolder.context = context;
  }

  public static ApplicationContext getContext() {
    return context;
  }
}
