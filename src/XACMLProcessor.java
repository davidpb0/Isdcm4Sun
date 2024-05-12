import com.sun.xacml.PDP;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XACMLProcessor {

    public static RequestCtx createRequestCtxFromFile(String filePath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(new FileInputStream(filePath));
        doc.getDocumentElement().normalize();

        Node rootNode = doc.getDocumentElement();

        return RequestCtx.getInstance(rootNode);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object to read input
        System.out.println("Enter a number (1-5) to select the request file:");
        
        int choice = scanner.nextInt();
        while (choice < 1 || choice > 5) { 
            System.out.println("Invalid input. Please enter a number between 1 to 5:");
            choice = scanner.nextInt();
        }
        scanner.close(); 
        String requestFile = "src/request/XACMLRequest" + choice + ".xml";
        
        try {
            long startTime = System.nanoTime();

            PDP pdp = PDPInitializer.initializePDPFromConfig();
            RequestCtx request = createRequestCtxFromFile(requestFile);
            ResponseCtx response = pdp.evaluate(request);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;
            System.out.println("Evaluation time: " + duration + " ms");
            
            try (FileOutputStream fos = new FileOutputStream("XACMLContextResponse.xml");
                 PrintStream ps = new PrintStream(fos)) {
                response.encode(ps);
                response.encode(System.out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


