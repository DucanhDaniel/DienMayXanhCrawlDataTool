package DienMayXanh;

import ProductType.ED.BlackListWord;
import ProductType.ED.ElectronicDevice;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;

public class GetProductInformation {
    public static void get(ElectronicDevice electronicDevice) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage(electronicDevice.getProductURL());

        DomNode domNode;

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("║THÔNG TIN THÔNG SỐ: ║");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

        domNode = page.querySelector(".parameter__title");
        System.out.println("\033[0;1m" + domNode.asNormalizedText().trim().toUpperCase() + "\u001B[0m");

        DomNodeList<DomNode> domNodeList = page.querySelectorAll(".parameter ul li");
        int cnt = 0;
        for (DomNode row : domNodeList) {
            DomNode nodePTag = row.querySelector("p");
            cnt++;

            DomNode nodeDivTag = row.querySelector("div");
            DomNodeList<DomNode> nodeList = nodeDivTag.querySelectorAll("span");
            if ((nodePTag.asNormalizedText()).equals("Hãng")) {
                System.out.println("\033[0;1m" + cnt + ". Hãng: " + "\u001B[0m");
                System.out.print("    - ");
                for (char c : nodeDivTag.asNormalizedText().toCharArray()) {
                    System.out.print(c);
                    if (c == '.') break;
                }
                System.out.println();
                continue;
            }
            System.out.println("\033[0;1m" + cnt + ". " + nodePTag.asNormalizedText() + "\u001B[0m");
            for (DomNode nodeSpanTag : nodeList) {
                System.out.println("    - " + nodeSpanTag.asNormalizedText());
            }
        }


        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("║THÔNG TIN SẢN PHẨM: ║");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

        electronicDevice.printInfo();

        domNode = page.querySelector(".content-article");
        DomNodeList<DomNode>  articleList = domNode.getChildNodes();
        for (DomNode node : articleList) {
            //Remove unused nodes
            if (BlackListWord.isContainBlackListWord(node.asNormalizedText().trim())) continue;

            //Print text nodes
            String text = node.asNormalizedText().trim();
            if (node.getLocalName().equals("h2") || node.getLocalName().equals("h3")) {
                System.out.println("\033[0;1m" + "* " + text + ":" + "\u001B[0m");
            }
            else if (text.charAt(0) == '-') System.out.println("    " + text);
            else System.out.println(text);
        }
    }
    public static void test(String[] args) throws IOException {
        get(new ElectronicDevice(
                "Máy giặt Toshiba Inverter 10Kg AW-DM1100JV(MK)",
                "5.950.000đ",
                4.7,
                7.0,
                "https://www.dienmayxanh.com/may-giat/may-giat-samsung-inverter-9-kg-ww90t634dln-sv"
        ));
    }
}
