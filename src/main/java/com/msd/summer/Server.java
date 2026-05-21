package com.msd.summer;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class Server {

  public static void start(final ApplicationContext context) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.getConnector();

    String baseDir = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();

    Context ctx = tomcat.addContext("", baseDir);

    Tomcat.addServlet(ctx, "applicationServlet", new ApplicationServlet(context));
    ctx.addServletMappingDecoded("/", "applicationServlet");

    tomcat.start();
    tomcat.getServer().await();
  }
}
