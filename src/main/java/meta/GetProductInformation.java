package meta;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;

public class GetProductInformation {
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
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("║THÔNG TIN THÔNG SỐ: ║");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

            DomNode prodSpecifi = page.querySelector(".prod-Spec-main");
            DomNodeList<DomNode> listSpecifications = prodSpecifi.querySelectorAll("div");
            for (DomNode node : listSpecifications) {
                String nodeClass = node.getAttributes().getNamedItem("class").getNodeValue();
                if (nodeClass.equals("h2")) continue;
                if (nodeClass.contains("title-specs")) {
                    System.out.println("- " + node.asNormalizedText().trim().toUpperCase() + ": ");
                }
                else if (nodeClass.contains("body-specs")) {
                    DomNodeList<DomNode> subList = node.querySelectorAll("ul li");
                    for (DomNode subNode : subList) {
                        DomNode specName = subNode.querySelector("span:nth-child(1)");
                        DomNode specValue = subNode.querySelector("span:nth-child(2)");
                        System.out.println("    + " + specName.asNormalizedText().trim() + ": " + specValue.asNormalizedText().trim());
                    }
                }
            }
            //Print description and product features
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("║THÔNG TIN SẢN PHẨM: ║");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            electronicDevice.printInfo();

            for (DomNode node : productInforList) {
                String text = node.asNormalizedText().trim();

                //Remove bad node
                if (BlackListWord.isContainBlackListWord(text)) continue;

                if (node.getLocalName().equals("ul")) {
                    DomNodeList<DomNode> subList = node.querySelectorAll("li");
                    for (DomNode subNode : subList) {
                        System.out.println("    • " + subNode.asNormalizedText().trim());
                    }
                } else if (node.getLocalName().equals("h2")) {
                    System.out.println("\033[0;1m" + node.asNormalizedText().trim().toUpperCase() + ":" + "\u001B[0m");
                } else if (node.getLocalName().equals("h3")) {
                    System.out.println("\033[0;1m" + "- " + node.asNormalizedText().trim() + ":" + "\u001B[0m");
                } else if (node.hasChildNodes() && node.getFirstChild().getLocalName() != null && node.getFirstChild().getLocalName().equals("strong")){
                    System.out.println("\033[0;1m" + "- " + node.asNormalizedText().trim() + ":" + "\u001B[0m");
                } else {
                    System.out.println("   + " + node.asNormalizedText().trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void test(String[] args) {
        get(new ElectronicDevice(
                "Máy giặt Toshiba Inverter 10Kg AW-DM1100JV(MK)",
                "5.950.000đ",
                4.7,
                7.0,
                "https://meta.vn/may-giat-thong-minh-ai-lg-inverter-11-kg-fv1411s4p-p80680"
        ));
    }
}
