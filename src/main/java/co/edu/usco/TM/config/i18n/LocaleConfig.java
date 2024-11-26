package co.edu.usco.TM.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("ISO_8859_1");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("es"));
        return resolver;
    }

    /**
     * <h2>Interceptor de cambio de idioma</h2>
     * Se encarga de leer automáticamente el idioma desde la cabecera <code>Accept-Language</code> si es un cliente
     * quien la envía, o desde un parámetro en la URL como <code>?lang=es</code>
     *
     * <b>Importante:</b> No es necesario agregar la anotación {@link org.springframework.web.bind.annotation.RequestHeader}
     * entre los parámetros de las funciones anotadas por mapeos de controladores, puesto que gracias a esta configuración
     * se interceptará el idioma global y automáticamente.
     *
     * @return Instancia de LocaleChangeInterceptor que contiene el idioma cambiado al interceptarlo en las cabeceras
     * de las peticiones HTTP.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
