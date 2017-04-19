public class FileHotspotIndicators {

    private String fileName;
    private Integer changeCount;
    private Integer complexity;

    public FileHotspotIndicators(String fileName, Integer changeCount, Integer complexity) {
        this.fileName = fileName;
        this.changeCount = changeCount;
        this.complexity = complexity;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getChangeCount() {
        return changeCount;
    }

    public Integer getComplexity() {
        return complexity;
    }
}
