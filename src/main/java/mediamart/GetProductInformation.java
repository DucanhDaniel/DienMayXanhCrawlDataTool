package mediamart;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import static font.TextFont.*;

public class GetProductInformation {
    public static void get(ElectronicDevice electronicDevice) {
        //TODO: Implement this method to get product information using the provided electronic device
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(electronicDevice.getProductURL());


            DomNode productSpecifi = page.querySelector("div.col-12.col-md-5.col-lg-5 > div > div:nth-child(5) > div > table > tbody");
            DomNodeList<DomNode> productInforList = productSpecifi.querySelectorAll("tr");

            //Print product specifications
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("║THÔNG TIN THÔNG SỐ: ║");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println(WHITE_BOLD + "- THƯƠNG HIỆU:" + RESET);

            for (DomNode productInfor : productInforList) {
                if (productInfor.hasChildNodes()) {
                    if (productInfor.querySelector("th") != null) {
                        System.out.println(WHITE_BOLD + "- " + productInfor.asNormalizedText().trim().toUpperCase() + ":" + RESET);
                        continue;
                    }
                    String specificationName = productInfor.querySelector("td:nth-child(1)").asNormalizedText().trim();
                    DomNodeList<DomNode> tableNode = productInfor.querySelectorAll("li");
                    if (tableNode.size() >= 2) {
                        System.out.print("    + " + specificationName + " ");
                        for (int i = 0; i < tableNode.size() - 1; i++) {
                            System.out.print(tableNode.get(i).asNormalizedText().trim() + ", ");
                        }
                        System.out.println(tableNode.getLast().asNormalizedText().trim());
                        continue;
                    }
                    String specificationValue = productInfor.querySelector("td:nth-child(2)").asNormalizedText().trim();
                    System.out.println("    + " + specificationName + " " + specificationValue);
                }
            }

            //Print description and product features
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("║THÔNG TIN SẢN PHẨM: ║");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

            electronicDevice.printInfo();

            DomNode contentNode = page.querySelector("#gioi-thieu-san-pham > div.col-12.col-md-12 > div.content-editor.pd-content.pd-content-seemore.pd-seemore");
            DomNode productDescription = contentNode.querySelector("div:nth-child(2)");
            DomNode contentTitleNode = contentNode.querySelector("h2");
            System.out.println(WHITE_BOLD + contentTitleNode.asNormalizedText().trim().toUpperCase() + ":" + RESET);
            DomNodeList<DomNode> contentList = productDescription.getChildNodes();

            boolean firstNode = false;
            for (int i = 0; i < contentList.size(); i++) {
                DomNode node = contentList.get(i);
                String text = node.asNormalizedText().trim();
                if (BlackListWord.isContainBlackListWord(text)) continue;
                if (!firstNode) {
                    firstNode= true;
                    System.out.println("    " + text);
                    continue;
                }
                if (node.getLocalName() == null || !node.getLocalName().equals("h3")) {
                    System.out.println("    " + text);
                }
                else {
                    System.out.println(WHITE_BOLD + node.asNormalizedText().trim().toUpperCase() + RESET);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        BlackListWord.initBlackListWords();
        get(new ElectronicDevice(
                "Máy giặt Toshiba Inverter 10Kg AW-DM1100JV(MK)",
                "5.950.000đ",
                4.7,
                7.0,
                "https://mediamart.vn/may-giat/may-giat-long-ngang-lg-ai-dd-inverter-9kg-fb1209s6w"
        ));
    }
}
