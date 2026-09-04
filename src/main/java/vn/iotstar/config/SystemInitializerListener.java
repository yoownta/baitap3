package vn.iotstar.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SystemInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=================================================");
        System.out.println("SSTORE WEB APPLICATION STARTUP - JPA INITIALIZING");
        System.out.println("=================================================");
        try {
            DataInitializer.initSampleData();
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo dữ liệu mặc định: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaConfig.closeFactory();
        System.out.println("SSTORE WEB APPLICATION SHUTDOWN - JPA FACTORY CLOSED.");
    }
}
