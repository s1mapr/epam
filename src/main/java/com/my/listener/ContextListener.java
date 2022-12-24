package com.my.listener;

import com.my.dao.DBManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * Listener for working wit database
 */
@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        DBManager.getInstance();
    }


    public void contextDestroyed(ServletContextEvent event) {
        DBManager.getInstance().shutdown();
    }

}