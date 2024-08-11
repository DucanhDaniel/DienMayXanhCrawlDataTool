package ProductType.ED;

public class ElectronicDevice {
    private final String productName;
    private final String price;
    private final double stars;
    private final double totalRating;
    private final String productURL;

    public ElectronicDevice(String productName, String price, double stars, String totalRating, String productURL) {
        this.productName = productName;
        this.price = price;
        this.stars = stars;
        this.totalRating = Double.parseDouble(totalRating);
        this.productURL = productURL;
    }

    public String getProductName() {
        return productName;
    }
    public String getPrice() {
        return price;
    }
    public double getStars() {
        return stars;
    }
    public double getTotalRating() {
        return totalRating;
    }
    public double getPoints() {
        return stars * totalRating;
    }
    public String getProductURL() {
        return productURL;
    }


    @Override
    public String toString() {
        return "Product name: " + productName + "\n"
                + "Price: " + price + "\n" +
                "Stars: " + stars + "\n" +
                "Total Rating: " + totalRating + "\n" +
                "Product URL: " + productURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ElectronicDevice other = (ElectronicDevice) obj;
        return productName.equals(other.productName) &&
                price.equals(other.price) &&
                stars == other.stars &&
                totalRating == other.totalRating;
    }

}
