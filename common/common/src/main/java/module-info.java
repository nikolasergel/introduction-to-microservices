module com.epam.common {
    requires com.fasterxml.jackson.databind;
    requires jakarta.validation;
    requires spring.context;
    requires spring.web;
    requires org.hibernate.validator;

    exports com.epam.common.annotation;
    exports com.epam.common.exception;
    exports com.epam.common.dto;
    exports com.epam.common.handler;
}