package DienMayXanh;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;

import static font.TextFont.*;

public class GetProductInformation {
    public static StringBuilder allText;
    public static void get(ElectronicDevice electronicDevice) throws IOException {
        allText = new StringBuilder();
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage(electronicDevice.getProductURL());

        DomNode domNode;

        allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
        allText.append("║THÔNG TIN THÔNG SỐ: ║\n");
        allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");


        domNode = page.querySelector(".parameter__title");

        allText.append(BOLD).append(domNode.asNormalizedText().trim().toUpperCase()).append(RESET + "\n");

        DomNodeList<DomNode> domNodeList = page.querySelectorAll(".parameter ul li");
        int cnt = 0;
        for (DomNode row : domNodeList) {
            DomNode nodePTag = row.querySelector("p");
            cnt++;

            DomNode nodeDivTag = row.querySelector("div");
            DomNodeList<DomNode> nodeList = nodeDivTag.querySelectorAll("span");
            if ((nodePTag.asNormalizedText()).equals("Hãng")) {
                allText.append(BOLD).append(cnt).append(". Hãng: ").append(RESET +  "\n");
                allText.append("    - ");

                for (char c : nodeDivTag.asNormalizedText().toCharArray()) {
                    allText.append(c);
                    if (c == '.') break;
                }
                allText.append("\n");
                continue;
            }
            allText.append(BOLD).append(cnt).append(". ").append(nodePTag.asNormalizedText()).append(RESET + "\n");
            for (DomNode nodeSpanTag : nodeList) {
                allText.append("    - ").append(nodeSpanTag.asNormalizedText()).append("\n");
            }
        }

        allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");
        allText.append("║THÔNG TIN SẢN PHẨM: ║\n");
        allText.append("━━━━━━━━━━━━━━━━━━━━━━\n");

        allText.append(electronicDevice.toString()).append("\n");

        domNode = page.querySelector(".content-article");
        DomNodeList<DomNode>  articleList = domNode.getChildNodes();
        for (DomNode node : articleList) {
            //Remove unused nodes
            if (BlackListWord.isContainBlackListWord(node.asNormalizedText().trim())) continue;

            //Print text nodes
            String text = node.asNormalizedText().trim();
            if (node.getLocalName().equals("h2") || node.getLocalName().equals("h3")) {
                allText.append(BOLD + "* ").append(text).append(":").append(RESET + "\n");
            }
            else if (text.charAt(0) == '-') {
                allText.append("    ").append(text).append("\n");
            }
            else {
                allText.append(text).append("\n");
            }
        }
        System.out.println(allText);
    }
    public static void main(String[] args) throws IOException {
        BlackListWord.initBlackListWords();
        get(new ElectronicDevice(
                "Máy giặt Toshiba Inverter 10Kg AW-DM1100JV(MK)",
                "5.950.000đ",
                4.7,
                7.0,
                "https://www.dienmayxanh.com/may-giat/may-giat-samsung-inverter-9-kg-ww90t634dln-sv"
        ));
    }
}
