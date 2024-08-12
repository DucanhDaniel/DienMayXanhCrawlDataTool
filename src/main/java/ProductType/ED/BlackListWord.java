package ProductType.ED;

import java.util.ArrayList;

public class BlackListWord {
    private static ArrayList<String> blackListWords = new ArrayList<>();
    public static void initBlackListWords() {
        blackListWords.add("Hình ảnh sản phẩm chỉ có tính chất minh họa");
        blackListWords.add("Tham khảo thêm");
        blackListWords.add("Tìm hiểu");
        blackListWords.add("Xem thêm");
        blackListWords.add("Hướng dẫn");
        blackListWords.add("Top");
        blackListWords.add("An error occurred");
        blackListWords.add("Chưa có gì để hiển thị");
        blackListWords.add("minh họa");
        blackListWords.add("video");
        blackListWords.add("cận cảnh");
    }

    public static void addBlackListWord(String word) {
        blackListWords.add(word);
    }
    public static boolean isContainBlackListWord(String text) {
        if (text == null || text.isEmpty()) return true;
        text = text.toLowerCase();
        for (String blackListWord : blackListWords) {
            if (text.contains(blackListWord.toLowerCase())) return true;
        }
        return false;
    }
}
