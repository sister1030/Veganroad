package service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SessionFactory {

    private SqlSessionFactory factory;

    public SqlSession getInstance() {
        if (factory == null) {
            try {
                String resource = "mybatis-config.xml";
                InputStream is = Resources.getResourceAsStream(resource);
                factory = new SqlSessionFactoryBuilder().build(is);

                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return factory.openSession(true);
    }

}
