package cn.ksmcbrigade.scbc;

import cn.ksmcbrigade.scbc.utils.ConfigUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Config {
    public final ConfigUtils config;

    public final Logger LOGGER = LogManager.getLogger();

    //temp
    public String lastServer = "";

    public Config() throws IOException {
        config = new ConfigUtils();
    }

    public void logger(String info){
        LOGGER.info("[SimpleClient] "+info);
    }

    public void logger(String info,String e){
        LOGGER.info("[SimpleClient] "+info,e);
    }

    public void error(String info,String m,Exception e){
        LOGGER.error("[SimpleClient] "+info + " {}",m,e);
    }

    public void error(String info,Exception e){
        LOGGER.error("[SimpleClient] "+info,e);
    }
}
