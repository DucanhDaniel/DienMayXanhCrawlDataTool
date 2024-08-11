package DienMayXanh;

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

        DomNode domNode = page.querySelector(".content-article");

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("║THÔNG TIN SẢN PHẨM: ║");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("Tên sản phẩm: " + electronicDevice.getProductName());
        System.out.println("Giá sản phẩm: " + electronicDevice.getPrice());
        System.out.println("Đánh giá sản phẩm: " + electronicDevice.getStars());
        System.out.println("URL: " + electronicDevice.getProductURL());

        System.out.println(domNode.asNormalizedText());

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("║THÔNG TIN THÔNG SỐ: ║");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━");

        domNode = page.querySelector(".parameter__title");
        System.out.println(domNode.asNormalizedText());

        DomNodeList<DomNode> domNodeList = page.querySelectorAll(".parameter ul li");
        int cnt = 0;
        for (DomNode row : domNodeList) {
            DomNode nodePTag = row.querySelector("p");
            cnt++;

            DomNode nodeDivTag = row.querySelector("div");
            DomNodeList<DomNode> nodeList = nodeDivTag.querySelectorAll("span");
            if ((nodePTag.asNormalizedText()).equals("Hãng")) {
                System.out.println(cnt + ". Hãng: ");
                for (char c : nodeDivTag.asNormalizedText().toCharArray()) {
                    System.out.print(c);
                    if (c == '.') break;
                }
                System.out.println();
                System.out.println("-----------------------------------");
                continue;
            }
            System.out.println(cnt + ". " + nodePTag.asNormalizedText());
            for (DomNode nodeSpanTag : nodeList) {
                System.out.println(nodeSpanTag.asNormalizedText());
            }
            System.out.println("-----------------------------------");
        }
    }
}
