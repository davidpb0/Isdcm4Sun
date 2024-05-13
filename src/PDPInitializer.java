import com.sun.xacml.PDP;
import com.sun.xacml.ConfigurationStore;


public class PDPInitializer {

    public static PDP initializePDPFromConfig() throws Exception {
        System.setProperty("com.sun.xacml.PDPConfigFile", "src/config/config_rbac.xml");

        ConfigurationStore store = new ConfigurationStore();

        store.useDefaultFactories();

        return new PDP(store.getDefaultPDPConfig());
    }
}
