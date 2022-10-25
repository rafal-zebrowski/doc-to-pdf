package pl.rafalzebrowski.doctopdf.service;

import java.io.File;

public interface CloudmersiveApiService {
    byte[] convertDocumentDocxToPdf(File file);
}
