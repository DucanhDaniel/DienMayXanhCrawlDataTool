package DienMayXanh;

import ProductType.ED.ElectronicDevice;
import org.apache.commons.lang3.StringUtils;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class GetProductsData {

    public static int getStars(DomNode node) {
        DomNode starNodes = node.querySelector(".star");
        return 0;
    }
    public static String validString(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\n' || (c == ' ' && str.charAt(i - 1) == ' ')) continue;
            result.append(c);
        }
        return result.toString().trim();
    }
    public static String extractURL(String raw) {
        StringBuilder result = new StringBuilder("https://www.dienmayxanh.com/");
        int index = raw.indexOf("=/");
        for (int i = index + 2; i < raw.length() - 1; i++) {
            result.append(raw.charAt(i));
        }
        return result.toString();
    }
    public static ArrayList<ElectronicDevice> get(String product_name) throws IOException {
        java.util.logging.Logger.getLogger("org").setLevel(Level.OFF);
        ArrayList<ElectronicDevice> electronicDevices = new ArrayList<>();

        String URLValue = "https://www.dienmayxanh.com/";
        for (int i = 0; i < product_name.length(); i++) {
            if (product_name.charAt(i) == ' ') URLValue = URLValue + "-";
            else URLValue = URLValue + product_name.charAt(i);
        }
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(URLValue);

            String selector = ".listproduct li";
            DomNodeList<DomNode> rows = page.querySelectorAll(selector);
            System.out.println();
            for (DomNode row : rows) {
                try {

                    DomNode product = row.querySelector("a:nth-child(1)");

                    String name = product.querySelector("h3").getTextContent();
                    String price = product.querySelector("strong").getTextContent();

                    String starXml = product.querySelector(".item-rating p").asXml();
                    double numStar = StringUtils.countMatches(starXml, "icon-star")
                            - 0.5 * StringUtils.countMatches(starXml, "icon-star-half")
                            - StringUtils.countMatches(starXml, "icon-star-dark");


                    String productURL = product.getAttributes().getNamedItem("href").toString();

                    electronicDevices.add(new ElectronicDevice(
                            validString(name),
                            price,
                            numStar,
                            product.querySelector(".item-rating .item-rating-total").asNormalizedText(),
                            extractURL(productURL)
                    ));

                } catch (Exception e) {

                }
            }
        } catch (FailingHttpStatusCodeException e) {
//            System.out.println("Products not found on the website!");
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Unknown error!");
            return new ArrayList<>();
        }

        return electronicDevices;
    }
}
