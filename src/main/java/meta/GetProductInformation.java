package meta;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import static font.TextFont.*;

public class GetProductInformation {
    public static StringBuilder allText = new StringBuilder();
    public static void get(ElectronicDevice electronicDevice) {
        // TODO: Implement this method to get product information using the provided electronic device
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(electronicDevice.getProductURL());

            DomNode productInfo = page.querySelector(".prod-desc-content");
            DomNodeList<DomNode> productInforList = productInfo.getChildNodes();

            //Print product specifications
           allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
           allText.append("║THÔNG TIN THÔNG SỐ: ║\n");
           allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");

            DomNode prodSpecifi = page.querySelector(".prod-Spec-main");
            DomNodeList<DomNode> listSpecifications = prodSpecifi.querySelectorAll("div");
            for (DomNode node : listSpecifications) {
                String nodeClass = node.getAttributes().getNamedItem("class").getNodeValue();
                if (nodeClass.equals("h2")) continue;
                if (nodeClass.contains("title-specs")) {
                   allText.append("- ").append(node.asNormalizedText().trim().toUpperCase()).append(": \n");
                }
                else if (nodeClass.contains("body-specs")) {
                    DomNodeList<DomNode> subList = node.querySelectorAll("ul li");
                    for (DomNode subNode : subList) {
                        DomNode specName = subNode.querySelector("span:nth-child(1)");
                        DomNode specValue = subNode.querySelector("span:nth-child(2)");
                       allText.append("    + ").append(specName.asNormalizedText().trim()).append(": ").append(specValue.asNormalizedText().trim()).append("\n");
                    }
                }
            }
            //Print description and product features
           allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
           allText.append("║THÔNG TIN SẢN PHẨM: ║\n");
           allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
            allText.append(electronicDevice).append("\n");

            for (DomNode node : productInforList) {
                String text = node.asNormalizedText().trim();

                //Remove bad node
                if (BlackListWord.isContainBlackListWord(text)) continue;

                if (node.getLocalName().equals("ul")) {
                    DomNodeList<DomNode> subList = node.querySelectorAll("li");
                    for (DomNode subNode : subList) {
                       allText.append("    • ").append(subNode.asNormalizedText().trim()).append("\n");
                    }
                } else if (node.getLocalName().equals("h2")) {
                   allText.append(BOLD).append(node.asNormalizedText().trim().toUpperCase()).append(":").append(RESET).append("\n");
                } else if (node.getLocalName().equals("h3")) {
                   allText.append(BOLD + "- ").append(node.asNormalizedText().trim()).append(":").append(RESET).append("\n");
                } else if (node.hasChildNodes() && node.getFirstChild().getLocalName() != null && node.getFirstChild().getLocalName().equals("strong")){
                   allText.append(BOLD + "- ").append(node.asNormalizedText().trim()).append(":").append(RESET).append("\n");
                } else {
                   allText.append("   + ").append(node.asNormalizedText().trim()).append("\n");
                }
            }
            System.out.println(allText);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BlackListWord.initBlackListWords();
        get(new ElectronicDevice(
                "Máy giặt Toshiba Inverter 10Kg AW-DM1100JV(MK)",
                "5.950.000đ",
                4.7,
                7.0,
                "https://meta.vn/may-giat-thong-minh-ai-lg-inverter-11-kg-fv1411s4p-p80680"
        ));
    }
}
