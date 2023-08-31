package it.codegen.assignment.sun.travel.configuration;

import it.codegen.assignment.sun.travel.service.CommonService;
import it.codegen.assignment.sun.travel.service.CommonServiceImpl;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformer;
import it.codegen.assignment.sun.travel.transformer.PayloadTransformerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Default configuration.
 */
@Configuration
public class DefaultConfiguration {

    /**
     * Get payload transformer payload transformer.
     *
     * @return the payload transformer
     */
    @Bean(name = "it.codegen.assignment.sun.travel.transformer.PayloadTransformer")
    public PayloadTransformer getPayloadTransformer(){
        return new PayloadTransformerImpl();
    }

//    @Bean(name = "it.codegen.assignment.sun.travel.service.CommonService")
//    public CommonService getCommonService(){
//        return new CommonServiceImpl();
//    }
}
