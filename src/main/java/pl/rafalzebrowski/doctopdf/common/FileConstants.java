package pl.rafalzebrowski.doctopdf.common;

import java.util.List;

public class FileConstants {
    public final static List<String> FILE_EXTENSION_ALLOWED = List.of(
            "doc",
            "docx",
            "application/msword",
            "application/x-tika-ooxml",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );
}
