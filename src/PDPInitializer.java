import com.sun.xacml.PDP;
import com.sun.xacml.ConfigurationStore;


public class PDPInitializer {

    public static PDP initializePDPFromConfig() throws Exception {
        System.setProperty("com.sun.xacml.PDPConfigFile", "src/config/config_rbac.xml");

        // Load the configuration
        ConfigurationStore store = new ConfigurationStore();

        // Use the default factories from the configuration
        store.useDefaultFactories();

        // Get the PDP configuration and setup the PDP
        return new PDP(store.getDefaultPDPConfig());
    }
}
