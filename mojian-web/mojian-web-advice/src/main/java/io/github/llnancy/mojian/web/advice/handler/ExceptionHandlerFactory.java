package io.github.llnancy.mojian.web.advice.handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * {@link IExceptionHandler} factory
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/3/15
 */
public class ExceptionHandlerFactory implements ApplicationContextAware {

    private final Map<Class<? extends Throwable>, Class<? extends IExceptionHandler>> EHMap = Maps.newHashMap();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void init() {
        Map<String, IExceptionHandler> beans = this.applicationContext.getBeansOfType(IExceptionHandler.class);
        for (Map.Entry<String, IExceptionHandler> entry : beans.entrySet()) {
            IExceptionHandler handler = entry.getValue();
            List<Class<? extends Throwable>> exceptionClasses = handler.getExceptionClasses();
            for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
                EHMap.put(exceptionClass, handler.getClass());
            }
        }
    }

    public void configureExceptionHandler(Class<? extends Throwable> exClass, Class<? extends IExceptionHandler> ehClass) {
        Preconditions.checkNotNull(exClass);
        Preconditions.checkNotNull(ehClass);
        EHMap.put(exClass, ehClass);
    }

    public IExceptionHandler match(Throwable ex) {
        Class<? extends Throwable> exClass = ex.getClass();
        Class<? extends IExceptionHandler> clazz = EHMap.get(exClass);
        if (clazz == null) {
            Class<?> superclass = exClass.getSuperclass();
            clazz = EHMap.get(superclass);
            if (clazz == null) {
                clazz = Server5XxExceptionHandler.class;
            }
        }
        IExceptionHandler bean = this.applicationContext.getBean(clazz);
        bean.formatIResponse(ex);
        return bean;
    }
}
