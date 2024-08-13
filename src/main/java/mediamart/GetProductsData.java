package mediamart;

import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.util.ArrayList;

public class GetProductsData {
    public static double extractTotalRate(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) result.append(text.charAt(i));
            else break;
        }
        return Double.parseDouble(result.toString());
    }
    public static double extractStars(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '/') break;
            if (text.charAt(i) == ',') result.append('.');
            else result.append(text.charAt(i));
        }
        return Double.parseDouble(result.toString());
    }
    public static ArrayList<ElectronicDevice> get(String productName) {
        if (productName.equalsIgnoreCase("dieu-hoa"))
            productName = "dieu-hoa-nhiet-do";

        ArrayList<ElectronicDevice> products = new ArrayList<>();
        //TODO: Implement this method to get products data using the provided product name
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            productName = productName.replace("-", "+");
            HtmlPage page = webClient.getPage("https://mediamart.vn/tag?key=" + productName);

            DomNodeList<DomNode> listProductNodes = page.querySelectorAll("div.col-6.col-md-3.col-lg-3");
            for (DomNode node : listProductNodes) {
                String name = node.querySelector("p.card-title.product-name").getTextContent().trim();
                String price = node.querySelector("p.card-text.product-price").getTextContent().trim();
                String url = "https://mediamart.vn" + node.querySelector("a.product-item").getAttributes().getNamedItem("href").getNodeValue();

                double totalRate = -1, stars = -1;
                HtmlPage productPage = webClient.getPage(url);
                DomNode ratingNode = productPage.querySelector(".rating-average");
                if (ratingNode!= null) {
                    String productRating = productPage.querySelector("body > div.body-content.bg-page > div.container > div.wrap-product > div:nth-child(15) > div > div > div.rating-summary > div > div.col-md-5 > p.rating-average").getTextContent().trim();
                    stars = extractStars(productRating);
                    DomNode productRatingNode = productPage.querySelector("body > div.body-content.bg-page > div.container > div.wrap-product > div:nth-child(15) > div > div > div.rating-summary > div > div.col-md-5 > p:nth-child(3)");
                    totalRate = extractTotalRate(productRatingNode.asNormalizedText());
                }

                products.add(new ElectronicDevice(name, price, stars, totalRate, url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public static void main(String[] args) {
        ArrayList<ElectronicDevice> result = get("may-giat");
        for (ElectronicDevice product : result) {
            System.out.println(product);
        }

    }
}
