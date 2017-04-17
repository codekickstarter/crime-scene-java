public class FileHotspotIndicators {

    private String fileName;
    private Integer changeCount;
    private Integer amountOfLines;

    public FileHotspotIndicators(String fileName, Integer changeCount, Integer amountOfLines) {
        this.fileName = fileName;
        this.changeCount = changeCount;
        this.amountOfLines = amountOfLines;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getChangeCount() {
        return changeCount;
    }

    public Integer getAmountOfLines() {
        return amountOfLines;
    }
}
