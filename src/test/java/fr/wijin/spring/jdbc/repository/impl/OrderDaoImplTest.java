package fr.wijin.spring.jdbc.repository.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jdbc.config.AppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
class OrderDaoImplTest {


}
