package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    public static SessionFactory factory;

    private HibernateFactory(){

    }

    public static synchronized SessionFactory getSessionFactory(){
        if(factory == null){
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(ClientEntity.class)
                    .buildSessionFactory();
        }
        return factory;
    }

}
