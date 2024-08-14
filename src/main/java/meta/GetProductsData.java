package meta;

import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.util.ArrayList;
import java.util.logging.Level;

public class GetProductsData {
    public static String extractURL(String raw) {
        StringBuilder result = new StringBuilder("https://www.meta.vn/");
        int index = raw.indexOf("=/");
        for (int i = index + 2; i < raw.length() - 1; i++) {
            result.append(raw.charAt(i));
        }
        return result.toString();
    }

    public static ArrayList<ElectronicDevice> get (String product_name) {
        java.util.logging.Logger.getLogger("org").setLevel(Level.OFF);
        String URLValue = "https://www.meta.vn/";
        for (int i = 0; i < product_name.length(); i++) {
            if (product_name.charAt(i) == ' ') URLValue = URLValue + "-";
            else URLValue = URLValue + product_name.charAt(i);
        }

        //Create an ArrayList to store the products information
        ArrayList<ElectronicDevice> productsList = new ArrayList<>();

        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(URLValue);

            DomNodeList<DomNode> products = page.querySelectorAll(".list-product-highlight ul li");
            for (DomNode product : products) {
                //Get product name
                DomNode nameNode = product.querySelector("div div .prod-hl-name");
                String productName = nameNode.asNormalizedText().trim();

                //Get product URL
                DomNode urlNode = product.querySelector("div div.prod-hl-name a");
                String productURL = extractURL(urlNode.getAttributes().getNamedItem("href").toString());

                //Get product rating (stars)
                double productRating = -1;
                DomNode rateNode = product.querySelector(".rating-box");
                if (rateNode != null) {
                    String rating = rateNode.getAttributes().getNamedItem("title").getNodeValue();
                    rating = rating.replace(",", ".").replace("sao", "");
                    productRating = Double.parseDouble(rating);
                }

                //Get total rate
                double totalRateValue = -1;
                DomNode totalRateNode = product.querySelector(".amount-rate");
                if (totalRateNode != null) {
                    String totalRate = totalRateNode.asNormalizedText().trim();
                    totalRate = totalRate.replace("(", "").replace(")", "");
                    totalRateValue = Double.parseDouble(totalRate);
                }

                //Get product price
                DomNode priceNode = product.querySelector(".product-price-meta");
                String productPrice = priceNode.asNormalizedText().trim();
                productPrice = productPrice.replace(" ", "");

                productsList.add(new ElectronicDevice(
                        productName,
                        productPrice,
                        productRating,
                        totalRateValue,
                        productURL
                ));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return productsList;

    }
    public static void test(String[] args) {
        ArrayList<ElectronicDevice> res = GetProductsData.get("may-giat");
        for (ElectronicDevice product : res) {
            System.out.println(product);
        }

    }
}
