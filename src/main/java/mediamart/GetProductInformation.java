package mediamart;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import static font.TextFont.*;

public class GetProductInformation {
    public static StringBuilder allText;
    public static void get(ElectronicDevice electronicDevice) {
        allText = new StringBuilder();
        //TODO: Implement this method to get product information using the provided electronic device
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            HtmlPage page = webClient.getPage(electronicDevice.getProductURL());


            DomNode productSpecifi = page.querySelector("div.col-12.col-md-5.col-lg-5 > div > div:nth-child(5) > div > table > tbody");
            DomNodeList<DomNode> productInforList = productSpecifi.querySelectorAll("tr");

            //Print product specifications
            allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
            allText.append("║THÔNG TIN THÔNG SỐ: ║\n");
            allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");

            allText.append(WHITE_BOLD + "- THƯƠNG HIỆU:" + RESET + "\n");

            for (DomNode productInfor : productInforList) {
                if (productInfor.hasChildNodes()) {
                    if (productInfor.querySelector("th") != null) {
                        allText.append(WHITE_BOLD + "- ").append(productInfor.asNormalizedText().trim().toUpperCase()).append(":").append(RESET).append("\n");
                        continue;
                    }
                    String specificationName = productInfor.querySelector("td:nth-child(1)").asNormalizedText().trim();
                    DomNodeList<DomNode> tableNode = productInfor.querySelectorAll("li");
                    if (tableNode.size() >= 2) {
                        allText.append("    + ").append(specificationName).append(" ");
                        for (int i = 0; i < tableNode.size() - 1; i++) {
                            allText.append(tableNode.get(i).asNormalizedText().trim()).append(", ");
                        }
                        allText.append(tableNode.getLast().asNormalizedText().trim()).append("\n");
                        continue;
                    }
                    String specificationValue = productInfor.querySelector("td:nth-child(2)").asNormalizedText().trim();
                    allText.append("    + ").append(specificationName).append(" ").append(specificationValue).append("\n");
                }
            }

            //Print description and product features
            allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
            allText.append("║THÔNG TIN SẢN PHẨM: ║\n");
            allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");

            allText.append(electronicDevice).append("\n");

            DomNode contentNode = page.querySelector("#gioi-thieu-san-pham > div.col-12.col-md-12 > div.content-editor.pd-content.pd-content-seemore.pd-seemore");
            DomNode productDescription = contentNode.querySelector("div:nth-child(2)");
            DomNode contentTitleNode = contentNode.querySelector("h2");
            allText.append(WHITE_BOLD).append(contentTitleNode.asNormalizedText().trim().toUpperCase()).append(":").append(RESET).append("\n");
            DomNodeList<DomNode> contentList = productDescription.getChildNodes();

            boolean firstNode = false;
            for (DomNode node : contentList) {
                String text = node.asNormalizedText().trim();
                if (BlackListWord.isContainBlackListWord(text)) continue;
                if (!firstNode) {
                    firstNode = true;
                    allText.append("    ").append(text).append("\n");
                    continue;
                }
                if (node.getLocalName() == null || !node.getLocalName().equals("h3")) {
                    allText.append("    ").append(text).append("\n");
                } else {
                    allText.append(WHITE_BOLD).append(node.asNormalizedText().trim().toUpperCase()).append(RESET).append("\n");
                }
            }
            System.out.println(allText);
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
